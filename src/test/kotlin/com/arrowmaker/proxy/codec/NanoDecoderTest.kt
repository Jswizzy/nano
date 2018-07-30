package com.arrowmaker.proxy.codec

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.embedded.EmbeddedChannel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class NanoDecoderTest {
    @Test
    fun `Nano to message`() {
        val buf = Unpooled.buffer()
        for (i in 0..8) {
            buf.writeByte(i)
        }
        val input = buf.duplicate()
        val channel = EmbeddedChannel(NanoDecoder())
        // write bytes
        assertTrue(channel.writeInbound(input.retain()))
        assertTrue(channel.finish())

        // read messages
        val read = channel.readInbound<Any>() as ByteBuf
        assertEquals(buf, read)
        read.release()
    }
}