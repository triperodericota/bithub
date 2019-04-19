package ar.edu.unlp.info.bd2.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Commit {

    @Id
    @GeneratedValue
    private Long id;
    private String message;
    private String hash;
    private User author;
    private List<File> files;
    private Branch master;

    public Commit() {
    }

    public void createCommit (String description, String hash, User author, List<File> files, Branch branch){
        this.setMessage(description);
        this.setHash(hash);
        this.setAuthor(author);
        this.setFiles(files);
        this.setMaster(branch);
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User user) {
        this.author = user;
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
        return message;
    }

    public void setMessage(String description) {
        this.message = description;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }


}
