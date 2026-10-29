package com.riraki.elfparser

data class Elf64_Sym(
    val st_name: Elf64_Word,                // 符号名称在字符串表中的偏移
    val st_info: UByte,                     // 符号绑定属性和符号类型
    val st_other: UByte,                    // 符号可见性等信息
    val st_shndx: Elf64_Section,            // 符号所属节的索引或特殊索引
    val st_value: Elf64_Addr,               // 符号值，通常用于表示地址或其他符号相关数值
    val st_size: Elf64_Xword,               // 符号对应实体的大小
    private val dynamicString: String       // elf 文件存放动态符号的名称字符，也就是 .dynsym
) {
    /**
     * typedef struct
     * {
     *   Elf64_Word	st_name;		/* Symbol name (string tbl index) */
     *   unsigned char	st_info;		/* Symbol type and binding */
     *   unsigned char st_other;		/* Symbol visibility */
     *   Elf64_Section	st_shndx;		/* Section index */
     *   Elf64_Addr	st_value;		/* Symbol value */
     *   Elf64_Xword	st_size;		/* Symbol size */
     * } Elf64_Sym;
     */

    private fun nameString(): String {
        val startIndex = st_name.toInt()
        val endIndex = dynamicString.indexOf('\u0000', startIndex)

        if (endIndex == -1) {
            println(dynamicString)
            throw IllegalStateException("Invalid string table: missing null terminator at offset $startIndex")
        }

        return dynamicString.substring(startIndex, endIndex)
    }

    private fun infoString(): String {
        val info = st_info.toInt()
        val binding = info shr 4
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

    override fun toString(): String {
        return "Elf64_Sym(st_name=\"${nameString()}\"" +
                ", st_info=\"${infoString()}\"" +
                ", st_other=$st_other" +
                ", st_shndx=$st_shndx" +
                ", st_value=$st_value" +
                ", st_size=$st_size)"
    }
}
