package com.app.workshop_registration_system.Services;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.app.workshop_registration_system.Advice.EmailSendException;
import com.app.workshop_registration_system.Config.MailSenderProperties;
import com.app.workshop_registration_system.Utils.EmailUtils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final MailSenderProperties mailSenderProperties;

    private final JavaMailSender javaMailSender;

    private final EmailUtils emailUtils;

    public EmailServiceImpl(JavaMailSender javaMailSender, MailSenderProperties mailSenderProperties,
            EmailUtils emailUtils) {
        this.javaMailSender = javaMailSender;
        this.mailSenderProperties = mailSenderProperties;
        this.emailUtils = emailUtils;
    }

    @Override
    public void sendEmail(String toUser, String subject, Map<String, String> variables, String namefile) {

        String html = emailUtils.loadHtmlTemplate("templates/email/".concat(namefile));

        // Reemplazar variables en el HTML
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            html = html.replace("[[" + entry.getKey() + "]]", entry.getValue());
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(mailSenderProperties.getEmailFrom());
            helper.setTo(toUser);
            helper.setSubject(subject);
            helper.setText(html, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailSendException("Error al enviar el correo", e);
        }
    }

    @Override
    public void sendEmailWithFile(String toUser, String subject, String message, File file) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true,
                    StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(mailSenderProperties.getEmailFrom());
            mimeMessageHelper.setTo(toUser);
            mimeMessageHelper.setText(message);
            mimeMessage.setSubject(subject);

            mimeMessageHelper.addAttachment(file.getName(), file);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo de confirmación");

        }
    }

}
