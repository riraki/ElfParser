package com.riraki.elfparser

data class Elf32_Sym(
    val st_name: Elf32_Word,                // 符号名称在字符串表中的偏移
    val st_value: Elf32_Addr,               // 符号值，通常用于表示地址或其他符号相关数值
    val st_size: Elf32_Word,                // 符号对应实体的大小
    val st_info: UByte,                     // 符号绑定属性和符号类型
    val st_other: UByte,                    // 符号可见性等信息
    val st_shndx: Elf32_Section,            // 符号所属节的索引或特殊索引
    private val dynamicString: String?      // 符号对应的字符串表（.dynstr 或 .strtab）
) {
    /**
     * typedef struct {
     *   Elf32_Word st_name;
     *   Elf32_Addr st_value;
     *   Elf32_Word st_size;
     *   unsigned char st_info;
     *   unsigned char st_other;
     *   Elf32_Half st_shndx;
     * } Elf32_Sym;
     */

    fun getName(): String = nameString()

    private fun nameString(): String {
        return if (dynamicString != null) {
            val startIndex = st_name.toInt()
            if (startIndex !in dynamicString.indices) {
                if (st_name == 0u && dynamicString.isNotEmpty() && dynamicString[0] == '\u0000') return ""
                throw IllegalStateException("Invalid string table offset $startIndex")
            }
            val endIndex = dynamicString.indexOf('\u0000', startIndex)
            if (endIndex == -1) {
                throw IllegalStateException("Invalid string table: missing null terminator at offset $startIndex")
            }
            dynamicString.substring(startIndex, endIndex)
        } else {
            FormatHelpers.toHexString(st_name)
        }
    }

    private fun infoString(): String {
        val info = st_info.toInt()
        val binding = info ushr 4
        val type = info and 0x0f

        val bindingStr = when (binding) {
            STB_LOCAL -> "STB_LOCAL"
            STB_GLOBAL -> "STB_GLOBAL"
            STB_WEAK -> "STB_WEAK"
            STB_NUM -> "STB_NUM"
            STB_LOOS -> "STB_LOOS"
            STB_GNU_UNIQUE -> "STB_GNU_UNIQUE"
            STB_HIOS -> "STB_HIOS"
            STB_LOPROC -> "STB_LOPROC"
            STB_HIPROC -> "STB_HIPROC"
            else -> "STB_UNKNOWN($binding)"
        }

        val typeStr = when (type) {
            STT_NOTYPE -> "STT_NOTYPE"
            STT_OBJECT -> "STT_OBJECT"
            STT_FUNC -> "STT_FUNC"
            STT_SECTION -> "STT_SECTION"
            STT_FILE -> "STT_FILE"
            STT_COMMON -> "STT_COMMON"
            STT_TLS -> "STT_TLS"
            STT_NUM -> "STT_NUM"
            STT_LOOS -> "STT_LOOS"
            STT_GNU_IFUNC -> "STT_GNU_IFUNC"
            STT_HIOS -> "STT_HIOS"
            STT_LOPROC -> "STT_LOPROC"
            STT_HIPROC -> "STT_HIPROC"
            else -> "STT_UNKNOWN($type)"
        }
        return "$bindingStr|$typeStr"
    }

    private fun otherString(): String {
        return when (st_other.toInt() and 0x03) {
            0 -> "STV_DEFAULT"
            1 -> "STV_INTERNAL"
            2 -> "STV_HIDDEN"
            3 -> "STV_PROTECTED"
            else -> "STV_UNKNOWN"
        }
    }

    override fun toString(): String {
        return "Elf32_Sym(st_name=\"${nameString()}\"" +
                ", st_value=${FormatHelpers.toHexString(st_value)}" +
                ", st_size=$st_size" +
                ", st_info=\"${infoString()}\"" +
                ", st_other=\"${otherString()}\"" +
                ", st_shndx=$st_shndx)"
    }
}
