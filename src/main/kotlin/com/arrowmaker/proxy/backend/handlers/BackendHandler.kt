package com.arrowmaker.proxy.backend.handlers

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.DatagramPacket


class BackendHandler : SimpleChannelInboundHandler<DatagramPacket>() {
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: DatagramPacket) {
        println("Received data")
    }
}
