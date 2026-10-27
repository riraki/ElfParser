package com.riraki.elfparser

data class Elf64_Phdr(
    val p_type: Elf64_Word,     // 段的类型
    val p_flags: Elf64_Word,    // 读、写、执行权限
    val p_offset: Elf64_Off,    // 对应内容在文件中的偏移
    val p_vaddr: Elf64_Addr,    // 对应内容的虚拟地址
    val p_paddr: Elf64_Addr,    // 物理地址字段
    val p_filesz: Elf64_Xword,  // 文件中对应内容的大小
    val p_memsz: Elf64_Xword,   // 内存中对应区域的大小
    val p_align: Elf64_Xword    // 对齐要求
) {
    /**
     * typedef struct
     * {
     *   Elf64_Word	p_type;			/* Segment type */
     *   Elf64_Word	p_flags;		/* Segment flags */
     *   Elf64_Off	p_offset;		/* Segment file offset */
     *   Elf64_Addr	p_vaddr;		/* Segment virtual address */
     *   Elf64_Addr	p_paddr;		/* Segment physical address */
     *   Elf64_Xword	p_filesz;		/* Segment size in file */
     *   Elf64_Xword	p_memsz;		/* Segment size in memory */
     *   Elf64_Xword	p_align;		/* Segment alignment */
     * } Elf64_Phdr;
     */

    private fun typeString(): String {
        return when (p_type) {
            PT_NULL -> "PT_NULL"
            PT_LOAD -> "PT_LOAD"
            PT_DYNAMIC -> "PT_DYNAMIC"
            PT_INTERP -> "PT_INTERP"
            PT_NOTE -> "PT_NOTE"
            PT_SHLIB -> "PT_SHLIB"
            PT_PHDR -> "PT_PHDR"
            PT_TLS -> "PT_TLS"
            PT_NUM -> "PT_NUM"
            PT_LOOS -> "PT_LOOS"
            PT_GNU_EH_FRAME -> "PT_GNU_EH_FRAME"
            PT_GNU_STACK -> "PT_GNU_STACK"
            PT_GNU_RELRO -> "PT_GNU_RELRO"
            PT_GNU_PROPERTY -> "PT_GNU_PROPERTY"
            PT_GNU_SFRAME -> "PT_GNU_SFRAME"
            PT_SUNWBSS -> "PT_SUNWBSS"
            PT_SUNWSTACK -> "PT_SUNWSTACK"
            PT_HIOS -> "PT_HIOS"
            PT_LOPROC -> "PT_LOPROC"
            PT_HIPROC -> "PT_HIPROC"
            else -> "PT_UNKNOWN($p_type)"
        }
    }

    private fun flagsString(): String {
        return buildList {
            if ((p_flags and PF_X) != 0u) {
                add("PF_X")
            }
            if ((p_flags and PF_W) != 0u) {
                add("PF_W")
            }
            if ((p_flags and PF_R) != 0u) {
                add("PF_R")
            }
            if ((p_flags and PF_MASKOS) != 0u) {
                add("PF_MASKOS")
            }
            if ((p_flags and PF_MASKPROC) != 0u) {
                add("PF_MASKPROC")
            }
        }.ifEmpty { listOf("PF_UNKNOWN($p_flags)") }.joinToString("|")
    }

    override fun toString(): String {
        return "Elf64_Phdr(p_type=${typeString()}" +
                ", p_flags=${flagsString()}" +
                ", p_offset=$p_offset" +
                ", p_vaddr=$p_vaddr" +
                ", p_paddr=$p_paddr" +
                ", p_filesz=$p_filesz" +
                ", p_memsz=$p_memsz" +
                ", p_align=$p_align)"
    }
}
