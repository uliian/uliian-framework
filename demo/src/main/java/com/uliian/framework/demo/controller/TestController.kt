package com.uliian.framework.demo.controller

import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
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
//        val javaWrapper = Wrappers.lambdaQuery<SysRole>().orderByDesc(
//            SysRole::getRoleId)
        val ktWrapper = KtQueryWrapper(SysRole::class.java)
//        var t = this.svc.offsetPage(javaWrapper,
//            SysRole::roleId,OrderType.Desc,0,2);
        val t1 = this.svc.offsetPage(ktWrapper,SysRole::roleId,OrderType.Desc,null,2);
       return  t1
    }

    @GetMapping("enum")
    fun ttt(e: TestE){
        println(e)
    }
}