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
@Column(name="member_password",nullable = false)
    private String member_password;

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
    @OneToMany(mappedBy = "member")
    List<BulletinBoard> bulletinBoards = new ArrayList<BulletinBoard>();

    public List<GroupMember> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<GroupMember> groupMembers) {
        this.groupMembers = groupMembers;
    }

    @OneToMany(mappedBy = "member")
    private List<GroupMember>groupMembers;
    public List<BulletinBoard> getBulletinBoards() {
        return bulletinBoards;
    }
//    @OneToMany(mappedBy = "member_receiver")
//    private List<Alarm>alarms_receiver;
//
//    public List<Alarm> getAlarms_receiver() {
//        return alarms_receiver;
//    }
//
//    public void setAlarms_receiver(List<Alarm> alarms_receiver) {
//        this.alarms_receiver = alarms_receiver;
//    }
//
//    public List<Alarm> getAlarms_sender() {
//        return alarms_sender;
//    }
//
//    public void setAlarms_sender(List<Alarm> alarms_sender) {
//        this.alarms_sender = alarms_sender;
//    }
//
//    @OneToMany(mappedBy = "member_sender")
//    private List<Alarm>alarms_sender;

    public void setBulletinBoards(List<BulletinBoard> bulletinBoards) {
        this.bulletinBoards = bulletinBoards;
    }

}
