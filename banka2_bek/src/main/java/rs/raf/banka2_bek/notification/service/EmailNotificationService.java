package rs.raf.banka2_bek.notification.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import rs.raf.banka2_bek.notification.template.EmailTemplateBuilder;

@Service
public class EmailNotificationService {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationService.class);

    private final JavaMailSender mailSender;

    @Value("${notification.activation-url-base:http://localhost:8080}")
    private String activationUrlBase;

    @Value("${notification.password-reset-url-base:http://localhost:8080}")
    private String passwordResetUrlBase;

    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendActivationEmail(String to, String employeeName, String activationToken) {
        var template = EmailTemplateBuilder.activationEmail(employeeName, activationToken, activationUrlBase);
        sendHtml(to, template.subject(), template.htmlBody());
    }

    public void sendPasswordResetEmail(String to, String resetToken) {
        var template = EmailTemplateBuilder.passwordResetEmail(resetToken, passwordResetUrlBase);
        sendHtml(to, template.subject(), template.htmlBody());
    }

    private void sendHtml(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
            log.info("Email sent to {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
