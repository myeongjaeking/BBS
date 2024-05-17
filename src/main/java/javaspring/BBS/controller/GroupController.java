package javaspring.BBS.controller;

import jakarta.servlet.http.HttpSession;
import javaspring.BBS.domain.Group;
import javaspring.BBS.domain.Member;
import javaspring.BBS.repository.MemberRepository;
import javaspring.BBS.service.AlarmService;
import javaspring.BBS.service.GroupService;
import javaspring.BBS.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final AlarmService alarmService;




    @GetMapping("/groupCreate")
    public String createForm() {
        return "groups/createGroup";
    }

    @PostMapping("groupCreate")
    public String create(GroupForm form, HttpSession session) throws Exception {
        Group group = new Group();
        group.setGroup_name(form.getGroup_name());
        group.setGroup_password(form.getGroup_password());
        group.setGroup_private(form.isGroup_private());
        group.setGroup_max_people(form.getGroup_max_people());
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember != null) {
            Long memberId = loggedInMember.getMember_id();
            groupService.groupCreate(group, memberId);
            return "/members/success";
        }
        return "/members/success";
    }

    @GetMapping("/groupList")
    public String list(Model model, HttpSession session) throws Exception{
        List<Group> groups = groupService.findAll();
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if(loggedInMember!=null){
            String mName = loggedInMember.getMember_name();
            model.addAttribute("MemberName",mName);
            model.addAttribute("groups", groups);
        }
        return "groups/groupList";
    }
//    @GetMapping("bulletinboards")
//    public String list(Model model){
//        List<BulletinBoard> bulletinBoards = bulletinBoardService.findBulletinBoards();
//        model.addAttribute("bulletinboards",bulletinBoards);
//        return "bulletinboards/bulletinboardList";
//    }



    @GetMapping("/join/{id}")
        public String groupJoin(@PathVariable("id") Long id, HttpSession session) throws Exception {
            // Optional<Group> optionalGroup = groupService.findByGroup_id(id);
            Member loggedInMember = (Member) session.getAttribute("loggedInMember");
            if (loggedInMember != null) {
                Long memberId = loggedInMember.getMember_id();
                groupService.groupJoin(id, memberId);
                alarmService.alarmSend(id,memberId);
                return "redirect:/groupList";
            }
            return "redirect:/login";
    }
    @GetMapping("/groupMember")
    public String groupShow(Model model, HttpSession session) throws Exception {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember != null) {
            Long memberId = loggedInMember.getMember_id();
            //List<GroupMember> groups = groupService.myGroup(memberId);
            List<Object[]> groups = groupService.myGroup(memberId);
            model.addAttribute("groupDataList", groups);
            return "groups/groupMember";
        }
        return "redirect:/login";
    }
    @GetMapping("/deleteGroup/{id}")
    public String groupForm(@PathVariable("id")Long id, Model model){
        Optional<Group> optionalGroup = groupService.findByGroup_id(id);
        optionalGroup.ifPresent(group -> model.addAttribute("group",group));
        return "groups/deleteGroup";
    }
    @PostMapping("/deleteGroup/{id}")
    public String groupDelete(@PathVariable("id")Long id){
        groupService.groupDelete(id);
        return "members/success";
    }
    @GetMapping("/leaveGroup/{id}")
    public String groupLeaveForm(@PathVariable("id")Long id, Model model){
        Optional<Group> optionalGroup = groupService.findByGroup_id(id);
        optionalGroup.ifPresent(group -> model.addAttribute("group",group));
        return "groups/leaveGroup";
    }
    @PostMapping("/leaveGroup/{id}")
    public String groupLeave(@PathVariable("id")Long id,HttpSession session){
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        Long memberId = loggedInMember.getMember_id();
        groupService.groupLeave(id,memberId);
        return "members/success";
    }

//    @GetMapping("/delete/{id}")
//    public String deleteForm(@PathVariable("id") Long id, Model model) {
//        // ID를 사용하여 기존 게시판 정보를 가져옴
//        Optional<BulletinBoard> optionalBulletinBoard = bulletinBoardService.findOne(id);
//
//        // (Thyleaf템플릿에서 사용할 모델) optionalBulletinBoard가 존재하는 경우 모델에 추가
//        optionalBulletinBoard.ifPresent(bulletinBoard -> model.addAttribute("bulletinboard", bulletinBoard));
//
//        return "bulletinboards/deleteBulletinBoard";
//    }
//
//    @PostMapping("/delete/{id}")
//    public String delete(@PathVariable("id") Long id) {
//        bulletinBoardService.delete(id);
//        return "members/success";
//    }
}
