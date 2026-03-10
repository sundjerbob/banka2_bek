package rs.raf.banka2_bek.notification.template;

/**
 * Builds email templates for activation and password reset.
 */
public final class EmailTemplateBuilder {

    private static final String ACTIVATION_SUBJECT = "Aktivacija naloga";
    private static final String PASSWORD_RESET_SUBJECT = "Resetovanje lozinke";

    private EmailTemplateBuilder() {}

    /**
     * Activation email template (employee account created).
     */
    public static EmailTemplate activationEmail(String employeeName, String activationToken, String activationUrlBase) {
        String activationLink = activationUrlBase + "/auth/activate?token=" + activationToken;
        String htmlBody = """
                <html>
                <body>
                <p>Poštovani %s,</p>
                <p>Vaš nalog je kreiran. Kliknite na link ispod da aktivirate nalog i postavite lozinku:</p>
                <p><a href="%s">Aktiviraj nalog</a></p>
                <p>Link ističe za 24 sata.</p>
                </body>
                </html>
                """.formatted(employeeName != null ? employeeName : "Zaposleni", activationLink);
        return new EmailTemplate(ACTIVATION_SUBJECT, htmlBody);
    }

    /**
     * Password reset email template.
     */
    public static EmailTemplate passwordResetEmail(String resetToken, String resetUrlBase) {
        String resetLink = resetUrlBase + "/auth/password_reset/confirm?token=" + resetToken;
        String htmlBody = """
                <html>
                <body>
                <p>Zatražili ste resetovanje lozinke.</p>
                <p>Kliknite na link ispod da postavite novu lozinku:</p>
                <p><a href="%s">Resetuj lozinku</a></p>
                <p>Link ističe za 1 sat.</p>
                </body>
                </html>
                """.formatted(resetLink);
        return new EmailTemplate(PASSWORD_RESET_SUBJECT, htmlBody);
    }

    public record EmailTemplate(String subject, String htmlBody) {}
}
