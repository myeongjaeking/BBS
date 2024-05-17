package javaspring.BBS.service;

import jakarta.persistence.EntityManager;
import javaspring.BBS.domain.Alarm;
import javaspring.BBS.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Configuration
@EnableTransactionManagement
public class SpringConfig {
    private final EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em){
        this.em=em;
    }
    @Bean
    public BulletinBoardService bulletinBoardService(){
        return new BulletinBoardService(bulletinBoardRepository(),memberRepository());
    }
    @Bean
    public GroupRepository groupRepository(){return new JpaGroupRepository(em);}
    @Bean
    public GroupMemberRepository groupMemberRepository(){return new JpaGroupMemberRepository(em);}
    @Bean
    public GroupService groupService(){return new GroupService(groupRepository(),memberRepository(),groupMemberRepository(),alarmService());}
    @Bean
    public BulletinBoardRepository bulletinBoardRepository(){
        return new JpaBulletinBoardRepository(em);
    }
    @Bean
    public MemberService memberService(){return new MemberService(memberRepository());}
    @Bean
    public MemberRepository memberRepository(){return new JpaMemberRepository(em);}
    @Bean
    public AlarmService alarmService(){return new AlarmService(alarmRepository());}
    @Bean
    public AlarmRepository alarmRepository(){return new JpaAlarmRepository(em);}



}
