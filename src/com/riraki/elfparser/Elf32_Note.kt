package com.riraki.elfparser

data class Elf32_Note(
    val header: Elf32_Nhdr,
    val name: String,
    val descriptor: ByteArray
) {
    /**
     * 如果这是一个 Build ID 类型的 Note，将其描述符转为可读的 16 进制字符串
     */
    fun getBuildIdHex(): String {
        if (header.n_type == NT_GNU_BUILD_ID && name == "GNU") {
            return descriptor.joinToString("") { byte -> (byte.toInt() and 0xFF).toString(16).padStart(2, '0') }
        }
        return ""
    }

    override fun toString(): String {
        val detail = if (header.n_type == NT_GNU_BUILD_ID) {
            "buildId=${getBuildIdHex()}"
        }
        else {
            "descSize=${descriptor.size} bytes"
        }
        return "Elf32_Note(name=\"$name\", type=${header.typeString()}, $detail)"
    }
}
