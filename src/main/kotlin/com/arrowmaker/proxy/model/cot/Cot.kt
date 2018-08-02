package com.arrowmaker.proxy.model.cot


/**
 * Cot message
 */
class Cot(
        val callsign: String,
        val uid: String,
        val time: String,
        val runTime: String,
        val staleTime: String,
        val lat: Double,
        val lon: Double) {

    /**
     * xml string representation of a cot object
     */
    fun xml() = "<event version='2.0' uid='$uid' type='a-f-G-U-C' time='$time' " +
            "run='$runTime' stale='$staleTime' how='m-g'><point lat='$lat' lon='$lon' hae='0' " +
            "ce='9999999' le='9999999'/><detail><contact callsign='$callsign'/>" +
            "</detail></event>"

}