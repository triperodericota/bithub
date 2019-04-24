package ar.edu.unlp.info.bd2.models;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

@Entity
public class Commit {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String message;

    @Column
    private String hash;

    @ManyToOne
    @JoinColumn(name="IdUser")
    private User author;

    @OneToMany
    private List<File> files;

    @ManyToOne
    @JoinColumn (name="IdBranch")
    private Branch master;

    public Commit() { }


    public Commit (String description, String hash, User author, List<File> files, Branch branch){
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
