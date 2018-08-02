package com.arrowmaker.proxy.handlers

import com.arrowmaker.proxy.model.IpHead
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.MessageToMessageEncoder
import io.netty.util.CharsetUtil
import java.net.InetSocketAddress
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class NanoToCotEncoder(private val remoteAddress: InetSocketAddress) : MessageToMessageEncoder<IpHead>() {
    override fun encode(ctx: ChannelHandlerContext, msg: IpHead, out: MutableList<Any>) {
        with (msg) {
            val timeString = epochToCotString(time)
            val staleTime = epochToCotString(time) { it.plusMinutes(15) }

            with (main.p3) {

                val cot = "<event version='2.0' uid='$imei' type='a-f-G-U-C' time='$timeString' " +
                        "run='$timeString' stale='$staleTime' how='m-g'><point lat='$lat' lon='$lon' hae='0' " +
                        "ce='9999999' le='9999999'/><detail><contact callsign='Nano'/>" +
                        "</detail></event>"
                println("Sending msg: $cot")

                val bytes = cot.toByteArray(CharsetUtil.UTF_8)
                val buf = ctx.alloc().buffer(bytes.size)
                buf.writeBytes(bytes)

                out.add(DatagramPacket(buf, remoteAddress))
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

