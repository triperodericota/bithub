package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
public class FileReview extends PersistentObject {


    @ManyToOne
    @JoinColumn(name="review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name="file_id")
    private File reviewedFile;

    @Column
    private Integer lineNumber;

    @Column
    private String comment;

    public FileReview() {
    }

    public FileReview(Review aReview, File aFile, Integer lineNumber, String aComment){
        this.setReview(aReview);
        this.setReviewedFile(aFile);
        this.setLineNumber(lineNumber);
        this.setComment(aComment);
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public File getReviewedFile() {
        return reviewedFile;
    }

    public void setReviewedFile(File reviewedFile) {
        this.reviewedFile = reviewedFile;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}

