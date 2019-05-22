package ar.edu.unlp.info.bd2.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Review {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_user")
    private User author;

    @ManyToOne
    @JoinColumn(name="id_branch")
    private Branch branch;

    @OneToMany
    @JoinColumn(name="review_id")
    private List<FileReview> files = new ArrayList<FileReview>();

    public Review() {
    }

    public Review (Branch master, User user){
        this.setAuthor(user);
        this.setBranch(master);
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void addFileReview(FileReview aFileReview){
        this.getFiles().add(aFileReview);
    }

    public List<FileReview> getFiles(){
        return this.files;
    }
}
