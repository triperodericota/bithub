package ar.edu.unlp.info.bd2.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Entity
public class Branch {

    //@Id
    //@GeneratedValue
    private Long id;
    private String name;

    public Branch() {
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
