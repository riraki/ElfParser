package com.riraki.elfparser

import java.io.BufferedInputStream
import java.io.File
import java.io.InputStream

class Elf32_Ehdr {
    /**
     * typedef struct
     * {
     *   unsigned char	e_ident[EI_NIDENT];	/* Magic number and other info */
     *   Elf32_Half	e_type;			/* Object file type */
     *   Elf32_Half	e_machine;		/* Architecture */
     *   Elf32_Word	e_version;		/* Object file version */
     *   Elf32_Addr	e_entry;		/* Entry point virtual address */
     *   Elf32_Off	e_phoff;		/* Program header table file offset */
     *   Elf32_Off	e_shoff;		/* Section header table file offset */
     *   Elf32_Word	e_flags;		/* Processor-specific flags */
     *   Elf32_Half	e_ehsize;		/* ELF header size in bytes */
     *   Elf32_Half	e_phentsize;		/* Program header table entry size */
     *   Elf32_Half	e_phnum;		/* Program header table entry count */
     *   Elf32_Half	e_shentsize;		/* Section header table entry size */
     *   Elf32_Half	e_shnum;		/* Section header table entry count */
     *   Elf32_Half	e_shstrndx;		/* Section header string table index */
     * } Elf32_Ehdr;
     */

    @OptIn(ExperimentalUnsignedTypes::class)
    val ei_mag: UByteArray = UByteArray(4)
    val ei_class: UByte
    val ei_data: UByte
    val ei_version: UByte
    val ei_osabi: UByte
    val ei_abiversion: UByte
    @OptIn(ExperimentalUnsignedTypes::class)
    val ei_pad: UByteArray = UByteArray(7)

    val e_type: Elf32_Half
    val e_machine: Elf32_Half
    val e_version: Elf32_Word
    val e_entry: Elf32_Addr
    val e_phoff: Elf32_Off
    val e_shoff: Elf32_Off
    val e_flags: Elf32_Word
    val e_ehsize: Elf32_Half
    val e_phentsize: Elf32_Half
    val e_phnum: Elf32_Half
    val e_shentsize: Elf32_Half
    val e_shnum: Elf32_Half
    val e_shstrndx: Elf32_Half

    @OptIn(ExperimentalUnsignedTypes::class)
    constructor(elf32File: File) {
        BufferedInputStream(elf32File.inputStream()).use { inputStream ->
            readUBytesFromInputStream(inputStream, ei_mag)
            ei_class = inputStream.read().toUByte()
            ei_data = inputStream.read().toUByte()
            ei_version = inputStream.read().toUByte()
            ei_osabi = inputStream.read().toUByte()
            ei_abiversion = inputStream.read().toUByte()
            readUBytesFromInputStream(inputStream, ei_pad)
            e_type = ParseUtils.readUShortFromInputStream(inputStream)
            e_machine = ParseUtils.readUShortFromInputStream(inputStream)
            e_version = ParseUtils.readUIntFromInputStream(inputStream)
            e_entry = ParseUtils.readUIntFromInputStream(inputStream)
            e_phoff = ParseUtils.readUIntFromInputStream(inputStream)
            e_shoff = ParseUtils.readUIntFromInputStream(inputStream)
            e_flags = ParseUtils.readUIntFromInputStream(inputStream)
            e_ehsize = ParseUtils.readUShortFromInputStream(inputStream)
            e_phentsize = ParseUtils.readUShortFromInputStream(inputStream)
            e_phnum = ParseUtils.readUShortFromInputStream(inputStream)
            e_shentsize = ParseUtils.readUShortFromInputStream(inputStream)
            e_shnum = ParseUtils.readUShortFromInputStream(inputStream)
            e_shstrndx = ParseUtils.readUShortFromInputStream(inputStream)
        }
    }

    companion object {
        @OptIn(ExperimentalUnsignedTypes::class)
        private fun readUBytesFromInputStream(inputStream: InputStream, ubytes: UByteArray) {
            val byteArray = ByteArray(ubytes.size)
            var offset = 0
            while (offset < byteArray.size) {
                val count = inputStream.read(byteArray, offset, byteArray.size - offset)
                if (count == -1) throw java.io.EOFException("Unexpected end of ELF identification")
                offset += count
            }
            convertBytesToUBytes(byteArray, ubytes)
        }

        @OptIn(ExperimentalUnsignedTypes::class)
        private fun convertBytesToUBytes(sbytes: ByteArray, ubytes: UByteArray) {
            for (i in sbytes.indices) ubytes[i] = sbytes[i].toUByte()
        }
    }

    fun getElfMachine(): ElfMachine {
        return when (e_machine.toInt()) {
            EM_ARM -> ElfMachine.EM_ARM
            EM_386 -> ElfMachine.EM_386
            else -> throw UnsupportedOperationException("Unsupported ELF32 machine: $e_machine!")
        }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    override fun toString(): String {
        return "Elf32_Ehdr(ei_mag=${FormatHelpers.toHexSpaceString(ei_mag)}" +
                ", ei_class=$ei_class" +
                ", ei_data=$ei_data" +
                ", ei_version=$ei_version" +
                ", ei_osabi=$ei_osabi" +
                ", ei_abiversion=$ei_abiversion" +
                ", ei_pad=${FormatHelpers.toHexSpaceString(ei_pad)}" +
                ", e_type=$e_type, e_machine=$e_machine" +
                ", e_version=$e_version, e_entry=${FormatHelpers.toHexString(e_entry)}" +
                ", e_phoff=$e_phoff, e_shoff=$e_shoff" +
                ", e_flags=$e_flags, e_ehsize=$e_ehsize" +
                ", e_phentsize=$e_phentsize" +
                ", e_phnum=$e_phnum" +
                ", e_shentsize=$e_shentsize" +
                ", e_shnum=$e_shnum" +
                ", e_shstrndx=$e_shstrndx)"
    }
}
