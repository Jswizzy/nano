package com.arrowmaker.proxy.backend

import com.arrowmaker.proxy.handlers.NanoToCotEncoder
import com.arrowmaker.proxy.model.IpHead
import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelOption
import io.netty.channel.socket.nio.NioDatagramChannel
import java.net.InetSocketAddress
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup


class CotEventBroadcaster(remoteAddress: InetSocketAddress): IBroadcast<IpHead> {
    private val group: EventLoopGroup = NioEventLoopGroup()
    private val bootstrap: Bootstrap = Bootstrap()
    private var channel: Channel? = null

    init {
        bootstrap
                .group(group)
                .channel(NioDatagramChannel::class.java)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(NanoToCotEncoder(remoteAddress))
    }

    override fun broadcast(msg: IpHead) =
            channel?.writeAndFlush(msg)
                    ?: error("CotEventBroadcaster must be running prior to broadcasting a message")


    fun run() {
        channel = bootstrap.bind(0).sync().channel()
    }

    fun stop() {
        group.shutdownGracefully()
    }
}