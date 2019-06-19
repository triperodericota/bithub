package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Branch extends PersistentObject {

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "branch_id")
    private List<Commit> commits = new ArrayList<>();

    public Branch() { }

    public Branch(String name){
        this.setName(name);
    }

    public Branch(String name, List<Commit> commits){
        this.setName(name);
        this.setCommits(commits);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Commit> getCommits() { return this.commits; }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    public void addCommit(Commit aCommit){
        this.getCommits().add(aCommit);
    }
}
