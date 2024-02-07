package com.example.fixify.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "operation")
    private String operation;

    @Column(name = "details")
    private String details; // JSON format or text describing the change

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public AuditLog() {
    }

    // Additional constructor, getters, and setters if needed
}
