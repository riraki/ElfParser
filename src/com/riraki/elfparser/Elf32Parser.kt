package com.riraki.elfparser

import java.io.BufferedInputStream
import java.io.EOFException
import java.io.File
import java.io.InputStream

object Elf32Parser {
    fun readElfHeader(elf32File: File): Elf32_Ehdr {
        return Elf32_Ehdr(elf32File)
    }

    fun readProgramHeaders(elf32File: File, e_phoff: Elf32_Off, e_phnum: Elf32_Half): List<Elf32_Phdr> {
        return BufferedInputStream(elf32File.inputStream()).use { inputStream ->
            inputStream.skipFully(e_phoff.toLong())

            List(e_phnum.toInt()) {
                Elf32_Phdr(
                    ParseUtils.readUIntFromInputStream(inputStream), // p_type
                    ParseUtils.readUIntFromInputStream(inputStream), // p_offset
                    ParseUtils.readUIntFromInputStream(inputStream), // p_vaddr
                    ParseUtils.readUIntFromInputStream(inputStream), // p_paddr
                    ParseUtils.readUIntFromInputStream(inputStream), // p_filesz
                    ParseUtils.readUIntFromInputStream(inputStream), // p_memsz
                    ParseUtils.readUIntFromInputStream(inputStream), // p_flags
                    ParseUtils.readUIntFromInputStream(inputStream)  // p_align
                )
            }
        }
    }

    fun readProgramHeaders(elf32File: File): List<Elf32_Phdr> {
        val ehdr = readElfHeader(elf32File)
        return readProgramHeaders(elf32File, ehdr.e_phoff, ehdr.e_phnum)
    }

    fun readSectionStringTable(elf32File: File): String {
        val ehdr = readElfHeader(elf32File)
        BufferedInputStream(elf32File.inputStream()).use { inputStream ->
            inputStream.skipFully(ehdr.e_shoff.toLong())

            for (i in 0 until ehdr.e_shnum.toInt()) {
                // ELF32_Shdr 前四个字段：name/type/flags/addr，共 16 字节
                inputStream.skipFully(16)
                val sh_offset = ParseUtils.readUIntFromInputStream(inputStream)
                val sh_size = ParseUtils.readUIntFromInputStream(inputStream)

                if (i == ehdr.e_shstrndx.toInt()) {
                    return readStringAt(elf32File, sh_offset.toLong(), sh_size.toInt())
                }

                // 已读取 offset、size，剩余 link/info/addralign/entsize 共 16 字节
                inputStream.skipFully(16)
            }
            throw IllegalStateException("ELF Section String Table not found. e_shstrndx: ${ehdr.e_shstrndx}")
        }
    }

    private fun readStringAt(file: File, offset: Long, size: Int): String {
        return BufferedInputStream(file.inputStream()).use { inputStream ->
            inputStream.skipFully(offset)
            String(inputStream.readFully(size), Charsets.UTF_8)
        }
    }

    fun readDynamicString(elf32File: File): String {
        val sectionHeaders = readSectionHeaders(elf32File)
        val dynamicStringHeader = sectionHeaders.firstOrNull {
            it.getName() == ".dynstr" && it.sh_type == SHT_STRTAB
        } ?: throw IllegalStateException("ELF Dynamic String not found.")
        return readStringAt(
            elf32File,
            dynamicStringHeader.sh_offset.toLong(),
            dynamicStringHeader.sh_size.toInt()
        )
    }

    fun readSectionHeaders(elf32File: File, e_shoff: Elf32_Off, e_shnum: Elf32_Half, stringTable: String): List<Elf32_Shdr> {
        return BufferedInputStream(elf32File.inputStream()).use { inputStream ->
            inputStream.skipFully(e_shoff.toLong())

            List(e_shnum.toInt()) {
                Elf32_Shdr(
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    stringTable
                )
            }
        }
    }

    fun readSectionHeaders(elf32File: File, e_shoff: Elf32_Off, e_shnum: Elf32_Half): List<Elf32_Shdr> {
        return readSectionHeaders(
            elf32File,
            e_shoff,
            e_shnum,
            readSectionStringTable(elf32File)
        )
    }

    fun readSectionHeaders(elf32File: File, stringTable: String): List<Elf32_Shdr> {
        val ehdr = readElfHeader(elf32File)
        return readSectionHeaders(elf32File, ehdr.e_shoff, ehdr.e_shnum, stringTable)
    }

    fun readSectionHeaders(elf32File: File): List<Elf32_Shdr> {
        return readSectionHeaders(elf32File, readSectionStringTable(elf32File))
    }

