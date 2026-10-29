package com.riraki.elfparser

data class Elf64_Note(
    val header: Elf64_Nhdr,
    val name: String,
    val descriptor: ByteArray
) {
    /**
     * 如果这是一个 Build ID 类型的 Note，将其描述符转为可读的 16 进制字符串
     */
    fun getBuildIdHex(): String {
        if (header.n_type == NT_GNU_BUILD_ID && name == "GNU") {
            return FormatHelpers.toHexSpaceString(descriptor)
        }
        return ""
    }

    fun typeString(): String {
        return when (name) {
            "GNU" -> when (header.n_type) {
                NT_GNU_ABI_TAG -> "NT_GNU_ABI_TAG"
                NT_GNU_HWCAP -> "NT_GNU_HWCAP"
                NT_GNU_BUILD_ID -> "NT_GNU_BUILD_ID"
                NT_GNU_GOLD_VERSION -> "NT_GNU_GOLD_VERSION"
                NT_GNU_PROPERTY_TYPE_0 -> "NT_GNU_PROPERTY_TYPE_0"
                else -> "NT_GNU_UNKNOWN(${header.n_type})"
            }

            "Android" -> when (header.n_type) {
                1u -> "NT_VERSION"
                else -> "NT_ANDROID_UNKNOWN(${header.n_type})"
            }

            else -> "NT_UNKNOWN(${header.n_type})"
        }
    }

    override fun toString(): String {
        val detail = if (header.n_type == NT_GNU_BUILD_ID) {
            "buildId=${getBuildIdHex()}"
        }
        else {
            "descSize=${descriptor.size} bytes"
        }
        return "Elf64_Note(name=\"$name\", type=${typeString()}, $detail)"
    }
}
