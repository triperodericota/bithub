package ar.edu.unlp.info.bd2.models;

import java.util.Arrays;
import java.util.List;

public class Commit {

    public Long id;
    public String description;
    public String hash;
    public User user;
    public List<File> files;
    public Branch master;

    public Commit() {
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return user;
    }

    public void setAuthor(User user) {
        this.user = user;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> array) {
        this.files = array;
    }

    public Branch getMaster() {
        return master;
    }

    public void setMaster(Branch master) {
        this.master = master;
    }


    public String getMessage() {
        return description;
    }

    public void setMessage(String description) {
        this.description = description;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }


}
