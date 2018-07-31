package com.arrowmaker.proxy.frontend.handlers

import com.arrowmaker.proxy.backend.BackendClient
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import java.net.InetSocketAddress


class FrontendHandler(private val remoteAddress: InetSocketAddress) : SimpleChannelInboundHandler<ByteBuf>() {
    private lateinit var channelFuture: ChannelFuture

    override fun channelActive(ctx: ChannelHandlerContext) {
        val client = BackendClient()
        channelFuture = client.start(ctx, remoteAddress)
    }

    override fun channelRead0(ctx: ChannelHandlerContext, msg: ByteBuf) {
        if (channelFuture.isDone) {
            channelFuture.channel().writeAndFlush(msg) //proxy
        }
    }
}
