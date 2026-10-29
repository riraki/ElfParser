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

        val dynamicString: String = readDynamicString(elf64File)
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
                    dynamicString
                )
            }
        }
    }

    fun readSymtabSections(elf64File: File, symtabSectionHeader: Elf64_Shdr): List<Elf64_Sym> {
        // 校验节类型是否为静态符号表
        if (symtabSectionHeader.sh_type != SHT_SYMTAB) {
            throw IllegalArgumentException("Invalid section type for .symtab: ${symtabSectionHeader.sh_type}")
        }

        // 检查名称（如果是通过节头表字符串表获取的名字，可以做个校验，但有时名称也可能为空或被修改）
        val sectionName = symtabSectionHeader.getName()
        if (sectionName != ".symtab") {
            // 注意：有时非标准工具生成的符号表名字可能不叫 .symtab，但只要 sh_type 是 SHT_SYMTAB 就是合法的
            println("Warning: SHT_SYMTAB section name is '$sectionName', expected '.symtab'")
        }

        val stringTable: String? = readStringTable(elf64File)
        return BufferedInputStream(elf64File.inputStream()).use { inputStream ->
            inputStream.skipFully(symtabSectionHeader.sh_offset.toLong())

            List((symtabSectionHeader.sh_size / symtabSectionHeader.sh_entsize).toInt()) {
                Elf64_Sym(
                    ParseUtils.readUIntFromInputStream(inputStream),
                    inputStream.read().toUByte(),
                    inputStream.read().toUByte(),
                    ParseUtils.readUShortFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    stringTable
                )
            }
        }
    }

    fun readSymtabSections(elf64File: File): List<Elf64_Sym> {
        for (elf64_shdr in readSectionHeaders(elf64File)) {
            if (/* elf64_shdr.getName() == ".symtab" &&  */elf64_shdr.sh_type == SHT_SYMTAB) {
                return readSymtabSections(elf64File, elf64_shdr)
            }
        }
        return emptyList()
    }

    fun readStringTable(elf64File: File, strtabSectionHeader: Elf64_Shdr): String {
        if (strtabSectionHeader.sh_type != SHT_STRTAB) {
            throw IllegalArgumentException("Invalid section type for string table: ${strtabSectionHeader.sh_type}")
        }

        val sectionName = strtabSectionHeader.getName()
        if (sectionName != ".strtab") {
            println("Warning: String table section name is '$sectionName', expected '.strtab'")
        }

        return BufferedInputStream(elf64File.inputStream()).use { inputStream ->
            inputStream.skipFully(strtabSectionHeader.sh_offset.toLong())

            val size = strtabSectionHeader.sh_size.toInt()
            val bytes = inputStream.readFully(size)

            String(bytes, Charsets.UTF_8)
        }
    }

    fun readStringTable(elf64File: File): String? {
        for (shdr in readSectionHeaders(elf64File)) {
            if (shdr.getName() == ".strtab" && shdr.sh_type == SHT_STRTAB) {
                return readStringTable(elf64File, shdr)
            }
        }
        return null
    }

    fun readDynamicSections(elf64File: File, elf64_shdr: Elf64_Shdr): List<Elf64_Dyn> {
        // 校验节名称和节类型
        val sectionName = elf64_shdr.getName()
        if (sectionName != ".dynamic" || elf64_shdr.sh_type != SHT_DYNAMIC) {
            throw IllegalArgumentException("Invalid section for .dynamic: $sectionName")
        }

        // 检查是否包含 SHF_ALLOC 标志（.dynamic 运行时必须加载进内存）
        if ((elf64_shdr.sh_flags and SHF_ALLOC) == 0uL) {
            throw IllegalArgumentException("Dynamic section must have SHF_ALLOC flag set.")
        }

        val dynamicString: String = readDynamicString(elf64File)

        return BufferedInputStream(elf64File.inputStream()).use { inputStream ->
            inputStream.skipFully(elf64_shdr.sh_offset.toLong())

            List((elf64_shdr.sh_size / elf64_shdr.sh_entsize).toInt()) {
                Elf64_Dyn(
                    ParseUtils.readLongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    dynamicString
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

    fun readRelocationAppendSection(elf64File: File, relaSectionOffset: Elf64_Off, relaSectionSize: Elf64_Xword,
        relaSectionEntsize: Elf64_Xword): List<Elf64_Rela> {
        val elf64_ehdr = readElfHeader(elf64File)
        val dynsymSections: List<Elf64_Sym> = readDynsymSections(elf64File)

        return BufferedInputStream(elf64File.inputStream()).use { inputStream ->
            inputStream.skipFully(relaSectionOffset.toLong())
            List((relaSectionSize / relaSectionEntsize).toInt(), { _ ->
                Elf64_Rela(
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readULongFromInputStream(inputStream),
                    ParseUtils.readLongFromInputStream(inputStream),
                    elf64_ehdr.getElfMachine(),
                    dynsymSections
                    )
            })
        }
    }

    fun readRelocationAppendSection(elf64File: File, relaSectionHeader: Elf64_Shdr): List<Elf64_Rela> {
        if (relaSectionHeader.sh_type != SHT_RELA) {
            throw IllegalArgumentException("Argument relaSectionHeader.sh_type must be SHT_RELA!")
        }
        return readRelocationAppendSection(elf64File, relaSectionHeader.sh_offset,
            relaSectionHeader.sh_size, relaSectionHeader.sh_entsize)
    }

    fun readRelocationAppendSections(elf64File: File, relaSectionHeaders: List<Elf64_Shdr>): List<Elf64_Rela> {
        return buildList {
            relaSectionHeaders.forEach { relaSectionHeader ->
                addAll(readRelocationAppendSection(elf64File, relaSectionHeader))
            }
        }
    }

    fun readRelocationAppendSections(elf64File: File): List<Elf64_Rela> {
        val sectionHeaders: List<Elf64_Shdr> = readSectionHeaders(elf64File)
        val relaSectionHeaders: List<Elf64_Shdr> = buildList {
            sectionHeaders.forEach { sectionHeader ->
                if (sectionHeader.sh_type == SHT_RELA) {
                    add(sectionHeader)
                }
            }
        }
        return readRelocationAppendSections(elf64File, relaSectionHeaders)
    }

    fun readNoteSectionHeaders(elf64File: File): List<Elf64_Shdr> {
        val sectionHeaders = readSectionHeaders(elf64File)
        return buildList {
            sectionHeaders.forEach { sectionHeader ->
                if (sectionHeader.sh_type == SHT_NOTE) {
                    add(sectionHeader)
                }
            }
        }
    }

    /**
     * 解析单个 Note 节区中的所有 Elf64_Note 条目
     */
    fun readNotes(elf64File: File, noteSectionHeader: Elf64_Shdr): List<Elf64_Note> {
        if (noteSectionHeader.sh_type != SHT_NOTE) {
            throw IllegalArgumentException("Invalid section type for note section: ${noteSectionHeader.sh_type}")
        }

        return BufferedInputStream(elf64File.inputStream()).use { inputStream ->
            inputStream.skipFully(noteSectionHeader.sh_offset.toLong())

            val notes = mutableListOf<Elf64_Note>()
            var bytesRead = 0L
            val sectionSize = noteSectionHeader.sh_size.toLong()

            // 循环解析 Note 节区内的所有 Note 条目（可能包含多个）
            while (bytesRead < sectionSize) {
                // 读取 12 字节的 Elf64_Nhdr 头部 (3 个 UInt)
                val n_namesz = ParseUtils.readUIntFromInputStream(inputStream)
                val n_descsz = ParseUtils.readUIntFromInputStream(inputStream)
                val n_type = ParseUtils.readUIntFromInputStream(inputStream)
                bytesRead += 12

                val nhdr = Elf64_Nhdr(n_namesz, n_descsz, n_type)

                // 读取 name 字段（必须向上对齐到 4 字节的倍数）
                val paddedNamesz = (n_namesz + 3u) and 3u.inv()
                val nameBytes = inputStream.readFully(paddedNamesz.toInt())
                bytesRead += paddedNamesz.toLong()
                val name = String(nameBytes, Charsets.UTF_8).trimEnd('\u0000')

                // 读取 descriptor 描述符字段（必须向上对齐到 4 字节的倍数）
                val paddedDescsz = (n_descsz + 3u) and 3u.inv()
                val descBytes = inputStream.readFully(paddedDescsz.toInt())
                bytesRead += paddedDescsz.toLong()

                // 截取真实的 descsz 长度的描述符内容
                val actualDesc = descBytes.copyOfRange(0, n_descsz.toInt())

                notes.add(Elf64_Note(nhdr, name, actualDesc))
            }
            notes
        }
    }

    /**
     * 自动遍历所有 Note 节区并提取全部 Note 条目
     */
    fun readNotes(elf64File: File): List<Elf64_Note> {
        val noteHeaders = readNoteSectionHeaders(elf64File)
        return buildList {
            noteHeaders.forEach { header ->
                addAll(readNotes(elf64File, header))
            }
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

    private fun InputStream.readFully(n: Int): ByteArray {
        val buffer = ByteArray(n)
        var bytesRead = 0
        while (bytesRead < n) {
            val retval = read(buffer, bytesRead, n - bytesRead)
            if (retval == -1) {
                throw EOFException("Unexpected end of file: expected to read $n bytes, but reached EOF after reading $bytesRead bytes.")
            }
            bytesRead += retval
        }
        return buffer
    }
}
