package ar.edu.unlp.info.bd2.models;

import javax.persistence.*;

@Entity
public class Tag {

    @Id
    private Long id;

    private String commitHash;

    private String name;

    @OneToOne
    @JoinColumn(name= "commit_id")
    @MapsId
    private Commit commit;

    public Tag() {
    }

    public Tag(String commitHash, String name, Commit newCommit){
        this.setCommitHash(commitHash);
        this.setName(name);
        this.setCommit(newCommit);
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

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

}
