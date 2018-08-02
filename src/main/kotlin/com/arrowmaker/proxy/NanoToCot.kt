package com.arrowmaker.proxy

import com.arrowmaker.proxy.backend.CotEventBroadcaster
import com.arrowmaker.proxy.frontend.NanoEventMonitor
import java.net.InetSocketAddress


/**
 * Class to start the server, nanoEventMonitor, and client, cotEventBroadcaster, and manage them
 */
class NanoToCot(private val localPort: Int, private val remoteAddress: InetSocketAddress) {
    private val cotEventBroadcaster = CotEventBroadcaster(remoteAddress)
    private val nanoEventMonitor = NanoEventMonitor(InetSocketAddress(localPort), cotEventBroadcaster)

    /**
     * starts the server and client
     */
    fun start() {
        println("Proxying *: $localPort to ${remoteAddress.address}:${remoteAddress.port}...")

        try {
            cotEventBroadcaster.run()
            bindNanoEventMonitor()
        } finally {
            cotEventBroadcaster.stop()
        }
    }

    //start the server
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