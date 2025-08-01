package com.ecommerce.ecommerce.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class Audit
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String action;
    @Column(length = 100, nullable = false)
    private String entity;
    @Column(name = "entity_id")
    private Long entitdyId;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "user_id"
    )
    private User user;

    public Audit() {
    }

    public Audit(Long id, String action, String entity, Long entitdyId, LocalDateTime dateTime, User user) {
        this.id = id;
        this.action = action;
        this.entity = entity;
        this.entitdyId = entitdyId;
        this.dateTime = dateTime;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getEntitdyId() {
        return entitdyId;
    }

    public void setEntitdyId(Long entitdyId) {
        this.entitdyId = entitdyId;
    }
}
