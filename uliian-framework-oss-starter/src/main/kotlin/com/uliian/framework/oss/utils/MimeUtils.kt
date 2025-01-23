package com.uliian.framework.oss.utils

object MimeUtils {
    private val MIME_MAP = hashMapOf<String, String>(
        ".jpg" to "image/jpeg",
        ".jpeg" to "image/jpeg",
        ".mp3" to "audio/mpeg",
        ".mpeg" to "video/mpeg",
        ".png" to "image/png",
        ".pdf" to "application/pdf",
        ".weba" to "audio/webm",
        ".webm" to "video/webm",
        ".webp" to "image/webp",
        ".mp4" to "video/mp4",
        ".txt" to "text/plain",
        ".gif" to "image/gif",
        ".bin" to "application/octet-stream",
        ".zip" to "application/zip",
        ".rar" to "application/x-rar-compressed",
        ".7z" to "application/x-7z-compressed",
        ".tar" to "application/x-tar",
    )

    fun getMime(suffix: String): String {
        val realSuffix = if(suffix.startsWith(".")) suffix else ".${suffix}"
        return MIME_MAP.getOrDefault(realSuffix, "application/octet-stream")
    }

    fun getSuffix(fileName:String, defaultExtName: String = ".bin"):String{
        return fileName.split(".").lastOrNull() ?: defaultExtName
    }

    fun addMime(suffix: String, mime: String) {
        val realSuffix = if(suffix.startsWith(".")) suffix else ".${suffix}"
        MIME_MAP[realSuffix] = mime
    }
}