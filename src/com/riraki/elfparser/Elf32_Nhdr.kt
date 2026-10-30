package com.riraki.elfparser

class Elf32_Nhdr(
    val n_namesz: Elf32_Word,
    val n_descsz: Elf32_Word,
    val n_type: Elf32_Word
) {
    /**
     * typedef struct
     * {
     *   Elf32_Word n_namesz;			/* Length of the note's name.  */
     *   Elf32_Word n_descsz;			/* Length of the note's descriptor.  */
     *   Elf32_Word n_type;			/* Type of the note.  */
     * } Elf32_Nhdr;
     */

    fun typeString(): String {
        return when (n_type) {
            NT_GNU_ABI_TAG -> "NT_GNU_ABI_TAG"
            NT_GNU_HWCAP -> "NT_GNU_HWCAP"
            NT_GNU_BUILD_ID -> "NT_GNU_BUILD_ID"
            NT_GNU_GOLD_VERSION -> "NT_GNU_GOLD_VERSION"
            NT_GNU_PROPERTY_TYPE_0 -> "NT_GNU_PROPERTY_TYPE_0"
            else -> "NT_UNKNOWN(${n_type})"
        }
    }

    override fun toString(): String {
        return "Elf32_Nhdr(" +
                "n_namesz=$n_namesz" +
                ", n_descsz=$n_descsz" +
                ", n_type=${typeString()}" +
                ")"
    }
}
