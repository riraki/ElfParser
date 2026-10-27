package com.riraki.elfparser

import java.io.BufferedInputStream
import java.io.File
import java.io.InputStream

class Elf64_Ehdr {
    /**
     * typedef struct
     * {
     *   unsigned char	e_ident[EI_NIDENT];	/* Magic number and other info */
     *   Elf64_Half	e_type;			/* Object file type */
     *   Elf64_Half	e_machine;		/* Architecture */
     *   Elf64_Word	e_version;		/* Object file version */
     *   Elf64_Addr	e_entry;		/* Entry point virtual address */
     *   Elf64_Off	e_phoff;		/* Program header table file offset */
     *   Elf64_Off	e_shoff;		/* Section header table file offset */
     *   Elf64_Word	e_flags;		/* Processor-specific flags */
     *   Elf64_Half	e_ehsize;		/* ELF header size in bytes */
     *   Elf64_Half	e_phentsize;		/* Program header table entry size */
     *   Elf64_Half	e_phnum;		/* Program header table entry count */
     *   Elf64_Half	e_shentsize;		/* Section header table entry size */
     *   Elf64_Half	e_shnum;		/* Section header table entry count */
     *   Elf64_Half	e_shstrndx;		/* Section header string table index */
     * } Elf64_Ehdr;
     */

    // private val e_ident: UByteArray = UByteArray(EI_NIDENT)     // ELF 文件的身份信息

    // ELF 魔数，用于识别文件格式
    @OptIn(ExperimentalUnsignedTypes::class)
    val ei_mag: UByteArray = UByteArray(4)

    // ELF64，表示 64 位格式类别
    val ei_class: UByte

    // 字节序
    val ei_data: UByte

    // ELF 标识版本为当前版本
    val ei_version: UByte

    // System V ABI 标识
    val ei_osabi: UByte

    // ABI 版本字段为 0
    val ei_abiversion: UByte

    // 保留填充字节
    @OptIn(ExperimentalUnsignedTypes::class)
    val ei_pad: UByteArray = UByteArray(7)

    val e_type: Elf64_Half                                  // 文件类型
    val e_machine: Elf64_Half                               // 目标架构
    val e_version: Elf64_Word                               // 文件版本
    val e_entry: Elf64_Addr                                 // 程序入口地址
    val e_phoff: Elf64_Off                                  // 程序头表起始偏移
    val e_shoff: Elf64_Off                                  // 节头表起始偏移
    val e_flags: Elf64_Word                                 // 处理器相关标志
    val e_ehsize: Elf64_Half                                // ELF Header 自身的大小
    val e_phentsize: Elf64_Half                             // 每个程序头条目的大小
    val e_phnum: Elf64_Half                                 // 程序头条目数量
    val e_shentsize: Elf64_Half                             // 每个节头条目的大小
    val e_shnum: Elf64_Half                                 // 节头条目数量
    val e_shstrndx: Elf64_Half                              // 节名称字符串表的索引



    @OptIn(ExperimentalUnsignedTypes::class)
    constructor(elf64File: File) {
        BufferedInputStream(elf64File.inputStream()).use { inputStream ->
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
            e_entry = ParseUtils.readULongFromInputStream(inputStream)
            e_phoff = ParseUtils.readULongFromInputStream(inputStream)
            e_shoff = ParseUtils.readULongFromInputStream(inputStream)
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
        private const val EI_NIDENT: Int = 16

        @OptIn(ExperimentalUnsignedTypes::class)
        private fun readUBytesFromInputStream(inputStream: InputStream, ubytes: UByteArray) {
            val byteArray: ByteArray = ByteArray(ubytes.size)
            inputStream.read(byteArray, 0, ubytes.size)
            convertBytesToUBytes(byteArray, ubytes)
        }

        @OptIn(ExperimentalUnsignedTypes::class)
        private fun convertBytesToUBytes(sbytes: ByteArray, sOff: Int, ubytes: UByteArray, uOff: Int, size: Int) {
            for (i in 0..<size) {
                ubytes[uOff + i] = sbytes[sOff + i].toUByte()
            }
        }

        @OptIn(ExperimentalUnsignedTypes::class)
        private fun convertBytesToUBytes(sbytes: ByteArray, ubytes: UByteArray) {
            if (sbytes.size == ubytes.size) {
                convertBytesToUBytes(sbytes, 0, ubytes, 0, sbytes.size)
            }
            else {
                throw IllegalArgumentException("ByteArray size (${sbytes.size}) must match UByteArray size (${ubytes.size})")
            }
        }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    override fun toString(): String {
        return "Elf64_Ehdr(ei_mag=${FormatHelpers.toHexSpaceString(ei_mag)}" +
                ", ei_class=$ei_class" +
                ", ei_data=$ei_data" +
                ", ei_version=$ei_version" +
                ", ei_osabi=$ei_osabi" +
                ", ei_abiversion=$ei_abiversion" +
                ", ei_pad=${FormatHelpers.toHexSpaceString(ei_pad)}" +
                ", e_type=$e_type, e_machine=$e_machine" +
                ", e_version=$e_version, e_entry=$e_entry" +
                ", e_phoff=$e_phoff, e_shoff=$e_shoff" +
                ", e_flags=$e_flags, e_ehsize=$e_ehsize" +
                ", e_phentsize=$e_phentsize" +
                ", e_phnum=$e_phnum" +
                ", e_shentsize=$e_shentsize" +
                ", e_shnum=$e_shnum" +
                ", e_shstrndx=$e_shstrndx)"
    }
}