    fun readDynsymSections(elf32File: File): List<Elf32_Sym> {
        val sectionHeaders = readSectionHeaders(elf32File)
        val dynsymHeader = sectionHeaders.firstOrNull {
            it.getName() == ".dynsym" && it.sh_type == SHT_DYNSYM
        } ?: return emptyList()
        return readDynsymSections(elf32File, dynsymHeader)
    }

    fun readDynsymSections(elf32File: File, elf32_shdr: Elf32_Shdr): List<Elf32_Sym> {
        val sectionName = elf32_shdr.getName()
        if (elf32_shdr.sh_type != SHT_DYNSYM) {
            throw IllegalArgumentException("Invalid section for dynamic symbol table: $sectionName")
        }
        if ((elf32_shdr.sh_flags.toULong() and SHF_ALLOC) == 0uL) {
            throw IllegalArgumentException("Symbol table section must have SHF_ALLOC flag set.")
        }
        if (elf32_shdr.sh_entsize != 16u) {
            throw IllegalArgumentException("Unexpected ELF32 symbol entry size: ${elf32_shdr.sh_entsize}")
        }
        if (elf32_shdr.sh_size % elf32_shdr.sh_entsize != 0u) {
            throw IllegalArgumentException("Invalid dynamic symbol table size")
        }
        val dynamicString = readDynamicString(elf32File)

        return BufferedInputStream(elf32File.inputStream()).use { inputStream ->
            inputStream.skipFully(elf32_shdr.sh_offset.toLong())
            List((elf32_shdr.sh_size / elf32_shdr.sh_entsize).toInt()) {
                Elf32_Sym(
                    ParseUtils.readUIntFromInputStream(inputStream), // st_name
                    ParseUtils.readUIntFromInputStream(inputStream), // st_value
                    ParseUtils.readUIntFromInputStream(inputStream), // st_size
                    inputStream.readCheckedByte().toUByte(),         // st_info
                    inputStream.readCheckedByte().toUByte(),         // st_other
                    ParseUtils.readUShortFromInputStream(inputStream),// st_shndx
                    dynamicString
                )
            }
        }
    }

    fun readSymtabSections(elf32File: File, symtabSectionHeader: Elf32_Shdr): List<Elf32_Sym> {
        if (symtabSectionHeader.sh_type != SHT_SYMTAB) {
            throw IllegalArgumentException("Invalid section type for .symtab: ${symtabSectionHeader.sh_type}")
        }

        val sectionHeaders = readSectionHeaders(elf32File)
        val stringTableIndex = symtabSectionHeader.sh_link.toInt()
        require(stringTableIndex in sectionHeaders.indices) {
            "Invalid .symtab string table index: $stringTableIndex"
        }
        val stringTableHeader = sectionHeaders[stringTableIndex]
        val stringTable = readStringTable(elf32File, stringTableHeader)

        require(symtabSectionHeader.sh_entsize == 16u) {
            "Unexpected ELF32 symbol entry size: ${symtabSectionHeader.sh_entsize}"
        }
        require(symtabSectionHeader.sh_size % symtabSectionHeader.sh_entsize == 0u) {
            "Invalid static symbol table size"
        }

        return BufferedInputStream(elf32File.inputStream()).use { inputStream ->
            inputStream.skipFully(symtabSectionHeader.sh_offset.toLong())
            List((symtabSectionHeader.sh_size / symtabSectionHeader.sh_entsize).toInt()) {
                Elf32_Sym(
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    inputStream.readCheckedByte().toUByte(),
                    inputStream.readCheckedByte().toUByte(),
                    ParseUtils.readUShortFromInputStream(inputStream),
                    stringTable
                )
            }
        }
    }

    fun readSymtabSections(elf32File: File): List<Elf32_Sym> {
        val symtabHeader = readSectionHeaders(elf32File).firstOrNull {
            it.sh_type == SHT_SYMTAB
        } ?: return emptyList()
        return readSymtabSections(elf32File, symtabHeader)
    }

    fun readStringTable(elf32File: File, strtabSectionHeader: Elf32_Shdr): String {
        if (strtabSectionHeader.sh_type != SHT_STRTAB) {
            throw IllegalArgumentException("Invalid section type for string table: ${strtabSectionHeader.sh_type}")
        }
        val sectionName = strtabSectionHeader.getName()
        if (sectionName != ".strtab") {
            println("Warning: String table section name is '$sectionName', expected '.strtab'")
        }
        return readStringAt(
            elf32File,
            strtabSectionHeader.sh_offset.toLong(),
            strtabSectionHeader.sh_size.toInt()
        )
    }

