package ar.edu.unlp.info.bd2.models;

import javax.persistence.*;
import java.util.*;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<File>();

    @ManyToOne
    @JoinColumn(name="IdBranch")
    private Branch branch;


    public Commit() { }

    public Commit (String description, String hash, User author, List<File> files, Branch aBranch){
        this.setMessage(description);
        this.setHash(hash);
        this.setAuthor(author);
        this.setFiles(files);
        this.setBranch(aBranch);
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
        return this.files;
    }

    public void setFiles(List<File> filesList) {
        Iterator<File> iter = filesList.iterator();
        while(iter.hasNext()){
            this.files.add(iter.next());
        }
    }

    public void addFile(File file){
        this.files.add(file);
    }

    public void removeFile(File file){
        this.files.remove(file);
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
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
