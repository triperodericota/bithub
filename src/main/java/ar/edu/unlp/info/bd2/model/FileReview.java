package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
public class FileReview extends PersistentObject {


    @ManyToOne
    @JoinColumn(name="review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name="file_id")
    private File file;

    @Column
    private Integer line;

    @Column
    private String comment;

    public FileReview() {
    }

    public FileReview(Review aReview, File aFile, Integer lineNumber, String aComment){
        this.setReview(aReview);
        this.setFile(aFile);
        this.setLine(lineNumber);
        this.setComment(aComment);
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