    fun readStringTable(elf32File: File): String? {
        val strtabHeader = readSectionHeaders(elf32File).firstOrNull {
            it.getName() == ".strtab" && it.sh_type == SHT_STRTAB
        } ?: return null
        return readStringTable(elf32File, strtabHeader)
    }

    fun readDynamicSections(elf32File: File, elf32_shdr: Elf32_Shdr): List<Elf32_Dyn> {
        val sectionName = elf32_shdr.getName()
        if (elf32_shdr.sh_type != SHT_DYNAMIC) {
            throw IllegalArgumentException("Invalid section for .dynamic: $sectionName")
        }
        if (sectionName != ".dynamic") {
            println("Warning: SHT_DYNAMIC section name is '$sectionName', expected '.dynamic'")
        }
        if ((elf32_shdr.sh_flags.toULong() and SHF_ALLOC) == 0uL) {
            throw IllegalArgumentException("Dynamic section must have SHF_ALLOC flag set.")
        }
        if (elf32_shdr.sh_entsize == 8u) {
            throw IllegalArgumentException("Unexpected ELF32 dynamic entry size: ${elf32_shdr.sh_entsize}")
        }
        if (elf32_shdr.sh_size % elf32_shdr.sh_entsize == 0u) {
            throw IllegalArgumentException("Invalid dynamic section size")
        }
        val dynamicString = readDynamicString(elf32File)

        return BufferedInputStream(elf32File.inputStream()).use { inputStream ->
            inputStream.skipFully(elf32_shdr.sh_offset.toLong())

            List((elf32_shdr.sh_size / elf32_shdr.sh_entsize).toInt()) {
                Elf32_Dyn(
                    ParseUtils.readIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    dynamicString
                )
            }
        }
    }

    fun readDynamicSections(elf32File: File): List<Elf32_Dyn> {
        val dynamicHeader = readSectionHeaders(elf32File).firstOrNull {
            it.getName() == ".dynamic" && it.sh_type == SHT_DYNAMIC
        } ?: return emptyList()
        return readDynamicSections(elf32File, dynamicHeader)
    }

    fun readRelocationSection(elf32File: File, relSectionOffset: Elf32_Off, relSectionSize: Elf32_Word,
        relSectionEntsize: Elf32_Word): List<Elf32_Rel> {
        if (relSectionEntsize == 8u) {
            throw IllegalArgumentException("Unexpected ELF32_Rel entry size: $relSectionEntsize")
        }
        if (relSectionSize % relSectionEntsize == 0u) {
            throw IllegalArgumentException("Invalid REL section size")
        }

        val machine = readElfHeader(elf32File).getElfMachine()
        val dynsym = readDynsymSections(elf32File)

        return BufferedInputStream(elf32File.inputStream()).use { inputStream ->
            inputStream.skipFully(relSectionOffset.toLong())
            List((relSectionSize / relSectionEntsize).toInt()) {
                Elf32_Rel(
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    machine,
                    dynsym
                )
            }
        }
    }

    fun readRelocationSection(elf32File: File, relSectionHeader: Elf32_Shdr): List<Elf32_Rel> {
        if (relSectionHeader.sh_type != SHT_REL) {
            throw IllegalArgumentException("Argument relSectionHeader.sh_type must be SHT_REL!")
        }
        return readRelocationSection(
            elf32File,
            relSectionHeader.sh_offset,
            relSectionHeader.sh_size,
            relSectionHeader.sh_entsize
        )
    }

    fun readRelocationSections(elf32File: File, relSectionHeaders: List<Elf32_Shdr>): List<Elf32_Rel> = buildList {
        relSectionHeaders.forEach {
            addAll(readRelocationSection(elf32File, it))
        }
    }

    fun readRelocationSections(elf32File: File): List<Elf32_Rel> {
        val headers = readSectionHeaders(elf32File).filter { it.sh_type == SHT_REL }
        return readRelocationSections(elf32File, headers)
    }

