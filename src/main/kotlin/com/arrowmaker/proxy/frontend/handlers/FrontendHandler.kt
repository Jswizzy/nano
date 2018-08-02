package com.arrowmaker.proxy.frontend.handlers

import com.arrowmaker.proxy.backend.BackendClient
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.DatagramPacket
import java.net.InetSocketAddress


class FrontendHandler(private val remoteAddress: InetSocketAddress) : SimpleChannelInboundHandler<DatagramPacket>() {
    private lateinit var channelFuture: ChannelFuture

    override fun channelActive(ctx: ChannelHandlerContext) {
        val client = BackendClient()
        channelFuture = client.start(ctx, remoteAddress)
    }

    override fun channelRead0(ctx: ChannelHandlerContext, msg: DatagramPacket) {
        if (channelFuture.isDone) {
            val future = channelFuture.channel().writeAndFlush(DatagramPacket(msg.content(), remoteAddress, msg.sender())) //proxy
            future.addListener { ChannelFutureListener.CLOSE }
        }
    }
}
