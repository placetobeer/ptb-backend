package com.placeToBeer.groupService.services.mail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service("EmailService")
class EmailServiceImpl: EmailService {

    @Autowired
    lateinit var emailSender: JavaMailSender

    override fun sendSimpleMessage(to: String, subject: String, text: String) {
        var message: SimpleMailMessage = SimpleMailMessage()
        message.from = "placetobeer.noreply@gmail.com"
        message.setTo(to)
        message.subject = subject
        message.text = text
        emailSender.send(message)
    }
}