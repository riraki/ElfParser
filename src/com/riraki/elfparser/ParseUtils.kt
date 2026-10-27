package com.riraki.elfparser

import java.io.EOFException
import java.io.InputStream
import kotlin.toUInt
import kotlin.toUShort

object ParseUtils {
    fun readUShortFromInputStream(inputStream: InputStream, littleEndian: Boolean = true): UShort {
        val b1 = inputStream.read()
        val b2 = inputStream.read()
        if (b1 == -1 || b2 == -1) {
            throw EOFException("Reached end of stream while reading UShort")
        }

        return if (littleEndian) {
            ((b1 and 0xff) or ((b2 and 0xff) shl 8)).toUShort()
        }
        else {
            ((b2 and 0xff) or ((b1 and 0xff) shl 8)).toUShort()
        }
    }

    fun readUIntFromInputStream(inputStream: InputStream, littleEndian: Boolean = true): UInt {
        val b1 = inputStream.read()
        val b2 = inputStream.read()
        val b3 = inputStream.read()
        val b4 = inputStream.read()
        if (b1 == -1 || b2 == -1 || b3 == -1 || b4 == -1) {
            throw EOFException("Reached end of stream while reading UInt")
        }
        return if (littleEndian) {
            ((b1 and 0xff)
                    or ((b2 and 0xff) shl 8)
                    or ((b3 and 0xff) shl 16)
                    or ((b4 and 0xff) shl 24)
                    ).toUInt()
        }
        else {
            ((b4 and 0xff)
                    or ((b3 and 0xff) shl 8)
                    or ((b2 and 0xff) shl 16)
                    or ((b1 and 0xff) shl 24)
                    ).toUInt()
        }
    }

    fun readLongFromInputStream(inputStream: InputStream, littleEndian: Boolean = true): Long {
        val b1 = inputStream.read()
        val b2 = inputStream.read()
        val b3 = inputStream.read()
        val b4 = inputStream.read()
        val b5 = inputStream.read()
        val b6 = inputStream.read()
        val b7 = inputStream.read()
        val b8 = inputStream.read()
        if (b1 == -1 || b2 == -1 || b3 == -1 || b4 == -1 ||
            b5 == -1 || b6 == -1 || b7 == -1 || b8 == -1) {
            throw EOFException("Reached end of stream while reading Long")
        }
        return if (littleEndian) {
            ((b1.toLong() and 0xff)
                    or ((b2.toLong() and 0xff) shl 8)
                    or ((b3.toLong() and 0xff) shl 16)
                    or ((b4.toLong() and 0xff) shl 24)
                    or ((b5.toLong() and 0xff) shl 32)
                    or ((b6.toLong() and 0xff) shl 40)
                    or ((b7.toLong() and 0xff) shl 48)
                    or ((b8.toLong() and 0xff) shl 56)
                    )
        }
        else {
            ((b8.toLong() and 0xff)
                    or ((b7.toLong() and 0xff) shl 8)
                    or ((b6.toLong() and 0xff) shl 16)
                    or ((b5.toLong() and 0xff) shl 24)
                    or ((b4.toLong() and 0xff) shl 32)
                    or ((b3.toLong() and 0xff) shl 40)
                    or ((b2.toLong() and 0xff) shl 48)
                    or ((b1.toLong() and 0xff) shl 56)
                    )
        }
    }

    fun readULongFromInputStream(inputStream: InputStream, littleEndian: Boolean = true): ULong {
        val b1 = inputStream.read()
        val b2 = inputStream.read()
        val b3 = inputStream.read()
        val b4 = inputStream.read()
        val b5 = inputStream.read()
        val b6 = inputStream.read()
        val b7 = inputStream.read()
        val b8 = inputStream.read()
        if (b1 == -1 || b2 == -1 || b3 == -1 || b4 == -1 ||
            b5 == -1 || b6 == -1 || b7 == -1 || b8 == -1) {
            throw EOFException("Reached end of stream while reading ULong")
        }
        return if (littleEndian) {
            ((b1.toLong() and 0xff)
                    or ((b2.toLong() and 0xff) shl 8)
                    or ((b3.toLong() and 0xff) shl 16)
                    or ((b4.toLong() and 0xff) shl 24)
                    or ((b5.toLong() and 0xff) shl 32)
                    or ((b6.toLong() and 0xff) shl 40)
                    or ((b7.toLong() and 0xff) shl 48)
                    or ((b8.toLong() and 0xff) shl 56)
                    ).toULong()
        }
        else {
            ((b8.toLong() and 0xff)
                    or ((b7.toLong() and 0xff) shl 8)
                    or ((b6.toLong() and 0xff) shl 16)
                    or ((b5.toLong() and 0xff) shl 24)
                    or ((b4.toLong() and 0xff) shl 32)
                    or ((b3.toLong() and 0xff) shl 40)
                    or ((b2.toLong() and 0xff) shl 48)
                    or ((b1.toLong() and 0xff) shl 56)
                    ).toULong()
        }
    }
}
