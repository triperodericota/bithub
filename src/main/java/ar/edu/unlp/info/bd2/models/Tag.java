package ar.edu.unlp.info.bd2.models;

import javax.persistence.*;

//@Entity
public class Tag {

    //@Id
    //@GeneratedValue
    private Long id;

    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "commit_id")
    private String commitHash;

    private String name;

    public Tag() {
    }

    public Tag(String commitHash, String name){
        this.setCommitHash(commitHash);
        this.setName(name);
    }

    public Long getId(){
        return id;
    }

    public String getCommitHash() {
        return commitHash;
    }

    public void setCommitHash(String commitHash) {
        this.commitHash = commitHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
