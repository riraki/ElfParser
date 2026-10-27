package com.riraki.elfparser

object FormatHelpers {
    @OptIn(ExperimentalUnsignedTypes::class)
    fun toHexSpaceString(ubytes: UByteArray): String {
        return ubytes.joinToString(", ", "[", "]",
            transform = { byte ->
                (byte.toInt() and 0xff).toString(16).padStart(2, '0').uppercase()
            })
    }

    fun toHexString(value: Byte): String {
        return "0x${value.toString(16).uppercase()}"
    }

    fun toHexString(value: UByte): String {
        return "0x${value.toString(16).uppercase()}"
    }

    fun toHexString(value: Short): String {
        return "0x${value.toString(16).uppercase()}"
    }

    fun toHexString(value: UShort): String {
        return "0x${value.toString(16).uppercase()}"
    }

    fun toHexString(value: Int): String {
        return "0x${value.toString(16).uppercase()}"
    }

    fun toHexString(value: UInt): String {
        return "0x${value.toString(16).uppercase()}"
    }

    fun toHexString(value: Long): String {
        return "0x${value.toString(16).uppercase()}"
    }

    fun toHexString(value: ULong): String {
        return "0x${value.toString(16).uppercase()}"
    }
}
