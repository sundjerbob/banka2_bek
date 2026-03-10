package rs.raf.banka2_bek.event;

import java.io.Serializable;

/**
 * Emitted when an employee account is created. Notification module listens to send activation email.
 * Published by: Employee module.
 */
public record EmployeeAccountCreatedEvent(
        String email,
        String employeeName,
        String activationToken
) implements Serializable {}
