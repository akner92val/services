package com.mlavrenko.api.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class EmployeeEvent {
    @Id
    @Column
    private Long employeeId;
    @Column
    private String state;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date eventCreated;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getEventCreated() {
        return eventCreated;
    }

    public void setEventCreated(Date eventCreated) {
        this.eventCreated = eventCreated;
    }
}
