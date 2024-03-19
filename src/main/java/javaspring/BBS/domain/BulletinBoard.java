package javaspring.BBS.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "bulletin_board") //내가 원하는 테이블
public class BulletinBoard {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    private String filename;
    private String filepath;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    private String fileUrl;
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;
}
