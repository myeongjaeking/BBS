package javaspring.BBS.repository;

import jakarta.persistence.EntityManager;
import javaspring.BBS.domain.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

public class JpaAlarmRepository implements AlarmRepository {
    private  final EntityManager em;

    public JpaAlarmRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Alarm> alarmSender(String senderName) {
        return em.createQuery("select a from Alarm a where a.receiver=:receiver",Alarm.class)
                .setParameter("receiver",senderName)
                .getResultList();
    }



    @Override
    public Alarm save(Long groupId,Long memberId){
        Member sender = em.find(Member.class,memberId);
        Group group = em.find(Group.class,groupId);
        if(sender!=null){

            Optional<GroupMember> existingMember = group.getGroupMembers().stream()
                    .filter(groupMember -> groupMember.getGroup().getGroup_id().equals(groupId) // 그룹 ID 일치 여부 확인
                            && groupMember.isGroup_role()) // 그룹 역할이 true인 멤버 필터링
                    .findAny();

            if(existingMember.isPresent()){

                Member receiver = existingMember.get().getMember();
                Alarm alarm = new Alarm();
                alarm.setSender(sender.getMember_name());
                alarm.setReceiver(receiver.getMember_name());
                em.persist(alarm);
            }
        }
        return null;
    }

    private GroupRepository groupRepository;

}
