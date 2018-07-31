package com.arrowmaker.proxy.model

import io.netty.buffer.ByteBuf

data class P3(
        val brevity: Int,
        val pdop: Float,
        val lat: Double,
        val lon: Double,
        val alt: Float,
        val crs: Float,
        val spd: Float
) {
    companion object {
        fun from(byteBuf: ByteBuf): P3 {
            with(byteBuf) {
                val brevity = readInt()
                val pdop = readFloat()
                val lat = readDouble()
                val lon = readDouble()
                val alt = readFloat()
                val crs = readFloat()
                val spd = readFloat()

                return P3(brevity, pdop, lat, lon, alt, crs, spd)
            }
        }
    }
}