package javaspring.BBS.controller;

import jakarta.servlet.http.HttpSession;
import javaspring.BBS.domain.Alarm;
import javaspring.BBS.domain.BulletinBoard;
import javaspring.BBS.domain.GroupMember;
import javaspring.BBS.domain.Member;
import javaspring.BBS.repository.AlarmRepository;
import javaspring.BBS.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AlarmController {
    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService){
        this.alarmService =alarmService;
    }
    @GetMapping("/subscribe")
    public String alarmList(Model model,HttpSession session) throws Exception{
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember != null) {
            List<Alarm>alarms = alarmService.alarmSender(loggedInMember.getMember_name());
            model.addAttribute("alarmsReceiver", alarms);
            return "alarm/receive";
        }
        return "redirect:/login";
    }
}
