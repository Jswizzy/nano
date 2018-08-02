package com.arrowmaker.proxy.handlers

import com.arrowmaker.proxy.model.nanoMessage.IpHead
import io.netty.buffer.ByteBufUtil
import io.netty.buffer.Unpooled
import io.netty.channel.embedded.EmbeddedChannel
import io.netty.channel.socket.DatagramPacket
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.net.InetSocketAddress
import java.nio.charset.Charset


class NanoToCotEncoderTest {
    @Test
    fun `Epoch to Cot`() {
        val bytes = ByteBufUtil.decodeHexDump("01005901001C42D9D427333030323334303131383331333530020C6F00005B59C3970200370001110FAD9FEC36FC7900000164D6A3E2B0000000003FCE147B40" +
                "437280204BCFE2C0535C09E69E2F2B42C2B9DB000000003E0B55B190")
        val buf = Unpooled.copiedBuffer(bytes)

        val nanoMsg = IpHead.from(buf)
        val remoteAddress = InetSocketAddress(8080)

        val channel = EmbeddedChannel(NanoToCotEncoder(remoteAddress))
        assertTrue(channel.writeOutbound(nanoMsg))
        assertTrue(channel.finish())

        val outbound = channel.readOutbound<DatagramPacket>()

        val len = outbound.content().readableBytes()
        val cot = outbound.content().getCharSequence(0, len, Charset.forName("UTF-8"))

        assertEquals("<event version='2.0' uid='300234011831350' type='a-f-G-U-C' time='2018-07-26T12:50:31.00Z' run='2018-07-26T12:50:31.00Z' stale='2018-07-26T13:05:31.00Z' how='m-g'><point lat='38.8945351' lon='-77.4381043' hae='0' ce='9999999' le='9999999'/><detail><contact callsign='Nano'/></detail></event>", cot)
        outbound.release()
        buf.release()
    }
}