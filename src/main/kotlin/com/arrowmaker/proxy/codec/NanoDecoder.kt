package com.arrowmaker.proxy.codec

import com.arrowmaker.proxy.model.IpHead
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder


class NanoDecoder : ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext, `in`: ByteBuf, out: MutableList<Any>) {

            val iphead = IpHead.from(`in`)
            out.add(iphead)
    }
}