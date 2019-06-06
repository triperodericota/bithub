package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Commit extends PersistentObject {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commit_id")
    private Long id;
*/
    @Column
    private String message;

    @Column
    private String hash;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User author;

    @OneToMany(/*fetch = FetchType.LAZY,*/mappedBy = "commit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<File>();

    @ManyToOne
    @JoinColumn(name="branch_id")
    private Branch branch;

    @OneToOne(mappedBy = "commit",cascade = CascadeType.ALL)
    private Tag tag;

    public Commit() { }

    public Commit (String description, String hash, User author, List<File> files, Branch aBranch){
        this.setMessage(description);
        this.setHash(hash);
        this.setAuthor(author);
        this.setFiles(files);
        this.setBranch(aBranch);

    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    public List<File> getFiles() {
        return this.files;
    }

    public void setFiles(List<File> filesList) {
        filesList.forEach(file -> this.addFile(file));
    }

    public void addFile(File file){
        this.files.add(file);
    }

    public void removeFile(File file){
        this.files.remove(file);
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String description) {
        this.message = description;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return " --- files for commit : " + this.files.toString();
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

}

