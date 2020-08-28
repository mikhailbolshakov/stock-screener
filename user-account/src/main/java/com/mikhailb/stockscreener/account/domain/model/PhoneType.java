package com.mikhailb.stockscreener.account.domain.model;

import lombok.Getter;

public enum PhoneType {

    Mobile("Mobile phone"),
    Home("Home phone");

    private String value;

    PhoneType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static PhoneType enumValueOf(String value) {
        for(PhoneType v : values())
            if(v.getValue().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
