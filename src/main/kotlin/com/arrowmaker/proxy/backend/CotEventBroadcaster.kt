package com.arrowmaker.proxy.backend

import com.arrowmaker.proxy.handlers.NanoToCotEncoder
import com.arrowmaker.proxy.model.nanoMessage.IpHead
import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelOption
import io.netty.channel.socket.nio.NioDatagramChannel
import java.net.InetSocketAddress
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup

/**
 * Client used by server to Broadcast
 */
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

    /**
     * write a message to the channel
     * note: Client must be running prior to calling
     */
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