package com.arrowmaker.proxy.codec

import com.arrowmaker.proxy.model.IpHead
import com.arrowmaker.proxy.model.Main
import com.arrowmaker.proxy.model.P3
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufUtil
import io.netty.buffer.Unpooled
import io.netty.channel.embedded.EmbeddedChannel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class NanoDecoderTest {
    @Test
    fun `Nano to message`() {
        val bytes = ByteBufUtil.decodeHexDump("01005901001C42D9D427333030323334303131383331333530020C6F00005B59C3970200370001110FAD9FEC36FC7900000164D6A3E2B0000000003FCE147B40" +
                "437280204BCFE2C0535C09E69E2F2B42C2B9DB000000003E0B55B190")
        val expected = IpHead(1,89,1,28,1121571879,"300234011831350",2,3183,0,1532609431, Main(2, 55, P3(0, 1.61f, 38.8945351, -77.4381043, 97.363f, 0.0f, 0.13606907f)))

        val buf = Unpooled.copiedBuffer(bytes)
        val input = buf.duplicate()
        val channel = EmbeddedChannel(NanoDecoder())
        // write bytes
        assertTrue(channel.writeInbound(input.retain()))
        assertTrue(channel.finish())

        // read messages
        val read = channel.readInbound<IpHead>()

        //println(read)

        assertEquals(expected, read)
        buf.release()
    }
}