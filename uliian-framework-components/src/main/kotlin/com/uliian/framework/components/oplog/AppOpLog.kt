package com.uliian.framework.components.oplog

import java.io.Serializable
import java.time.LocalDateTime

/**
 * <p>
 * 操作日志表
 * </p>
 *
 * @author xmtx
 * @since 2020-02-14
 */
class AppOpLog : Serializable {


    var id: Long? = null

    /**
     * 业务名
     */
    lateinit var business: String

    /**
     * 目标ID
     */
    var targetId: String? = null

    /**
     * 操作人ID
     */
    var userId: Long? = null

    /**
     * 操作人姓名
     */
    var userName: String? = null

    /**
     * 操作描述
     */
    var opDesc: String? = null

    /**
     * 操作时间
     */
    var createTime: LocalDateTime? = null

    /**
     * 备注
     */
    var remark: String? = null


    override fun toString(): String {
        return "AppOpLog{" +
        "id=" + id +
        ", business=" + business +
        ", targetId=" + targetId +
        ", userId=" + userId +
        ", userName=" + userName +
        ", opDesc=" + opDesc +
        ", createTime=" + createTime +
        ", remark=" + remark +
        "}"
    }
}
