package com.arrowmaker.proxy.handlers

import com.arrowmaker.proxy.model.nanoMessage.IpHead
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.MessageToMessageDecoder


/**
 * Decodes incoming Datagram packets to IpHead objects
 */
class NanoDecoder : MessageToMessageDecoder<DatagramPacket>() {

    override fun decode(ctx: ChannelHandlerContext, `in`: DatagramPacket, out: MutableList<Any>) {
        if (`in`.content().readableBytes() >= 92) {
            val iphead = IpHead.from(`in`.content())
            out.add(iphead)
        }
    }
}