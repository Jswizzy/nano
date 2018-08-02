package com.arrowmaker.proxy.backend

import io.netty.channel.ChannelFuture


/**
 * Broadcast interface for broadcast clients to implement that allows a server to tell them to broadcast a message to
 * remote server
 */
interface IBroadcast<T> {
    /**
     * Send Message to a remote server
     */
    fun broadcast(msg: T): ChannelFuture
}