package com.arrowmaker.proxy.handlers

import com.arrowmaker.proxy.backend.IBroadcast
import com.arrowmaker.proxy.model.IpHead
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler


class NanoEventHandler(private val iBroadcast: IBroadcast<IpHead>) : SimpleChannelInboundHandler<IpHead>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: IpHead) {
        iBroadcast.broadcast(msg)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext,
                                 cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}
