package com.placeToBeer.groupService.config

import org.springframework.context.annotation.Bean
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

class EmailConfig {

    @Bean
    public fun getJavaMailServer(): JavaMailSender {
        var mailSender: JavaMailSenderImpl = JavaMailSenderImpl()
        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587

        mailSender.username = "placetobeer.noreply@gmail.com"
        mailSender.password = "password - do not put this on GitHub"

        var props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.debug"] = "true"

        return mailSender
    }

}