package com.arrowmaker.proxy.handlers

import com.arrowmaker.proxy.model.cot.Cot
import com.arrowmaker.proxy.model.nanoMessage.IpHead
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.MessageToMessageEncoder
import io.netty.util.CharsetUtil
import java.net.InetSocketAddress
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


/**
 * Encodes an IpHead object into a DatagramPacket containing a COT message
 */
class NanoToCotEncoder(private val remoteAddress: InetSocketAddress) : MessageToMessageEncoder<IpHead>() {
    override fun encode(ctx: ChannelHandlerContext, msg: IpHead, out: MutableList<Any>) {
        with (msg) {
            //transform Nano Epoch seconds to Cot formatted time
            val timeString = epochToCotString(time)
            val staleTime = epochToCotString(time) { it.plusMinutes(15) }

            with (main.p3) {

                //create a cot xml string
                val cot = Cot(
                        callsign = "Nano",
                        uid = imei.toString(),
                        time = timeString,
                        runTime = timeString,
                        staleTime = staleTime,
                        lat = lat,
                        lon = lon).xml()

                println("Sending msg: $cot")

                //transform cot string into a ByteBuf
                val bytes = cot.toByteArray(CharsetUtil.UTF_8)
                val buf = ctx.alloc().buffer(bytes.size)
                buf.writeBytes(bytes)

                out.add(DatagramPacket(buf, remoteAddress))
            }
        }
    }
}

//format datetime to cot time string
private fun dateTimeToCotString(dateTime: LocalDateTime): String =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
                .format(dateTime)

//convert nano epoch time to LocalDateTime
private fun epochToDateTime(seconds: Int): LocalDateTime = LocalDateTime.ofEpochSecond(seconds.toLong(), 0, ZoneOffset.UTC)

//convert nano epoch time to cot time string with optional transformation
private fun epochToCotString(seconds: Int, transformer: (LocalDateTime) -> LocalDateTime = { it }): String =
        dateTimeToCotString(transformer(epochToDateTime(seconds)))

