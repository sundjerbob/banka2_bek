package rs.raf.banka2_bek.notification.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import rs.raf.banka2_bek.event.PasswordResetRequestedEvent;
import rs.raf.banka2_bek.notification.service.EmailNotificationService;

@Component
public class PasswordResetRequestedListener {

    private static final Logger log = LoggerFactory.getLogger(PasswordResetRequestedListener.class);

    private final EmailNotificationService emailService;

    public PasswordResetRequestedListener(EmailNotificationService emailService) {
        this.emailService = emailService;
    }

    @Async("notificationTaskExecutor")
    @TransactionalEventListener(fallbackExecution = true)
    public void onPasswordResetRequested(PasswordResetRequestedEvent event) {
        log.debug("Processing PasswordResetRequested for {}", event.email());
        emailService.sendPasswordResetEmail(event.email(), event.resetToken());
    }
}
