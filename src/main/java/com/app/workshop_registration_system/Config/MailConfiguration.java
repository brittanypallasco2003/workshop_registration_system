package com.app.workshop_registration_system.Config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {

   
    private final  MailSenderProperties mailSenderProperties;

    public MailConfiguration(MailSenderProperties mailSenderProperties) {
        this.mailSenderProperties = mailSenderProperties;
    }


    @Bean
    public JavaMailSender getJavaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // especifica el host del proveedor
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(mailSenderProperties.getEmailFrom());
        mailSender.setPassword(mailSenderProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        // Protocolo para enviar el correo
        props.put("mail.transport.protocol", "smtp");
        // el protocolo se va autenticar con el email y el password para enviar correos
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;

    }

}
