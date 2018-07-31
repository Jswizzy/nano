package com.arrowmaker.proxy.codec

import com.arrowmaker.proxy.model.IpHead
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufUtil
import io.netty.buffer.Unpooled
import io.netty.channel.embedded.EmbeddedChannel
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.nio.charset.Charset


class NanoToCotEncoderTest {
    @Test
    fun `Epoch to Cot`() {
        val bytes = ByteBufUtil.decodeHexDump("01005901001C42D9D427333030323334303131383331333530020C6F00005B59C3970200370001110FAD9FEC36FC7900000164D6A3E2B0000000003FCE147B40" +
                "437280204BCFE2C0535C09E69E2F2B42C2B9DB000000003E0B55B190")
        val buf = Unpooled.copiedBuffer(bytes)

        val nanoMsg = IpHead.from(buf)

        val channel = EmbeddedChannel(NanoToCotEncoder())
        assertTrue(channel.writeOutbound(nanoMsg))
        assertTrue(channel.finish())

        val outbound = channel.readOutbound<ByteBuf>()
        println(ByteBufUtil.prettyHexDump(outbound))

        val len = outbound.readableBytes()
        val cot = outbound.getCharSequence(0, len, Charset.forName("UTF-8"))
        println(cot)

        outbound.release()

        buf.release()
    }
}