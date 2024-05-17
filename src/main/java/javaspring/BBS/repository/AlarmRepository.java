package javaspring.BBS.repository;

import javaspring.BBS.domain.Alarm;
import javaspring.BBS.domain.BulletinBoard;
import javaspring.BBS.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmRepository{

    List<Alarm> alarmSender(String  senderName);
    Alarm save(Long groupId,Long memberId);
}
