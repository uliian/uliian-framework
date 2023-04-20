package com.uliian.framework.demo.db.entity

import com.baomidou.mybatisplus.annotation.*
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler
import java.time.LocalDateTime

/**
 *
 * @author Wwt
 * @date 2022-07-01 15:21
 */
/**
 * 房源信息
 */
@TableName(value = "room",autoResultMap = true)
class Room {
    /**
     * @return id
     */
    /**
     * @param id
     */
    @TableId(value = "id")
    var id: Long = 0
    /**
     * 获取户主姓名
     *
     * @return owner_name - 户主姓名
     */
    /**
     * 设置户主姓名
     *
     * @param ownerName 户主姓名
     */
    /**
     * 户主姓名
     */
    @TableField(value = "owner_name")
    var ownerName: String? = null
    /**
     * 获取户主电话
     *
     * @return owner_phone - 户主电话
     */
    /**
     * 设置户主电话
     *
     * @param ownerPhone 户主电话
     */
    /**
     * 户主电话
     */
    @TableField(value = "owner_phone")
    var ownerPhone: String? = null
    /**
     * 获取户主证件号
     *
     * @return owner_idcard - 户主证件号
     */
    /**
     * 设置户主证件号
     *
     * @param ownerIdcard 户主证件号
     */
    /**
     * 户主证件号
     */
    @TableField(value = "owner_idcard")
    var ownerIdcard: String? = null
    /**
     * 获取房源地址
     *
     * @return address - 房源地址
     */
    /**
     * 设置房源地址
     *
     * @param address 房源地址
     */
    /**
     * 房源地址
     */
    @TableField(value = "address")
    var address: String? = null
    /**
     * 获取房源名称
     *
     * @return name - 房源名称
     */
    /**
     * 设置房源名称
     *
     * @param name 房源名称
     */
    /**
     * 房源名称
     */
    @TableField(value = "`name`")
    var name: String? = null
    /**
     * 获取证件
     *
     * @return idcard_images - 证件
     */
    /**
     * 设置证件
     *
     * @param idcardImages 证件
     */
    /**
     * 证件
     */
    @TableField(value = "idcards",typeHandler = JacksonTypeHandler::class)
    var idcards: List<String>? = null
    /**
     * 获取户主ID
     *
     * @return user_id - 户主ID
     */
    /**
     * 设置户主ID
     *
     * @param userId 户主ID
     */
    /**
     * 户主ID
     */
    @TableField(value = "user_id")
    var userId: Long? = null

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    var createTime: LocalDateTime? = null
    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    var updateTime: LocalDateTime? = null


}