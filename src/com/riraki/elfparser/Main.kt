package com.riraki.elfparser

import java.io.File

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val file = File("src", "libnative-lib-arm64-v8a.so")
            // val file = File("/home/ignite/Desktop/android_sys_libs/android13/com.android.art/lib64", "libart.so")
            val elf64_ehdr: Elf64_Ehdr = Elf64Parser.readElfHeader(file)
            println(elf64_ehdr)
            val elf64_program_headers = Elf64Parser.readProgramHeaders(file)
            println(elf64_program_headers)
            val elf64_section_headers = Elf64Parser.readSectionHeaders(file)
            println(elf64_section_headers)
            val elf64_dynsym_sections = Elf64Parser.readDynsymSections(file)
            println(elf64_dynsym_sections)
            val elf64_dynamic_sections = Elf64Parser.readDynamicSections(file)
            // println(elf64_dynamic_sections)
            elf64_dynamic_sections.forEach { elf64_dynamic_section ->
                println(elf64_dynamic_section)
            }

            // sh_offset=1448, sh_size=72, sh_entsize=24


            // sh_offset=1520, sh_size=168, sh_entsize=24
        }
    }
}
