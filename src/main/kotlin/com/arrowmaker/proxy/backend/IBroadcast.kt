package com.arrowmaker.proxy.backend

import io.netty.channel.ChannelFuture


interface IBroadcast<T> {
    fun broadcast(msg: T): ChannelFuture
}