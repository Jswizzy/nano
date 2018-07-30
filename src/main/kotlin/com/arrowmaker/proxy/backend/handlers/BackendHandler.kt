package com.arrowmaker.proxy.backend.handlers

import io.netty.buffer.ByteBuf
import io.netty.channel.*


class BackendHandler : SimpleChannelInboundHandler<ByteBuf>() {
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: ByteBuf?) {
        println("Received data")
    }
}
