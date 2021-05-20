package com.placeToBeer.groupService.services.mail

interface EmailService {
    fun sendSimpleMessage(to: String, subject: String, text: String)
}