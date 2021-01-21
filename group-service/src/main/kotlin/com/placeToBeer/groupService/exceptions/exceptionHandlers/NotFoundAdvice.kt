package com.placeToBeer.groupService.exceptions.exceptionHandlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.placeToBeer.groupService.exceptions.AbstractNotFoundException
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestControllerAdvice
class NotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(AbstractNotFoundException::class)
    fun groupNotFoundHandler(abstractNotFoundException: AbstractNotFoundException): ResponseEntity<String> {
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json; charset=utf-8")
        return ResponseEntity(ObjectMapper().writeValueAsString(abstractNotFoundException.message), headers, HttpStatus.NOT_FOUND)
    }
}