package com.uliian.framework.core.utils

import com.baomidou.mybatisplus.core.toolkit.StringUtils
import javax.servlet.http.HttpServletRequest

/**
 * 获取域名URL
 * @param request
 * @return 域名URL（加schema）
 */
fun getDomainUrl(request: HttpServletRequest): String {
    val xScheme = request.getHeader("X-Scheme")
    val scheme = if (xScheme?.isNotEmpty() == true) xScheme else request.scheme
    val serverName = request.serverName
    return String.format("%s://%s/", scheme, serverName)
}

fun getIpAdrress(request: HttpServletRequest): String {
    val Xip = request.getHeader("X-Real-IP")
    var XFor = request.getHeader("X-Forwarded-For")
    if (XFor?.isNotEmpty() == true && !"unKnown".equals(XFor, ignoreCase = true)) {
        //多次反向代理后会有多个ip值，第一个ip才是真实ip
        val index = XFor.indexOf(",")
        return if (index != -1) {
            XFor.substring(0, index)
        } else {
            XFor
        }
    }
    XFor = Xip
    if (XFor?.isNotEmpty() == true && !"unKnown".equals(XFor, ignoreCase = true)) {
        return XFor
    }
    if (StringUtils.isBlank(XFor) || "unknown".equals(XFor, ignoreCase = true)) {
        XFor = request.getHeader("Proxy-Client-IP")
    }
    if (StringUtils.isBlank(XFor) || "unknown".equals(XFor, ignoreCase = true)) {
        XFor = request.getHeader("WL-Proxy-Client-IP")
    }
    if (StringUtils.isBlank(XFor) || "unknown".equals(XFor, ignoreCase = true)) {
        XFor = request.getHeader("HTTP_CLIENT_IP")
    }
    if (StringUtils.isBlank(XFor) || "unknown".equals(XFor, ignoreCase = true)) {
        XFor = request.getHeader("HTTP_X_FORWARDED_FOR")
    }
    if (StringUtils.isBlank(XFor) || "unknown".equals(XFor, ignoreCase = true)) {
        XFor = request.remoteAddr
    }
    return XFor
}