package com.riraki.elfparser

open class ElfSection(
    private val elfFile: ElfFile,
    val sectionHeader: ElfSectionHeader
) {
    val type: UInt by lazy {
        sectionHeader.sh_type
    }

    val name: String by lazy {
        sectionHeader.nameDescription
    }

    override fun toString(): String {
        return "ElfSection(elfFile=$elfFile, sectionHeader=$sectionHeader)"
    }
}
