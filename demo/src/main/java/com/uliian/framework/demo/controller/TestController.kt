package com.uliian.framework.demo.controller

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.uliian.framework.demo.db.entity.Room
import com.uliian.framework.demo.db.entity.SysRole
import com.uliian.framework.demo.enums.TestE
import com.uliian.framework.demo.service.RoleService
import com.uliian.framework.mybatisplus.OffsetPageResult
import com.uliian.framework.mybatisplus.OrderType
import com.uliian.framework.mybatisplus.extention.offsetPage
import com.uliian.idGenerate.EasyGenerator
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(private val idg:EasyGenerator,private val svc:RoleService){
//    @SecurityRequirement(name="auth")
    data class Test(val id:Long,val name:String)
    data class RecordData(val id: Long, val name: String)

    @GetMapping("")
    fun teste1(): OffsetPageResult<SysRole, Long> {
        return this.svc.offsetPage(KtQueryWrapper(SysRole::class.java),SysRole::roleId,OrderType.Desc,0,2);
//        return idg.newId()
    }

    @GetMapping("enum")
    fun ttt(e: TestE){
        println(e)
    }
}