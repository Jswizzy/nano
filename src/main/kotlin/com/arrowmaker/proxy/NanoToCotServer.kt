package com.arrowmaker.proxy

import com.arrowmaker.proxy.frontend.FrontendServer
import io.netty.channel.ChannelFuture
import io.netty.channel.nio.NioEventLoopGroup
import java.net.InetSocketAddress


class NanoToCotServer(private val localPort: Int, private val remoteAddress: InetSocketAddress) {
    fun start() {
         println("Proxying *: $localPort to ${remoteAddress.address}:${remoteAddress.port}...")

        val bossGroup = NioEventLoopGroup(1)
        val workerGroup = NioEventLoopGroup()
        try {
            val channelFuture = FrontendServer.start(bossGroup, workerGroup, remoteAddress, localPort)
            val future = channelFuture.bind(localPort)
            future.addListener {
                if (it.isSuccess) {
                    println("Server bound")
                } else {
                    System.err.println("Bind attempt failed")
                    it as ChannelFuture
                    it.cause().printStackTrace()
                }
            }
        } finally {
            bossGroup.shutdownGracefully().syncUninterruptibly()
            workerGroup.shutdownGracefully().syncUninterruptibly()
        }
    }
}