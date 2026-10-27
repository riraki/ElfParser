package com.riraki.elfparser

data class Elf64_Dyn(
    val d_tag: Elf64_Sxword,
    val d_un: ULong
) {
    /**
     * typedef struct
     * {
     *   Elf64_Sxword	d_tag;			/* Dynamic entry type */
     *   union
     *     {
     *       Elf64_Xword d_val;		/* Integer value */
     *       Elf64_Addr d_ptr;			/* Address value */
     *     } d_un;
     * } Elf64_Dyn;
     */
}
