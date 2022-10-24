package com.example.factorize.domain;

import javax.persistence.*;

@Entity
public class Numbers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String bigInteger;
    private String factNumber;

    public Numbers() {
    }

    public Numbers(String bigInteger, String factNumber) {
        this.bigInteger = bigInteger;
        this.factNumber = factNumber;
    }

    public String getBigInteger() {
        return bigInteger;
    }

    public void setBigInteger(String bigInteger) {
        this.bigInteger = bigInteger;
    }

    public String getFactNumber() {
        return factNumber;
    }

    public void setFactNumber(String factNumber) {
        this.factNumber = factNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
