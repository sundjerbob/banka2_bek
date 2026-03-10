package rs.raf.banka2_bek.event;

import java.io.Serializable;
import java.time.Instant;

/**
 * Emitted when a user requests a password reset. Notification module listens to send reset email.
 * Published by: Auth module.
 */
public record PasswordResetRequestedEvent(
        String email,
        String resetToken,
        Instant expiresAt
) implements Serializable {}
