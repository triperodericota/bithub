package ar.edu.unlp.info.bd2.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Commit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commitId")
    private Long id;

    @Column
    private String message;

    @Column
    private String hash;

    @ManyToOne
    @JoinColumn(name="IdUser")
    private User author;

    @OneToMany(/*fetch = FetchType.LAZY,*/mappedBy = "commit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files;

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
        return files;
    }


    public void setFiles(List<File> array) {
        this.files = array;
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
