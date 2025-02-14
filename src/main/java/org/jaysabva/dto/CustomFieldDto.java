package org.jaysabva.dto;

import org.jaysabva.entity.UserDataType;

public class CustomFieldDto {
    private String name;
    private UserDataType type;
    private boolean enabled;
    private String createdBy;

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
