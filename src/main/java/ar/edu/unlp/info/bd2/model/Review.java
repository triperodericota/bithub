package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Review extends PersistentObject {


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

    public void addReview(FileReview aFileReview){
        this.getReviews().add(aFileReview);
    }

    public List<FileReview> getReviews(){
        return this.files;
    }
}

