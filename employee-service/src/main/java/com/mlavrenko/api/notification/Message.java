package com.mlavrenko.api.notification;

import java.util.UUID;

public class Message {
    private UUID uuid;
    private Status status;

    public Message(UUID uuid, Status status) {
        this.uuid = uuid;
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Status getStatus() {
        return status;
    }
}
