package com.uliian.framework.demo.controller

import com.uliian.framework.mybatisplus.extention.PageCondition
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("databind")
class DataBindController {

    @GetMapping
    fun noAnnotation(@ModelAttribute data:Data ,@ModelAttribute page:PageCondition): String {
        return "data:${data},page:${page}"
    }
}

class Data{
    var a:String? = null
    var b:String? = null
    override fun toString(): String {
        return "Data(a=$a, b=$b)"
    }

}