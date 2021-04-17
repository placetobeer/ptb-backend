package com.placeToBeer.groupService.exceptions.exceptionHandlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.placeToBeer.groupService.exceptions.AbstractInvalidValueException
import com.placeToBeer.groupService.exceptions.AbstractNotFoundException
import com.placeToBeer.groupService.exceptions.GroupNameIsInvalidException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class InvalidValueAdvice {

    @ResponseBody
    @ExceptionHandler(AbstractInvalidValueException::class)
    fun invalidValueHandler(abstractInvalidValueException: AbstractInvalidValueException): ResponseEntity<String> {
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json; charset=utf-8")
        return ResponseEntity(ObjectMapper().writeValueAsString(abstractInvalidValueException.message), headers, HttpStatus.UNPROCESSABLE_ENTITY)
    }

}