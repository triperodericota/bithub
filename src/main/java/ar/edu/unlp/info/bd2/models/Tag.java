package ar.edu.unlp.info.bd2.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Entity
public class Tag {

  //  @Id
    //@GeneratedValue
    private Long id;
    private String commitHash;
    private String name;

    public Tag() {
    }

    public void createTagForCommit(String commitHash, String name){
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
