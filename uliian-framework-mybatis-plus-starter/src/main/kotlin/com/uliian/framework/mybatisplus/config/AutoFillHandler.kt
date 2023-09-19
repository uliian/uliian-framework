package com.uliian.framework.mybatisplus.config

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AutoFillHandler : MetaObjectHandler {
    override fun insertFill(metaObject: MetaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::class.java, LocalDateTime.now())
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime::class.java, LocalDateTime.now())
    }

    override fun updateFill(metaObject: MetaObject) {
        setFieldValByName("updateTime", LocalDateTime.now(), metaObject)
        //        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
    }
}