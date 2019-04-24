package ar.edu.unlp.info.bd2.models;

import javax.persistence.*;

@Entity
public class File {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String content;

    public File() { }


    public File(String content, String name) {
        this.setFilename(name);
        this.setContent(content);
    }


    public Long getId() {
        return id;
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


}
