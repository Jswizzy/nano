package com.arrowmaker.proxy.handlers

import com.arrowmaker.proxy.backend.IBroadcast
import com.arrowmaker.proxy.model.nanoMessage.IpHead
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler


/**
 * Event handler that takes an msg object and broadcasts it using the provided Broadcaster client
 */
class NanoEventHandler(private val iBroadcast: IBroadcast<IpHead>) : SimpleChannelInboundHandler<IpHead>() {
    override fun channelRead0(ctx: ChannelHandlerContext, msg: IpHead) {
        //when event msg is read the broadcast method on the Broadcast client is called
        iBroadcast.broadcast(msg)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext,
                                 cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}
