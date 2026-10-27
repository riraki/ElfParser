package com.riraki.elfparser

import java.io.BufferedInputStream
import java.io.EOFException
import java.io.File
import java.io.InputStream

object Elf64Parser {
    fun readElfHeader(elf64File: File): Elf64_Ehdr {
        return Elf64_Ehdr(elf64File)
    }

    fun readProgramHeaders(elf64File: File, e_phoff: Elf64_Off, e_phnum: Elf64_Half): List<Elf64_Phdr> {
        return BufferedInputStream(elf64File.inputStream()).use { inputStream ->
            inputStream.skipFully(e_phoff.toLong())

            List(e_phnum.toInt()) { _ ->
                Elf64_Phdr(
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream)
                )
            }
        }
    }

    fun readProgramHeaders(elf64File: File): List<Elf64_Phdr> {
        val elf64_ehdr = Elf64_Ehdr(elf64File)
        return readProgramHeaders(elf64File, elf64_ehdr.e_phoff, elf64_ehdr.e_phnum)
    }

    fun readSectionStringTable(elf64File: File): String {
        val elf64_ehdr = readElfHeader(elf64File)
        BufferedInputStream(elf64File.inputStream()).use { inputStream ->
            inputStream.skipFully(elf64_ehdr.e_shoff.toLong())

            for (i in 0..<elf64_ehdr.e_shnum.toInt()) {
                inputStream.skipFully(24)
                val sh_offset: ULong = ParseUtils.readULongFromInputStream(inputStream)
                val sh_size: ULong = ParseUtils.readULongFromInputStream(inputStream)
                if (i == elf64_ehdr.e_shstrndx.toInt()) {
                    BufferedInputStream(elf64File.inputStream()).use { stringTableStream ->
                        stringTableStream.skipFully(sh_offset.toLong())
                        val bytes = ByteArray(sh_size.toInt())
                        stringTableStream.read(bytes)
                        return String(bytes)
                    }
                }
                inputStream.skipFully(24)
            }
            throw IllegalStateException("ELF Section String Table not found. e_shstrndx: ${elf64_ehdr.e_shstrndx}")
        }
    }

    fun readDynamicString(elf64File: File): String {
        val elf64_ehdr = readElfHeader(elf64File)
        val sectionStringTable = readSectionStringTable(elf64File)
        BufferedInputStream(elf64File.inputStream()).use { inputStream ->
            inputStream.skipFully(elf64_ehdr.e_shoff.toLong())

            for (i in 0..<elf64_ehdr.e_shnum.toInt()) {
                val elf64_shdr = Elf64_Shdr(
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    sectionStringTable
                )
                if (elf64_shdr.getName() == ".dynstr" && elf64_shdr.sh_type == SHT_STRTAB) {
                    BufferedInputStream(elf64File.inputStream()).use { inputStream ->
                        inputStream.skipFully(elf64_shdr.sh_offset.toLong())
                        val bytes = ByteArray(elf64_shdr.sh_size.toInt())
                        inputStream.read(bytes)
                        return String(bytes)
                    }
                }
            }
            throw IllegalStateException("ELF Dynamic String not found.")
        }
    }

    fun readSectionHeaders(elf64File: File, e_shoff: Elf64_Off, e_shnum: Elf64_Half, stringTable: String): List<Elf64_Shdr> {
        return BufferedInputStream(elf64File.inputStream()).use { inputStream ->
            inputStream.skipFully(e_shoff.toLong())

            List(e_shnum.toInt()) {
                Elf64_Shdr(
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    stringTable
                )
            }
        }
    }

    fun readSectionHeaders(elf64File: File, e_shoff: Elf64_Off, e_shnum: Elf64_Half): List<Elf64_Shdr> {
        val elf64_ehdr = readElfHeader(elf64File)
        return readSectionHeaders(elf64File, elf64_ehdr.e_shoff, elf64_ehdr.e_shnum, readSectionStringTable(elf64File))
    }

    fun readSectionHeaders(elf64File: File, stringTable: String): List<Elf64_Shdr> {
        val elf64_ehdr = readElfHeader(elf64File)
        return readSectionHeaders(elf64File, elf64_ehdr.e_shoff, elf64_ehdr.e_shnum, stringTable)
    }

    fun readSectionHeaders(elf64File: File): List<Elf64_Shdr> {
        return readSectionHeaders(elf64File, readSectionStringTable(elf64File))
    }

    fun readDynsymSections(elf64File: File): List<Elf64_Sym> {
        for (elf64_shdr in readSectionHeaders(elf64File)) {
            if (elf64_shdr.getName() == ".dynsym" && elf64_shdr.sh_type == SHT_DYNSYM) {
                return readDynsymSections(elf64File, elf64_shdr)
            }
        }
        return emptyList()
    }

    fun readDynsymSections(elf64File: File, elf64_shdr: Elf64_Shdr): List<Elf64_Sym> {
        // 校验节名称和节类型
        val sectionName = elf64_shdr.getName()
        if (sectionName != ".dynsym" || elf64_shdr.sh_type != SHT_DYNSYM) {
            throw IllegalArgumentException("Invalid section for symbol table: $sectionName")
        }

        // 检查是否包含 SHF_ALLOC 标志
        if ((elf64_shdr.sh_flags and SHF_ALLOC) == 0uL) {
            throw IllegalArgumentException("Symbol table section must have SHF_ALLOC flag set.")
        }

        return BufferedInputStream(elf64File.inputStream()).use { inputStream ->
            inputStream.skipFully(elf64_shdr.sh_offset.toLong())

            List((elf64_shdr.sh_size / elf64_shdr.sh_entsize).toInt()) {
                Elf64_Sym(
                    ParseUtils.readUIntFromInputStream(inputStream),
                    inputStream.read().toUByte(),
                    inputStream.read().toUByte(),
                    ParseUtils.readUShortFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    readDynamicString(elf64File)
                )
            }
        }
    }

    fun readDynamicSections(elf64File: File): List<Elf64_Dyn> {
        for (elf64_shdr in readSectionHeaders(elf64File)) {
            if (elf64_shdr.getName() == ".dynamic" && elf64_shdr.sh_type == SHT_DYNAMIC) {
                return readDynamicSections(elf64File, elf64_shdr)
            }
        }
        return emptyList()
    }

    fun readDynamicSections(elf64File: File, elf64_shdr: Elf64_Shdr): List<Elf64_Dyn> {
        // 校验节名称和节类型
        val sectionName = elf64_shdr.getName()
        if (sectionName != ".dynamic" || elf64_shdr.sh_type != SHT_DYNAMIC) {
            throw IllegalArgumentException("Invalid section for symbol table: $sectionName")
        }

        // 检查是否包含 SHF_ALLOC 标志
        if ((elf64_shdr.sh_flags and SHF_ALLOC) == 0uL || (elf64_shdr.sh_flags and SHF_WRITE) == 0uL) {
            throw IllegalArgumentException("Symbol table section must have SHF_ALLOC and SHF_WRITE flag set.")
        }
    }

    /**
    * 确保从流中跳过指定数量的字节数 (n)
    * 如果中途遇到文件末尾（EOF），则抛出 EOFException
    */
    private fun InputStream.skipFully(n: Long) {
        if (n <= 0) return

        var remaining = n
        while (remaining > 0) {
            val skipped = this.skip(remaining)

            if (skipped <= 0L) {
                // 如果 skip() 返回 0，我们需要确认是真的到了文件末尾（EOF），
                // 还是该 InputStream 的底层实现暂时无法通过 skip 跳过。
                // 这里用一次 read() 来做最终的 EOF 探针。
                if (this.read() == -1) {
                    throw EOFException("Reached end of stream. Expected to skip $n bytes, but reached EOF with $remaining bytes remaining.")
                }

                // 如果 read() 成功读到了一个字节，说明流还没结束，
                // 刚才相当于成功跳过（消耗）了 1 个字节
                remaining--
            }
            else {
                remaining -= skipped
            }
        }
    }
}
