package com.riraki.elfparser

import java.io.File

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val file = File("unidbg-android/src/test/java/com/guozilu/class0607", "libnative-lib-arm64-v8a.so")
            val elf64_ehdr: Elf64_Ehdr = Elf64Parser.readElfHeader(file)
            println(elf64_ehdr)
            val elf64_program_headers = Elf64Parser.readProgramHeaders(file)
            println(elf64_program_headers)
            val elf64_section_headers = Elf64Parser.readSectionHeaders(file)
            println(elf64_section_headers)
            val elf64_dynsym_sections = Elf64Parser.readDynsymSections(file)
            println(elf64_dynsym_sections)
            val elf64_dynamic_sections = Elf64Parser.readDynamicSections(file)
            println(elf64_dynamic_sections)
        }
    }
}
