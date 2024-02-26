package javaspring.BBS.domain;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="member")
public class Member {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;
@Column(name="member_name",nullable = false)
    private String member_name;
    private String member_password;
    @OneToMany(mappedBy = "member")
    List<BulletinBoard> bulletinBoards = new ArrayList<BulletinBoard>();

    public Long getMember_id() {
        return member_id;
    }

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_password() {
        return member_password;
    }

    public void setMember_password(String member_password) {
        this.member_password = member_password;
    }

    public List<BulletinBoard> getBulletinBoards() {
        return bulletinBoards;
    }

    public void setBulletinBoards(List<BulletinBoard> bulletinBoards) {
        this.bulletinBoards = bulletinBoards;
    }

}
