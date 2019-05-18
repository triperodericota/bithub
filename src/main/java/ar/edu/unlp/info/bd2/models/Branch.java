package ar.edu.unlp.info.bd2.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Branch {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "IdBranch")
    List<Commit> commits;

    public Branch() { }

    public Branch(String name){
        this.setName(name);
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

    public List<Commit> getCommits() { return this.commits; }
}
