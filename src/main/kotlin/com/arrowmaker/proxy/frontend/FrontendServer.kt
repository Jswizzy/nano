package com.arrowmaker.proxy.frontend

import com.arrowmaker.proxy.frontend.intialializers.FrontendInitializer
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.socket.nio.NioDatagramChannel
import java.net.InetSocketAddress


object FrontendServer {
    fun start(group: EventLoopGroup, remoteAddress: InetSocketAddress, localPort: Int): Bootstrap {
        val bootstrap = Bootstrap()
        bootstrap.group(group)
                .channel(NioDatagramChannel::class.java)
                .handler(FrontendInitializer(remoteAddress))
                .option(ChannelOption.SO_BROADCAST, true)
                .bind(localPort).sync().channel().closeFuture().sync()
        return bootstrap
    }
}