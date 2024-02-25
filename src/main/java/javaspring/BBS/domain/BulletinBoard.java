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
    @ManyToOne
    @JoinColumn(name="member_name")
    private Member member;
}
