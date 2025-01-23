package com.uliian.framework.oss.controller

import com.uliian.framework.oss.IOssGateway
import com.uliian.framework.oss.dto.UploadFileData
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${framework.oss.path:/oss}")
class OssController(private val ossGateway: IOssGateway) {

    @GetMapping("/sign")
    fun getSign(@RequestParam savePrefix: String, @RequestParam fileName: String): UploadFileData {
        return this.ossGateway.getSign(savePrefix, fileName)
    }
}