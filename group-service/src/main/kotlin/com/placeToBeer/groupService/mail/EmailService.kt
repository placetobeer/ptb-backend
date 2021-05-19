package com.placeToBeer.groupService.mail

interface EmailService {
    fun sendSimpleMessage(to: String, subject: String, text: String)
}