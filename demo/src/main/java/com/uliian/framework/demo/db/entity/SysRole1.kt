package com.uliian.framework.demo.db.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.time.LocalDateTime

/**
 * 角色信息表
 */
@TableName(value = "sys_role")
class SysRole {
    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    /**
     * 角色ID
     */
    @TableId(value = "role_id", type = IdType.INPUT)
    var roleId: Long? = null
    /**
     * 获取角色名称
     *
     * @return role_name - 角色名称
     */
    /**
     * 设置角色名称
     *
     * @param roleName 角色名称
     */
    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    var roleName: String? = null
    /**
     * 获取角色权限字符串
     *
     * @return role_key - 角色权限字符串
     */
    /**
     * 设置角色权限字符串
     *
     * @param roleKey 角色权限字符串
     */
    /**
     * 角色权限字符串
     */
    @TableField(value = "role_key")
    var roleKey: String? = null
    /**
     * 获取显示顺序
     *
     * @return role_sort - 显示顺序
     */
    /**
     * 设置显示顺序
     *
     * @param roleSort 显示顺序
     */
    /**
     * 显示顺序
     */
    @TableField(value = "role_sort")
    var roleSort: Int? = null
    /**
     * 获取数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     *
     * @return data_scope - 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    /**
     * 设置数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     *
     * @param dataScope 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    @TableField(value = "data_scope")
    var dataScope: String? = null
    /**
     * 获取菜单树选择项是否关联显示
     *
     * @return menu_check_strictly - 菜单树选择项是否关联显示
     */
    /**
     * 设置菜单树选择项是否关联显示
     *
     * @param menuCheckStrictly 菜单树选择项是否关联显示
     */
    /**
     * 菜单树选择项是否关联显示
     */
    @TableField(value = "menu_check_strictly")
    var menuCheckStrictly: Boolean? = null
    /**
     * 获取部门树选择项是否关联显示
     *
     * @return dept_check_strictly - 部门树选择项是否关联显示
     */
    /**
     * 设置部门树选择项是否关联显示
     *
     * @param deptCheckStrictly 部门树选择项是否关联显示
     */
    /**
     * 部门树选择项是否关联显示
     */
    @TableField(value = "dept_check_strictly")
    var deptCheckStrictly: Boolean? = null
    /**
     * 获取角色状态（0正常 1停用）
     *
     * @return status - 角色状态（0正常 1停用）
     */
    /**
     * 设置角色状态（0正常 1停用）
     *
     * @param status 角色状态（0正常 1停用）
     */
    /**
     * 角色状态（0正常 1停用）
     */
    @TableField(value = "`status`")
    var status: String? = null
    /**
     * 获取删除标志（0代表存在 2代表删除）
     *
     * @return del_flag - 删除标志（0代表存在 2代表删除）
     */
    /**
     * 设置删除标志（0代表存在 2代表删除）
     *
     * @param delFlag 删除标志（0代表存在 2代表删除）
     */
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableField(value = "del_flag")
    var delFlag: String? = null
    /**
     * 获取创建者
     *
     * @return create_by - 创建者
     */
    /**
     * 设置创建者
     *
     * @param createBy 创建者
     */
    /**
     * 创建者
     */
    @TableField(value = "create_by")
    var createBy: String? = null
    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    var createTime: LocalDateTime? = null
    /**
     * 获取更新者
     *
     * @return update_by - 更新者
     */
    /**
     * 设置更新者
     *
     * @param updateBy 更新者
     */
    /**
     * 更新者
     */
    @TableField(value = "update_by")
    var updateBy: String? = null
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
    @TableField(value = "update_time")
    var updateTime: LocalDateTime? = null
    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    /**
     * 设置备注
     *
     * @param remark 备注
     */
    /**
     * 备注
     */
    @TableField(value = "remark")
    var remark: String? = null
}