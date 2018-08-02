package com.arrowmaker.proxy

import com.arrowmaker.proxy.backend.CotEventBroadcaster
import com.arrowmaker.proxy.frontend.NanoEventMonitor
import java.net.InetSocketAddress


class NanoToCotServer(private val localPort: Int, private val remoteAddress: InetSocketAddress) {
    private val cotEventBroadcaster = CotEventBroadcaster(remoteAddress)
    private val nanoEventMonitor = NanoEventMonitor(InetSocketAddress(localPort), cotEventBroadcaster)

    fun start() {
        println("Proxying *: $localPort to ${remoteAddress.address}:${remoteAddress.port}...")

        try {
            cotEventBroadcaster.run()
            bindNanoEventMonitor()
        } finally {
            cotEventBroadcaster.stop()
        }
    }

    private fun bindNanoEventMonitor() {
        try {
            val channel = nanoEventMonitor.bind()
            println("NanoEventMonitor running")
            channel.closeFuture().sync()
        } finally {
            nanoEventMonitor.stop()
        }
    }
}