    fun readRelocationAppendSection(elf32File: File, relaSectionOffset: Elf32_Off, relaSectionSize: Elf32_Word,
        relaSectionEntsize: Elf32_Word): List<Elf32_Rela> {
        if (relaSectionEntsize == 12u) {
           throw IllegalArgumentException("Unexpected ELF32_Rela entry size: $relaSectionEntsize")
        }
        if (relaSectionSize % relaSectionEntsize == 0u) {
            throw IllegalArgumentException("Invalid RELA section size")
        }
        val machine = readElfHeader(elf32File).getElfMachine()
        val dynsym = readDynsymSections(elf32File)
        return BufferedInputStream(elf32File.inputStream()).use { inputStream ->
            inputStream.skipFully(relaSectionOffset.toLong())
            List((relaSectionSize / relaSectionEntsize).toInt()) {
                Elf32_Rela(
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream),
                    ParseUtils.readUIntFromInputStream(inputStream).toInt(),
                    machine,
                    dynsym
                )
            }
        }
    }

    fun readRelocationAppendSection(elf32File: File, relaSectionHeader: Elf32_Shdr): List<Elf32_Rela> {
        if (relaSectionHeader.sh_type != SHT_RELA) {
            throw IllegalArgumentException("Argument relaSectionHeader.sh_type must be SHT_RELA!")
        }
        return readRelocationAppendSection(
            elf32File,
            relaSectionHeader.sh_offset,
            relaSectionHeader.sh_size,
            relaSectionHeader.sh_entsize
        )
    }

    fun readRelocationAppendSections(elf32File: File, relaSectionHeaders: List<Elf32_Shdr>): List<Elf32_Rela> = buildList {
        relaSectionHeaders.forEach { addAll(readRelocationAppendSection(elf32File, it)) }
    }

    fun readRelocationAppendSections(elf32File: File): List<Elf32_Rela> {
        val headers = readSectionHeaders(elf32File).filter { it.sh_type == SHT_RELA }
        return readRelocationAppendSections(elf32File, headers)
    }

    fun readNoteSectionHeaders(elf32File: File): List<Elf32_Shdr> {
        return readSectionHeaders(elf32File).filter { it.sh_type == SHT_NOTE }
    }

    fun readNotes(elf32File: File, noteSectionHeader: Elf32_Shdr): List<Elf32_Note> {
        if (noteSectionHeader.sh_type != SHT_NOTE) {
            throw IllegalArgumentException("Invalid section type for note section: ${noteSectionHeader.sh_type}")
        }
        return BufferedInputStream(elf32File.inputStream()).use { inputStream ->
            inputStream.skipFully(noteSectionHeader.sh_offset.toLong())
            val notes = mutableListOf<Elf32_Note>()
            var bytesRead = 0L
            val sectionSize = noteSectionHeader.sh_size.toLong()

            while (bytesRead < sectionSize) {
                if (sectionSize - bytesRead < 12L) {
                    throw EOFException("Incomplete Elf32_Nhdr at end of NOTE section")
                }
                val n_namesz = ParseUtils.readUIntFromInputStream(inputStream)
                val n_descsz = ParseUtils.readUIntFromInputStream(inputStream)
                val n_type = ParseUtils.readUIntFromInputStream(inputStream)
                bytesRead += 12
                val nhdr = Elf32_Nhdr(n_namesz, n_descsz, n_type)

                val paddedNamesz = (n_namesz + 3u) and 3u.inv()
                val nameBytes = inputStream.readFully(paddedNamesz.toInt())
                bytesRead += paddedNamesz.toLong()
                val name = String(
                    nameBytes.copyOfRange(0, n_namesz.toInt()),
                    Charsets.UTF_8
                ).trimEnd('\u0000')

                val paddedDescsz = (n_descsz + 3u) and 3u.inv()
                val descBytes = inputStream.readFully(paddedDescsz.toInt())
                bytesRead += paddedDescsz.toLong()
                val actualDesc = descBytes.copyOfRange(0, n_descsz.toInt())
                notes.add(Elf32_Note(nhdr, name, actualDesc))
            }
            notes
        }
    }

    fun readNotes(elf32File: File): List<Elf32_Note> {
        return buildList {
            readNoteSectionHeaders(elf32File).forEach {
                addAll(readNotes(elf32File, it))
            }
        }
    }

    private fun InputStream.skipFully(n: Long) {
        if (n <= 0) return
        var remaining = n
        while (remaining > 0) {
            val skipped = skip(remaining)
            if (skipped > 0L) {
                remaining -= skipped
            } else {
                if (read() == -1) throw EOFException("Unexpected EOF while skipping $n bytes")
                remaining--
            }
        }
    }

    private fun InputStream.readFully(n: Int): ByteArray {
        if (n < 0) {
            throw IllegalArgumentException("Negative read length: $n")
        }

        val buffer = ByteArray(n)
        var bytesRead = 0
        while (bytesRead < n) {
            val count = read(buffer, bytesRead, n - bytesRead)
            if (count == -1) {
                throw EOFException("Unexpected end of file: expected $n bytes, read $bytesRead")
            }
            if (count == 0) {
                val value = read()
                if (value == -1) throw EOFException("Unexpected end of file")
                buffer[bytesRead++] = value.toByte()
            }
            else {
                bytesRead += count
            }
        }
        return buffer
    }

    private fun InputStream.readCheckedByte(): Int {
        val value = read()
        if (value == -1) throw EOFException("Unexpected end of file while reading byte")
        return value
    }
}
