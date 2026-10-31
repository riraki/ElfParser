package com.riraki.elfparser

import java.io.File

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // val file = File("src", "libnative-lib-armeabi-v7a.so")
            val file = File("src", "libnative-lib-arm64-v8a.so")
            // val file = File("src", "libart.so")
            // val file = File("src", "libart64.so")
            val elfFile: ElfFile = ElfFile.from(file)

            val elfHeader = elfFile.elfHeader
            println(elfHeader)

            println("-".repeat(250))
            elfFile.sectionHeaders.forEach {
                println(it)
            }

            println("-".repeat(250))
            elfFile.sections.forEach {
                println(it)
            }

            println("-".repeat(250))
            println(elfFile.stringTable)

            println("-".repeat(250))
            println(elfFile.dynamicStringTable)

            println("-".repeat(250))
            println(elfFile.dynamicSymbolTableSection)

            println("-".repeat(250))
            println(elfFile.symbolTableSection)

            println("-".repeat(250))
            println(elfFile.dynamicSection)

            println("-".repeat(250))
            elfFile.allSymbols.forEach {
                println(it)
            }

            println("-".repeat(250))
            println(elfFile.isLittleEndian)

            println("-".repeat(250))
            println(elfFile.isBigEndian)

            println("-".repeat(250))
            println(elfFile.is32Bits)

            println("-".repeat(250))
            println(elfFile.is64Bits)

            println("-".repeat(250))
            println(elfFile.getSectionHeader(elfHeader.e_shstrndx.toInt()))

            println("-".repeat(250))
            println(elfFile.getSectionHeader(ElfSectionConstants.NAME_SHSTRTAB))

            println("-".repeat(250))
            elfFile.getSectionHeaders(ElfSectionConstants.SHT_STRTAB).forEach {
                println(it)
            }

            println("-".repeat(250))
            println(elfFile.getSection(elfHeader.e_shstrndx.toInt()))

            println("-".repeat(250))
            elfFile.getSections(ElfSectionConstants.SHT_STRTAB).forEach {
                println(it)
            }

            println("-".repeat(250))
            elfFile.getSections(ElfStringTableSection::class.java).forEach {
                println(it)
            }

            println("-".repeat(250))
            println(elfFile.getSymbol("__cxa_finalize"))

            println("-".repeat(250))
            println(elfFile.getProgramHeader(1))

            println("-".repeat(250))
            elfFile.getProgramHeader(ElfProgramConstants.PT_LOAD).forEach {
                println(it)
            }
        }
    }
}
