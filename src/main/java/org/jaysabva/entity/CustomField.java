package org.jaysabva.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "customFields")
public class CustomField {
    @Id
    private Long id;
    private String name;
    private UserDataType type;
    private boolean enabled;
    private String createdBy;
    private LocalDateTime createdAt;

    public CustomField() {
    }

    public CustomField(String name, UserDataType type, boolean enabled, String createdBy) {
        this.name = name;
        this.type = type;
        this.enabled = enabled;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserDataType getType() {
        return type;
    }

    public void setType(UserDataType type) {
        this.type = type;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
