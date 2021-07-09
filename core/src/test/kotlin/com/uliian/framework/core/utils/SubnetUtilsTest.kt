package com.uliian.framework.core.utils

import com.google.common.net.InetAddresses
import org.junit.jupiter.api.Test
import java.net.Inet4Address
import java.net.NetworkInterface
import kotlin.test.assertTrue


class SubnetUtilsTest {

    @Test
    fun subnetTest() {
        val info = SubnetUtils("10.10.10.0", "255.255.255.128").getInfo()
        assertTrue(info.isInRange("10.10.10.10"))
    }

    @Test
    fun subnetCidrTest() {
        val info = SubnetUtils("10.10.10.0/25").getInfo()
        assertTrue(info.isInRange("10.10.10.10"))
    }

    @Test
    fun getIpAddrsTest() {
        val e = NetworkInterface.getNetworkInterfaces()
        for (n in e) {
            for (a in n.inetAddresses) {
                if(!a.isLoopbackAddress&&a is Inet4Address)
                {
                    println(a.hostAddress)
                }
            }
        }
    }

    @Test
    fun getSubNetAddrsTest(){
        val info = SubnetUtils("192.168.9.0/24").getInfo()
        val t = NetworkUtils.getIpv4Ips().filter { info.isInRange(it) }
        println(t)
        val ip = IntArray(4)
        val parts = t[0].split(".").toTypedArray()

        var ipNumbers: Long = 0
        for (i in 0..3) {
            ip[i] = parts[i].toInt()
            ipNumbers += ip[i] shl 24 - 8 * i
        }
        println(ipNumbers)
        println(info.asInteger(t[0]))
        val revertMask =  SubnetUtils("192.168.9.0/24").netmask.inv()
        println(info.asInteger(t[0]) and revertMask)

    }
}