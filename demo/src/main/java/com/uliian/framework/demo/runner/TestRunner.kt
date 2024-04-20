package com.uliian.framework.demo.runner

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.uliian.framework.demo.db.entity.SysRole
import com.uliian.framework.demo.db.mapper.SysRoleMapper
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class TestRunner(private val mapper:SysRoleMapper) : CommandLineRunner {
    companion object {
        val LOG = LoggerFactory.getLogger(TestRunner::class.java)
    }
    override fun run(vararg args: String?) {
        var result = mapper.selectBatchIds(emptyList())
        LOG.info("safeSelectBatchIds result:{}" , result.isEmpty())
        result = mapper.selectBatchIds(listOf(1,2,3))
        LOG.info("safeSelectBatchIds result size:{}" , result.size)

        result = mapper.selectList(KtQueryWrapper(SysRole::class.java).`in`(SysRole::roleId, emptyList<Long>()).eq(SysRole::status, "0"))
        LOG.info("selectList result size:{}" , result.size)
        result = mapper.selectBatchIds(emptyList())
    }
}