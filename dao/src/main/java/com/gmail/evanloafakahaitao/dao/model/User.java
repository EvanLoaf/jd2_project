package com.gmail.evanloafakahaitao.dao.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    @Column
    private String password;
    @NotNull
    @Column
    private String firstName;
    @NotNull
    @Column
    private String lastName;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "F_ROLE_ID")
    private Role role;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;

    public User() {
    }

    private User(Builder builder) {
        id = builder.id;
        setEmail(builder.email);
        setPassword(builder.password);
        setFirstName(builder.firstName);
        setLastName(builder.lastName);
        setRole(builder.role);
        setProfile(builder.profile);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public static final class Builder {
        private Long id;
        private @NotNull String email;
        private @NotNull String password;
        private @NotNull String firstName;
        private @NotNull String lastName;
        private Role role;
        private Profile profile;

        private Builder() {
        }

        public Builder withId(Long val) {
            id = val;
            return this;
        }

        public Builder withEmail(@NotNull String val) {
            email = val;
            return this;
        }

        public Builder withPassword(@NotNull String val) {
            password = val;
            return this;
        }

        public Builder withFirstName(@NotNull String val) {
            firstName = val;
            return this;
        }

        public Builder withLastName(@NotNull String val) {
            lastName = val;
            return this;
        }

        public Builder withRole(Role val) {
            role = val;
            return this;
        }

        public Builder withProfile(Profile val) {
            profile = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}