package com.arrowmaker.proxy.frontend.intialializers

import com.arrowmaker.proxy.frontend.handlers.FrontendHandler
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import java.net.InetSocketAddress


class FrontendInitializer(private val remoteAddress: InetSocketAddress): ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel) {
        ch.pipeline().addLast(
                LoggingHandler(LogLevel.INFO),
                FrontendHandler(remoteAddress))
    }
}
