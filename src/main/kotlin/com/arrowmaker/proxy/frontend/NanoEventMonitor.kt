package com.arrowmaker.proxy.frontend

import com.arrowmaker.proxy.backend.IBroadcast
import com.arrowmaker.proxy.handlers.NanoEventHandler
import com.arrowmaker.proxy.handlers.NanoDecoder
import com.arrowmaker.proxy.model.IpHead
import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioDatagramChannel
import java.net.InetSocketAddress


class NanoEventMonitor(address: InetSocketAddress, iBroadcast: IBroadcast<IpHead>) {
    private val group = NioEventLoopGroup()
    private val bootstrap = Bootstrap()

    init {
        bootstrap.group(group)
                .channel(NioDatagramChannel::class.java)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(object : ChannelInitializer<Channel>() {
                    override fun initChannel(ch: Channel) {
                        ch.pipeline().addLast(
                                NanoDecoder(),
                                NanoEventHandler(iBroadcast))
                    }
                })
                .localAddress(address)
    }

    fun bind(): Channel = bootstrap.bind().syncUninterruptibly().channel()

    fun stop() {
        group.shutdownGracefully()
    }
}