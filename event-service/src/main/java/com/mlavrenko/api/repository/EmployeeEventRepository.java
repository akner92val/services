package com.mlavrenko.api.repository;

import com.mlavrenko.api.domain.EmployeeEvent;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeEventRepository extends JpaRepository<EmployeeEvent, UUID> {
    List<EmployeeEvent> findAllByEmployeeIdOrderByEventCreatedAsc(UUID uuid);
}
