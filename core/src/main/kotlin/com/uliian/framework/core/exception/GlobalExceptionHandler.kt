package com.uliian.framework.core.exception

import org.springframework.http.ResponseEntity
import org.springframework.util.CollectionUtils
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException


@RestControllerAdvice
class GlobalExceptionHandler {
    /**
     * 用来处理bean validation异常
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseBody
    fun resolveConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<ErrorResponse> {
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
            val result = ErrorResponse(ErrorCode.Normal,errorMessage)
            return rsp.body(result)
        }

        return rsp.body(ErrorResponse(ErrorCode.SystemError,ex.message?:""))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun resolveMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
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
            val result = ErrorResponse(ErrorCode.Normal,errorMessage)
            return rsp.body(result)
        }
        return rsp.body(ErrorResponse(ErrorCode.SystemError,ex.message?:""))
    }

    @ExceptionHandler(AppException::class)
    @ResponseBody
    fun resolveAppException(ex: AppException):ResponseEntity<ErrorResponse>{
        return ResponseEntity.status(ex.httpStatus).body(ErrorResponse(ErrorCode.Normal,ex.message?:""))
    }
}