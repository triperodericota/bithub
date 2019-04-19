package ar.edu.unlp.info.bd2.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FileReview {

    @Id
    @GeneratedValue
    private Long id;
    private Review review;
    private File file;
    private Integer line;
    private String comment;

    public FileReview() {
    }

    public Long getId() {
        return id;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public File getReviewedFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Integer getLineNumber() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
