package javaspring.BBS.service;

import jakarta.transaction.Transactional;
import javaspring.BBS.domain.*;
import javaspring.BBS.repository.AlarmRepository;
import javaspring.BBS.repository.GroupMemberRepository;
import javaspring.BBS.repository.GroupRepository;
import javaspring.BBS.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service

public class AlarmService {
    private final AlarmRepository alarmRepository;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository1){

        this.alarmRepository = alarmRepository1;
    }
    public void alarmSend(Long groupId,Long memberId) {

        alarmRepository.save(groupId,memberId);
    }
    public List<Alarm> alarmSender(String memberName){
        return alarmRepository.alarmSender(memberName);
    }
}
