package com.uliian.framework.demo.service

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.uliian.framework.demo.db.entity.Room
import com.uliian.framework.demo.db.entity.SysRole
import com.uliian.framework.demo.db.mapper.RoomMapper
import com.uliian.framework.demo.db.mapper.SysRoleMapper
import org.springframework.stereotype.Service

@Service
class RoleService : ServiceImpl<SysRoleMapper,SysRole>() {
}