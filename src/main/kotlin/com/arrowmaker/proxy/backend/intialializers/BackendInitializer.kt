package com.arrowmaker.proxy.backend.intialializers

import com.arrowmaker.proxy.backend.handlers.BackendHandler
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler


class BackendInitializer: ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel) {
        ch.pipeline().addLast(
                LoggingHandler(LogLevel.INFO),
                BackendHandler()
        )
    }
}