package com.mlavrenko.api.notification;

import java.util.UUID;

public class Message {
    private UUID uuid;
    private EmployeeStatus status;

    public Message(UUID uuid, EmployeeStatus status) {
        this.uuid = uuid;
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public EmployeeStatus getStatus() {
        return status;
    }
}
