package javaspring.BBS.controller;

import jakarta.servlet.http.HttpSession;
import javaspring.BBS.domain.Group;
import javaspring.BBS.domain.GroupMember;
import javaspring.BBS.domain.Member;
import javaspring.BBS.repository.MemberRepository;
import javaspring.BBS.service.AlarmService;
import javaspring.BBS.service.GroupService;
import javaspring.BBS.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

@RestController
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

    @PostMapping("/groupCreate")
    public String create(@RequestBody GroupForm form, HttpSession session) throws Exception {
        Group group = new Group();
        group.setGroup_name(form.getGroup_name());
        group.setGroup_password(form.getGroup_password());
        group.setGroup_private(form.isGroup_private());
        group.setGroup_max_people(form.getGroup_max_people());


        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember != null) {
            Long memberId = loggedInMember.getMember_id();
            group.setGroup_MemberName(loggedInMember.getMember_name());

            groupService.groupCreate(group, memberId);
            return "성공";
        }
        return "실패";
    }

    @GetMapping("/groupList")
    public String list(Model model, HttpSession session) throws Exception {
        List<Group> groups = groupService.findAll();

        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember != null) {
            String mName = loggedInMember.getMember_name();
            model.addAttribute("MemberName", mName);
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
            alarmService.alarmSend(id, memberId);
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

    //비밀번호 있는 방 비밀번호 입력하여 가입
    @PostMapping("/groupPassword/{id}")
    public boolean groupPassword(@PathVariable("id") Long id, @RequestBody GroupForm groupForm, HttpSession session) throws Exception {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember != null) {
            Long memberId = loggedInMember.getMember_id();
            Optional<Group> optionalGroup = groupService.findByGroup_id(id);
            if (optionalGroup.isPresent()) {
                Group group = optionalGroup.get();
                if (group.getGroup_password().equals(groupForm.getGroup_password())) {
                    groupService.groupJoin(id, memberId);
                    alarmService.alarmSend(id, memberId);
                    return true;
                }
            }
        }
        return false;
    }
    @PostMapping("/groupChangePassword/{id}")
    public boolean groupChangePassword(@PathVariable("id") Long id, @RequestBody GroupForm groupForm, HttpSession session) throws Exception {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember != null) {
            System.out.println("Logged in member ID: " + loggedInMember.getMember_id());
            System.out.println("Logged in member name: " + loggedInMember.getMember_name());

            Long memberId = loggedInMember.getMember_id();
            Optional<Group> optionalGroup = groupService.findByGroup_id(id);
            if (optionalGroup.isPresent()) {
                Group group = optionalGroup.get();
                System.out.println("Group Member Name: " + group.getGroup_MemberName());
                System.out.println("Logged In Member Name: " + loggedInMember.getMember_name());

                if (group.getGroup_MemberName().equals(loggedInMember.getMember_name())) {
                    System.out.println("Group member names match");
                    System.out.println("Current group private status: " + group.isGroup_private());
                    if (!group.isGroup_private()) {
                        System.out.println("Setting group to private and updating password");
                        group.setGroup_private(true);
                        group.setGroup_password(groupForm.getGroup_password());
                    } else {
                        System.out.println("Setting group to public and removing password");
                        group.setGroup_private(false);
                        group.setGroup_password(null);
                    }
                    groupService.edit_group(group);
                    return true;
                } else {
                    System.out.println("권한 없음");
                }
            } else {
                System.out.println("Group not found");
            }
        } else {
            System.out.println("Logged in member not found in session");
        }
        return false;
    }
    //내가 가입한 그룹들
    @PostMapping("/myGroupMembers")
    public List<Object[]> myGroupMembers(HttpSession session) throws Exception {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember != null) {
            Long memberId = loggedInMember.getMember_id();
            return groupService.myGroup(memberId);
        }
        return null;
    }

    @GetMapping("/deleteGroup/{id}")
    public String groupForm(@PathVariable("id") Long id,@RequestBody GroupForm groupForm, Model model) {
        Optional<Group> optionalGroup = groupService.findByGroup_id(id);
        optionalGroup.ifPresent(group -> model.addAttribute("group", group));
        return "groups/deleteGroup";
    }

    //그룹장 삭제d
    @PostMapping("/deleteGroup/{id}")
    public String groupDelete(@PathVariable("id") Long id) {
        groupService.groupDelete(id);
        return "members/success";
    }

    @GetMapping("/leaveGroup/{id}")
    public String groupLeaveForm(@PathVariable("id") Long id, Model model) {
        Optional<Group> optionalGroup = groupService.findByGroup_id(id);
        optionalGroup.ifPresent(group -> model.addAttribute("group", group));
        return "groups/leaveGroup";
    }

    //그룹원 탈퇴
    @PostMapping("/leaveGroup/{id}")
    public String groupLeave(@PathVariable("id") Long id, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        Long memberId = loggedInMember.getMember_id();
        groupService.groupLeave(id, memberId);
        return "members/success";
    }

}

