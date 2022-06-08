package com.uliian.framework.demo.controller

import com.uliian.framework.demo.enums.TestE
import com.uliian.idGenerate.EasyGenerator
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(private val idg:EasyGenerator){
//    @SecurityRequirement(name="auth")
    @GetMapping("")
    fun teste1(): Long {
        return idg.newId()
    }

    @GetMapping("enum")
    fun ttt(e: TestE){
        println(e)
    }
}