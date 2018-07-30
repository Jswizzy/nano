package com.arrowmaker.proxy.codec

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder


class NanoDecoder: ByteToMessageDecoder() {
    override fun decode(ctx: ChannelHandlerContext, `in`: ByteBuf, out: MutableList<Any>) {
        val byteCount = `in`.readableBytes()
        val buffer = `in`.readBytes(byteCount)
        out.add(buffer)
    }
}