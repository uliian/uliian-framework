package com.uliian.framework.core.oplog

import com.uliian.framework.core.exception.AppException
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Aspect
@Component
class OpLogger(private val opLogRepository: IOpLogRepository, private val userInfoGetter: IUserInfoGetter) {

    @Pointcut("@annotation(OpLog)")
    fun handle() {
    }

    @After("@annotation(OpLog)")
    fun doLog(joinPoint: JoinPoint) {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
//        val userInfo = joinPoint.args.filterIsInstance<UserInfo>().firstOrNull()?:UserContext.userInfo
        val userInfo = this.userInfoGetter.getUserInfo(joinPoint)
        val opLog = method.annotations.filterIsInstance<OpLog>().first()
        var id: String = ""

        var idParameterIx: Int = -1
        method.parameters.forEachIndexed { i, item ->
            if (item.name == "id" || item.isAnnotationPresent(Id::class.java)) {
                idParameterIx = i
            }
        }
        if(idParameterIx>-1) {
            id = when (joinPoint.args[idParameterIx]){
                is String -> joinPoint.args[idParameterIx] as String
                else -> (joinPoint.args[idParameterIx] as Any).toString()
            }
        }
        val parameter = joinPoint.args.filterIsInstance<IOpDesc>().firstOrNull()
        if (parameter != null) {
            id = parameter.id
        }

        val log = AppOpLog()
        log.business = opLog.businessName
        log.createTime = LocalDateTime.now()
        log.opDesc = "${opLog.desc}-${parameter?.desc?:""}"
        log.userName = userInfo.name
        log.userId = userInfo.id
        log.targetId = id
        log.remark = opLog.remark
        this.opLogRepository.add(log)
    }

    @AfterReturning(pointcut="@annotation(CreateLog)", returning="returnData")
    fun doCreatedLog(joinPoint:JoinPoint,returnData:Any){
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
//        val userInfo = joinPoint.args.filterIsInstance<UserInfo>().firstOrNull()?:UserContext.userInfo
        val userInfo = this.userInfoGetter.getUserInfo(joinPoint)
        val opLog = method.annotations.filterIsInstance<CreateLog>().first()
        var opLogDesc = opLog.desc
        val parameter = joinPoint.args.filterIsInstance<IOpDesc>().firstOrNull()
        if (parameter != null) {
            opLogDesc = "$opLogDesc ${parameter.id}"
        }

        val returnId = when(returnData){
            is Long -> returnData
            is String -> returnData
            is IOpDesc -> returnData.id
            else ->throw AppException(HttpStatus.INTERNAL_SERVER_ERROR,"错误的返回值")
        }

        val log = AppOpLog()
        log.business = opLog.businessName
        log.createTime = LocalDateTime.now()
        log.opDesc = "${opLog.desc}-${parameter?.desc?:""}"
        log.userName = userInfo.name
        log.userId = userInfo.id
        log.targetId = returnId.toString()
        log.remark = opLog.remark
        this.opLogRepository.add(log)
    }
}