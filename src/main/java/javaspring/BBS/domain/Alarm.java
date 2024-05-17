package javaspring.BBS.domain;

import jakarta.persistence.*;

@Entity
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarm_id;
//    @ManyToOne
//    @JoinColumn(name="member_receiver_id")
//    private Member member_receiver;
//    @ManyToOne
//    @JoinColumn(name="member_sender_id")
//    private Member member_sender;
    private String receiver;
    private String sender;

    public String getGroup_bulletinBoard() {
        return group_bulletinBoard;
    }

    public void setGroup_bulletinBoard(String group_bulletinBoard) {
        this.group_bulletinBoard = group_bulletinBoard;
    }

    public Long getGroup_bulletinBoard_id() {
        return group_bulletinBoard_id;
    }

    public void setGroup_bulletinBoard_id(Long group_bulletinBoard_id) {
        this.group_bulletinBoard_id = group_bulletinBoard_id;
    }

    private String group_bulletinBoard;

    private Long group_bulletinBoard_id;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    public Long getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(Long alarm_id) {
        this.alarm_id = alarm_id;
    }

//    public Member getMember_receiver() {
//        return member_receiver;
//    }
//
//    public void setMember_receiver(Member member_receiver) {
//        this.member_receiver = member_receiver;
//    }
//
//    public Member getMember_sender() {
//        return member_sender;
//    }
//
//    public void setMember_sender(Member member_sender) {
//        this.member_sender = member_sender;
//    }
}
