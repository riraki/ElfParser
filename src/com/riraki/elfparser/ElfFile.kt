package com.riraki.elfparser

import java.io.File

class ElfFile {
    val file: File

    val elfHeader: ElfHeader by lazy {
        ElfHeader(this)
    }

    val programHeaders: Array<ElfProgramHeader> by lazy {
        Array(elfHeader.e_phnum.toInt()) {
            val programHeaderOffset = elfHeader.e_phoff + it.toULong() * elfHeader.e_phentsize
            ElfProgramHeader(this, programHeaderOffset)
        }
    }

    val sectionHeaders: Array<ElfSectionHeader> by lazy {
        Array(elfHeader.e_shnum.toInt()) {
            val sectionHeaderOffset = elfHeader.e_shoff + it.toULong() * elfHeader.e_shentsize
            ElfSectionHeader(this, sectionHeaderOffset, mStringTable)
        }
    }

    val sections: Array<ElfSection> by lazy {
        Array(sectionHeaders.size) {
            when (sectionHeaders[it].sh_type) {
                ElfSectionConstants.SHT_STRTAB -> ElfStringTableSection(this, sectionHeaders[it])
                ElfSectionConstants.SHT_SYMTAB
                    , ElfSectionConstants.SHT_DYNSYM -> ElfSymbolTableSection(this, sectionHeaders[it])
                ElfSectionConstants.SHT_DYNAMIC -> ElfDynamicSection(this, sectionHeaders[it])
                ElfSectionConstants.SHT_RELA -> ElfRelocationAppendSection(this, sectionHeaders[it])
                ElfSectionConstants.SHT_REL -> ElfRelocationSection(this, sectionHeaders[it])
                ElfSectionConstants.SHT_HASH -> ElfHashSection(this, sectionHeaders[it])
                ElfSectionConstants.SHT_GNU_HASH -> ElfGnuHashSection(this, sectionHeaders[it], mStringTable)
                ElfSectionConstants.SHT_NOTE -> ElfNoteSection(this, sectionHeaders[it])
                else -> ElfSection(this, sectionHeaders[it])
            }
        }
    }

    private val mStringTable: ElfStringTableSection by lazy {
        val stringTableSectionHeaderOffset = elfHeader.e_shoff + elfHeader.e_shstrndx * elfHeader.e_shentsize
        ElfStringTableSection(
            this,
            ElfSectionHeader(
                this,
                stringTableSectionHeaderOffset,
                null
            )
        )
    }

    fun getStringTable(): ElfStringTableSection {
        return sections[elfHeader.e_shstrndx.toInt()] as? ElfStringTableSection
            ?: throw IllegalArgumentException("Index ${elfHeader.e_shstrndx} is not an ElfStringTable")
    }

    val isLittleEndian: Boolean
        get() = elfHeader.isLittleEndian

    val isBigEndian: Boolean
        get() = elfHeader.isBigEndian

    val is32Bits: Boolean
        get() = elfHeader.is32Bits

    val is64Bits: Boolean
        get() = elfHeader.is64Bits

    private constructor(file: File) {
        this.file = file
    }

    companion object {
        fun from(elfFile: File): ElfFile {
            return ElfFile(elfFile)
        }
    }
}
