package com.app.workshop_registration_system.Services;

import java.io.File;
import java.util.Map;

public interface EmailService {

    void sendEmail(String toUser, String subject, Map<String, String>variables);

    void sendEmailWithFile(String toUser, String subject, String message, File file);

}
