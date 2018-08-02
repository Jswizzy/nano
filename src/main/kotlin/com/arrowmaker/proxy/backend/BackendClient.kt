package com.arrowmaker.proxy.backend

import com.arrowmaker.proxy.backend.intialializers.BackendInitializer
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelOption
import io.netty.channel.socket.nio.NioDatagramChannel
import java.net.InetSocketAddress


class BackendClient {
    fun start(ctx: ChannelHandlerContext, remoteAddress: InetSocketAddress): ChannelFuture {
        val bootStrap = Bootstrap()
        bootStrap
                .group(ctx.channel().eventLoop())
                .channel(NioDatagramChannel::class.java)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(BackendInitializer())
                return bootStrap.connect(remoteAddress)
    }
}