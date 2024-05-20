package com.uliian.framework.web.exceptionhandle

import com.uliian.framework.components.exception.AppException
import jakarta.servlet.ServletRequest
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.util.CollectionUtils
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {
    companion object {
        val LOG = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }
    /**
     * 用来处理bean validation异常
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseBody
    fun resolveConstraintViolationException(ex: ConstraintViolationException,request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        logRequest(request)
        val rsp = ResponseEntity.badRequest()

        val constraintViolations = ex.constraintViolations
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            val msgBuilder = StringBuilder()
            for (constraintViolation in constraintViolations) {
                msgBuilder.append(constraintViolation.message).append(",")
            }
            var errorMessage = msgBuilder.toString()
            if (errorMessage.length > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length - 1)
            }
            val result = ErrorResponse(ErrorCode.Normal.code,errorMessage)
            return rsp.body(result)
        }

        return rsp.body(ErrorResponse(ErrorCode.SystemError.code,ex.message?:""))
    }

    private fun logRequest(request: HttpServletRequest) {
        if (LOG.isDebugEnabled) {
            LOG.warn("参数验证异常：${this.resolveUrl(request)}\nBODY: ${this.resolveBody(request)}")
        } else {
            LOG.warn("参数验证异常：${this.resolveUrl(request)}")
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun resolveMethodArgumentNotValidException(ex: MethodArgumentNotValidException,request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        logRequest(request)

        val rsp = ResponseEntity.badRequest()

        val objectErrors = ex.bindingResult.allErrors
        if (!CollectionUtils.isEmpty(objectErrors)) {
            val msgBuilder = StringBuilder()
            for (objectError in objectErrors) {
                msgBuilder.append(objectError.defaultMessage).append(",")
            }
            var errorMessage = msgBuilder.toString()
            if (errorMessage.length > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length - 1)
            }
            val result = ErrorResponse(ErrorCode.Normal.code,errorMessage)
            return rsp.body(result)
        }
        return rsp.body(ErrorResponse(ErrorCode.SystemError.code,ex.message?:""))
    }

    @ExceptionHandler(AppException::class)
    @ResponseBody
    fun resolveAppException(ex: AppException,request:HttpServletRequest):ResponseEntity<ErrorResponse>{
        if (LOG.isDebugEnabled) {
            LOG.warn("未处理的异常：${this.resolveUrl(request)}\nBODY: ${this.resolveBody(request)}")
        } else {
            LOG.warn("未处理的异常：${this.resolveUrl(request)}")
        }
        return ResponseEntity.status(ex.httpStatus).body(ErrorResponse(ErrorCode.Normal.code,ex.message?:""))
    }

    private fun resolveUrl(request:HttpServletRequest):String{
        return "Method: ${request.method},URL: ${request.requestURL}"
    }

    private fun resolveBody(request:HttpServletRequest):String?{
        return request.reader.readText()
    }
}