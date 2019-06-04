package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import ar.edu.unlp.info.bd2.mongo.Association;

@Entity
public class File extends PersistentObject{

    @Column
    private String name;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="commit_id")
    private Commit commit;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private List<FileReview> reviews = new ArrayList<FileReview>();

    @Transient
    private Association commit_file;

    public File() { }

    public File(String content, String name) {
        this.setFilename(name);
        this.setContent(content);
    }

    public String getFilename() {
        return name;
    }

    public void setFilename(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCommit(Commit commit) { this.commit = commit; }

    public Commit getCommit() { return this.commit; }

    public List<FileReview> getReviews(){
        return this.reviews;
    }

    public void addReview(FileReview aNewFileReview){
        this.getReviews().add(aNewFileReview);
    }

    public Association getCommit_file() {
        return commit_file;
    }

    public void setCommit_file(Association commit_file) {
        this.commit_file = commit_file;
    }
}
