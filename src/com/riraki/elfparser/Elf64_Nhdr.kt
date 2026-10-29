package com.riraki.elfparser

class Elf64_Nhdr(
    val n_namesz: Elf64_Word,
    val n_descsz: Elf64_Word,
    val n_type: Elf64_Word
) {
    /**
     * typedef struct
     * {
     *   Elf64_Word n_namesz;			/* Length of the note's name.  */
     *   Elf64_Word n_descsz;			/* Length of the note's descriptor.  */
     *   Elf64_Word n_type;			/* Type of the note.  */
     * } Elf64_Nhdr;
     */

    override fun toString(): String {
        return "Elf64_Nhdr(" +
                "n_namesz=$n_namesz" +
                ", n_descsz=$n_descsz" +
                ", n_type=$n_type" +
                ")"
    }
}
