package com.arrowmaker.proxy

import com.arrowmaker.proxy.handlers.NanoToCotEncoder
import io.netty.bootstrap.Bootstrap
import io.netty.buffer.ByteBufUtil
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelOption
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramPacket
import io.netty.channel.socket.nio.NioDatagramChannel
import io.netty.util.internal.SocketUtils
import java.net.InetSocketAddress


fun main(args: Array<String>) {
    val group = NioEventLoopGroup()
    val bootstap = Bootstrap()
    bootstap.group(group)
            .channel(NioDatagramChannel::class.java)
            .option(ChannelOption.SO_BROADCAST, true)
            .handler(object: SimpleChannelInboundHandler<DatagramPacket>() {
                override fun channelRead0(ctx: ChannelHandlerContext?, msg: DatagramPacket?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })

    try {
        val ch = bootstap.bind(0).sync().channel()

        val bytes = ByteBufUtil.decodeHexDump("01005901001C42D9D427333030323334303131383331333530020C6F00005B59C3970200370001110FAD9FEC36FC7900000164D6A3E2B0000000003FCE147B40" +
                "437280204BCFE2C0535C09E69E2F2B42C2B9DB000000003E0B55B190")
        val buf = Unpooled.copiedBuffer(bytes)

        ch.writeAndFlush(DatagramPacket(buf, SocketUtils.socketAddress("255.255.255.255", port))).sync()

        if (!ch.closeFuture().await(5000)) {
            System.err.println("request timed out")
        }
    } finally {
        group.shutdownGracefully()
    }
}

private val port = 8080

val msg = "01005901001C42D6BFA3333030323334303131383331333530000C2900005B59AFFA0200370001110FAD9FEC36FC7900000164D6572A10000000004110000040\n" +
        "43728CD48F38EEC0535C0C9F4AB382425D645A419B26273EC70CB411"