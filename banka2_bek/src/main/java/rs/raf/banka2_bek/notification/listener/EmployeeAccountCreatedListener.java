package rs.raf.banka2_bek.notification.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import rs.raf.banka2_bek.event.EmployeeAccountCreatedEvent;
import rs.raf.banka2_bek.notification.service.EmailNotificationService;

@Component
public class EmployeeAccountCreatedListener {

    private static final Logger log = LoggerFactory.getLogger(EmployeeAccountCreatedListener.class);

    private final EmailNotificationService emailService;

    public EmployeeAccountCreatedListener(EmailNotificationService emailService) {
        this.emailService = emailService;
    }

    @Async("notificationTaskExecutor")
    @TransactionalEventListener(fallbackExecution = true)
    public void onEmployeeAccountCreated(EmployeeAccountCreatedEvent event) {
        log.debug("Processing EmployeeAccountCreated for {}", event.email());
        emailService.sendActivationEmail(
                event.email(),
                event.employeeName(),
                event.activationToken()
        );
    }
}
