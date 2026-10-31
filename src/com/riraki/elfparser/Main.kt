package com.riraki.elfparser

import java.io.File

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // val file = File("src", "libnative-lib-arm64-v8a.so")
            // val file = File("src", "libnative-lib-armeabi-v7a.so")
            val file = File("/home/ignite/Desktop/android_sys_libs/android13/com.android.art/lib64", "libart.so")
            val elfFile: ElfFile = ElfFile.from(file)
            // println(elfFile.elfHeader)
            elfFile.sections.forEach {
                println(it)
            }
            println(elfFile.dynamicStringTable)
            println(elfFile.sectionHeaders.contentToString())
            for (i in elfFile.sectionHeaders.indices) {
                println(elfFile.sectionHeaders[i])
            }
            println(elfFile.stringTable)
            elfFile.programHeaders.forEach { println(it) }
        }
    }
}
