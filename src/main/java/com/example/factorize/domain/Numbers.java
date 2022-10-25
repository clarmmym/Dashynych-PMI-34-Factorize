package com.example.factorize.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Numbers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String bigInteger;
    private String factNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_list", joinColumns = @JoinColumn(name = "number_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<User> listUser = new HashSet<>();

    public Numbers() {
    }

    public List<String> getAuthorName() {
        List<String> authors = new ArrayList<>();
        if (listUser.size() != 0) {
            listUser.forEach(u -> authors.add(u.getUsername()));
        } else {
            authors.add("<none>");
        }

        return authors;
    }

    public Numbers(String bigInteger, String factNumber, User user) {
        this.bigInteger = bigInteger;
        this.factNumber = factNumber;
        listUser.add(user);
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

    public Set<User> getListUser() {
        return listUser;
    }

    public void setListUser(User user) {
        listUser.add(user);
    }
}
