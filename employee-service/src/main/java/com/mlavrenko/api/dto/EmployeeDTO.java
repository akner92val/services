package com.mlavrenko.api.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.UUID;

public class EmployeeDTO extends RepresentationModel {
    private UUID id;
    private String email;
    private String fullName;
    private Date birthDate;
    private DepartmentDTO department;

    public UUID getUuid() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }
}
