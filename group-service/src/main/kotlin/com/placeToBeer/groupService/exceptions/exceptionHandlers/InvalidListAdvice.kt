package com.placeToBeer.groupService.exceptions.exceptionHandlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.placeToBeer.groupService.exceptions.AbstractInvalidListException
import com.placeToBeer.groupService.exceptions.AbstractInvalidValueException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class InvalidListAdvice {

    @ResponseBody
    @ExceptionHandler(AbstractInvalidListException::class)
    fun invalidValueHandler(abstractInvalidListException: AbstractInvalidListException): ResponseEntity<String> {
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json; charset=utf-8")
        return ResponseEntity(ObjectMapper().writeValueAsString(abstractInvalidListException.message), headers, HttpStatus.UNPROCESSABLE_ENTITY)
    }
}