package com.arrowmaker.proxy.codec

import com.arrowmaker.proxy.model.IpHead
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.ByteToMessageDecoder
import io.netty.handler.codec.MessageToMessageDecoder


class NanoDecoder : MessageToMessageDecoder<DatagramPacket>() {

    override fun decode(ctx: ChannelHandlerContext, `in`: DatagramPacket, out: MutableList<Any>) {
        if (`in`.content().readableBytes() >= 92) {
            val iphead = IpHead.from(`in`.content())
            out.add(iphead)
        }
    }
}