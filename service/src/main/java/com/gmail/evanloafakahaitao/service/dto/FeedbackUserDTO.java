package com.gmail.evanloafakahaitao.service.dto;

public class FeedbackUserDTO {

    private String name;
    private String email;

    public FeedbackUserDTO() {
    }

    private FeedbackUserDTO(Builder builder) {
        setName(builder.name);
        setEmail(builder.email);
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

    public static final class Builder {
        private String name;
        private String email;

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

        public FeedbackUserDTO build() {
            return new FeedbackUserDTO(this);
        }
    }
}
