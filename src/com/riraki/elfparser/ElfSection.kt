package com.riraki.elfparser

open class ElfSection(
    private val elfFile: ElfFile,
    private val sectionHeader: ElfSectionHeader
) {
    override fun toString(): String {
        return "ElfSection(elfFile=$elfFile, sectionHeader=$sectionHeader)"
    }
}
