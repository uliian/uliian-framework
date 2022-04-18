package com.uliian.framework.demo.controller

import com.uliian.framework.demo.enums.TestE
import com.uliian.idGenerate.EasyGenerator
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(private val idg:EasyGenerator){
    @GetMapping("")
    fun teste1(): Long {
        return idg.newId()
    }

    @GetMapping("enum")
    fun ttt(e: TestE){
        println(e)
    }
}