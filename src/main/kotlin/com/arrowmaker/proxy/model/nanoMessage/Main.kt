package com.arrowmaker.proxy.model.nanoMessage

import io.netty.buffer.ByteBuf


/**
 * Main of a Nano msg has a payload
 */
data class Main(
        val iei: Int = 0x02,
        val len: Int = 55,
        val p3: P3) {
    companion object {

        /**
         * Factory method for creating Payload Main from a ByteBuf
         */
        fun p3(byteBuf: ByteBuf): Main {
            with(byteBuf) {
                val moIei = readByte().toInt()
                require(moIei == 0x02)
                val moLen = readUnsignedShort()

                skipBytes(18)

                val p3 = P3.from(byteBuf)

                skipBytes(1)

                return Main(moIei, moLen, p3)
            }
        }
    }
}