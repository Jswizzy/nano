package com.arrowmaker.proxy.codec

import com.arrowmaker.proxy.model.IpHead
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class NanoToCotEncoder : MessageToByteEncoder<IpHead>() {
    override fun encode(ctx: ChannelHandlerContext, msg: IpHead, out: ByteBuf) {
        with (msg) {
            val timeString = epochToCotString(time)
            val staleTime = epochToCotString(time) { it.plusMinutes(15) }

            with (main.p3) {

                val cot = "<event version='2.0' uid='$imei' type='a-f-G-U-C' time='$timeString' " +
                        "start='$timeString' stale='$staleTime' how='m-g'><point lat='$lat' lon='$lon' hae='0' " +
                        "ce='9999999' le='9999999'/><detail><contact callsign='Nano'/>" +
                        "</detail></event>"
                out.writeCharSequence(cot, Charset.forName("UTF-8"))
            }
        }
    }
}

private fun dateTimeToCotString(dateTime: LocalDateTime): String =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
                .format(dateTime)

private fun epochToDateTime(seconds: Int): LocalDateTime = LocalDateTime.ofEpochSecond(seconds.toLong(), 0, ZoneOffset.UTC)

private fun epochToCotString(seconds: Int, transformer: (LocalDateTime) -> LocalDateTime = { it }): String =
        dateTimeToCotString(transformer(epochToDateTime(seconds)))

