package com.uliian.framework.core.utils

import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface

object NetworkUtils {

    fun getAllNetworkInterfaces(): ArrayList<InetAddress> {
        val e = NetworkInterface.getNetworkInterfaces()
        val ips = arrayListOf<InetAddress>()
        for (n in e) {
            for (a in n.inetAddresses) {
                ips.add(a)
            }
        }
        return ips
    }

    fun getAllIps()= getAllNetworkInterfaces().map { it.hostAddress }

    fun getIpv4Ips() = getAllNetworkInterfaces().filterIsInstance<Inet4Address>().map { it.hostAddress }
}