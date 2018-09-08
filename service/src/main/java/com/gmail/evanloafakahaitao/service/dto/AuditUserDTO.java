package com.gmail.evanloafakahaitao.service.dto;

import com.gmail.evanloafakahaitao.dao.model.Role;

public class AuditUserDTO {

    private String name;
    private String email;
    private Role role;

    public AuditUserDTO() {
    }

    private AuditUserDTO(Builder builder) {
        setName(builder.name);
        setEmail(builder.email);
        setRole(builder.role);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static final class Builder {
        private String name;
        private String email;
        private Role role;

        private Builder() {
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withEmail(String val) {
            email = val;
            return this;
        }

        public Builder withRole(Role val) {
            role = val;
            return this;
        }

        public AuditUserDTO build() {
            return new AuditUserDTO(this);
        }
    }
}