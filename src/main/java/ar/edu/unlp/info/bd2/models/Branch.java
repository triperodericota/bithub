package ar.edu.unlp.info.bd2.models;

public class Branch {

    public Long id;
    public String name;

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
