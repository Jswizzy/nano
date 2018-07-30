package com.arrowmaker.proxy.frontend

import com.arrowmaker.proxy.frontend.intialializers.FrontendInitializer
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import java.net.InetSocketAddress


object FrontendServer {
    fun start(bossGroup: EventLoopGroup, workerGroup: EventLoopGroup, remoteAddress: InetSocketAddress, localPort: Int): ServerBootstrap {
        val serverBootstrap = ServerBootstrap()
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .handler(LoggingHandler(LogLevel.INFO))
                .childHandler(FrontendInitializer(remoteAddress))
                .childOption(ChannelOption.AUTO_READ, false)
                .bind(localPort).sync().channel().closeFuture().sync()
        return serverBootstrap
    }
}