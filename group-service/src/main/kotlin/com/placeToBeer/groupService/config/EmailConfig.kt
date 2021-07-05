package com.placeToBeer.groupService.config

import org.springframework.context.annotation.Bean
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

class EmailConfig {

    @Bean
    fun getJavaMailServer(): JavaMailSender {
        val mailSender: JavaMailSenderImpl = JavaMailSenderImpl()
        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587

        mailSender.username = "placetobeer.noreply@gmail.com"
        mailSender.password = "zcddewuaphlhntne"

        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.debug"] = "true"

        return mailSender
    }

    @Bean
    fun templateSimpleMessage(): SimpleMailMessage{
        val message = SimpleMailMessage()
        message.text ="This mail was auto-generated by the place-to-beer team"
        return message
    }

}