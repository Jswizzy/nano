package com.arrowmaker.proxy.model.nanoMessage

import io.netty.buffer.ByteBuf
import java.nio.charset.Charset


/**
 * IpHead of Nano message, has a Main and Payload
 */
data class IpHead(
        val revNum: Int,
        val messageLen: Int,
        val moIei: Int = 0x01,
        val moHeaderLen: Int = 28,
        val cdrRefId: Int,
        val imei: CharSequence,
        val status: Int,
        val momsn: Int,
        val mtmsn: Int,
        val time: Int,
        val main: Main) {
    companion object {
        /**
         * Factory method for creating IpHead from a ByteBuf
         */
        fun from(byteBuf: ByteBuf): IpHead {
            with(byteBuf) {
                val rev = readByte().toInt()
                val messageLen = readUnsignedShort()
                val moHeaderIei = readByte().toInt()
                require(moHeaderIei == 0x01)
                val mmHeaderLen = readUnsignedShort()
                val cdrRefId = readInt()
                val imei = readCharSequence(15, Charset.forName("UTF-8"))
                val status = readByte().toInt()
                val momsn = readUnsignedShort()
                val mtmsn = readUnsignedShort()
                val time = readInt()

                val payload = Main.p3(byteBuf)

                return IpHead(rev, messageLen, moHeaderIei, mmHeaderLen, cdrRefId, imei, status, momsn, mtmsn, time, payload)
            }
        }
    }
}