package com.mlavrenko.api.domain;

import com.mlavrenko.api.domain.enums.EmployeeState;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class EmployeeEvent {
    @Id
    @Column
    private UUID employeeId;
    @Column
    @Enumerated
    private EmployeeState state;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventCreated;

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeState getState() {
        return state;
    }

    public void setState(EmployeeState state) {
        this.state = state;
    }

    public Date getEventCreated() {
        return eventCreated;
    }

    public void setEventCreated(Date eventCreated) {
        this.eventCreated = eventCreated;
    }
}
