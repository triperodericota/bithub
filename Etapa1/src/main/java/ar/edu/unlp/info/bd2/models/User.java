package ar.edu.unlp.info.bd2.models;

import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Commit> commits = new ArrayList<>();

    public User() { }


    public User(String email, String name) {
        this.setEmail(email);
        this.setName(name);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void addCommit(Commit aCommit){
        this.getCommits().add(aCommit);
    }
}

