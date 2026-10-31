package com.riraki.elfparser

import java.io.BufferedInputStream

class ElfDynamicSection(
    elfFile: ElfFile,
    dynamicSectionHeader: ElfSectionHeader
) : ElfSection(elfFile, dynamicSectionHeader) {

    val entries: Array<ElfDynamicStructure> by lazy {
        val stringTableSectionHeader: ElfSectionHeader = elfFile.sectionHeaders[dynamicSectionHeader.sh_link.toInt()]
        val stringTable = ElfStringTableSection(elfFile, stringTableSectionHeader)
        BufferedInputStream(elfFile.file.inputStream()).use { inputStream ->
            InputStreamHelpers.skipFully(inputStream, dynamicSectionHeader.sh_offset.toLong())
            Array((dynamicSectionHeader.sh_size / dynamicSectionHeader.sh_entsize).toInt()) {
                ElfDynamicStructure(
                    ElfParserHelpers.readIntOrLong(inputStream, elfFile.elfHeader.is32Bits(), elfFile.elfHeader.isLittleEndian()),
                    ElfParserHelpers.readUIntOrULong(inputStream, elfFile.elfHeader.is32Bits(), elfFile.elfHeader.isLittleEndian()),
                    stringTable
                )
            }
        }
    }

    override fun toString(): String {
        return "ElfDynamicSection(" +
                "entryCount=${entries.size}" +
                ", entries=${entries.contentToString()}" +
                ")"
    }
}
