package ar.edu.unlp.info.bd2.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Entity
public class Review {

  //  @Id
    //@GeneratedValue
    private Long id;
    private User author;
    private Branch branch;

    public Review() {
    }

    public void createReview(Branch master, User user){
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
}
