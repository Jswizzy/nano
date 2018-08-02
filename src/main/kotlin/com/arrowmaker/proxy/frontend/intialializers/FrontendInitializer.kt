package com.arrowmaker.proxy.frontend.intialializers

import com.arrowmaker.proxy.codec.NanoDecoder
import com.arrowmaker.proxy.codec.NanoToCotEncoder
import com.arrowmaker.proxy.frontend.handlers.FrontendHandler
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.DatagramChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import java.net.InetSocketAddress


class FrontendInitializer(private val remoteAddress: InetSocketAddress): ChannelInitializer<DatagramChannel>() {
    override fun initChannel(ch: DatagramChannel) {
        ch.pipeline().addLast(
                LoggingHandler(LogLevel.INFO),
                NanoDecoder(),
                NanoToCotEncoder(),
                FrontendHandler(remoteAddress))
    }
}
