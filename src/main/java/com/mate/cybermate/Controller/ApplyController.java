package com.mate.cybermate.Controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.mate.cybermate.Config.Role;
import com.mate.cybermate.DTO.apply.applySaveForm;
import com.mate.cybermate.Service.ApplyService;
import com.mate.cybermate.Service.BoardService;
import com.mate.cybermate.Service.MemberService;
import com.mate.cybermate.Service.StudyRoomService;
import com.mate.cybermate.domain.Board;
import com.mate.cybermate.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ApplyController {

    private final BoardService boardService;
    private final ApplyService applyService;
    private final MemberService memberService;
    private final StudyRoomService studyRoomService;


    @GetMapping("/members/makeApply")
    public String showApply(Model model,Principal principal){

        Member member=memberService.getMember(principal.getName());

        if(member.getStudyRoom()!=null) {
            Long id = member.getStudyRoom().getSr_id();

            model.addAttribute("srId",id);
        }
        model.addAttribute("applySaveForm",new applySaveForm());


        return "apply/add";
    }

    @PostMapping("/members/makeApply")
    public String doApply(@Validated applySaveForm applysaveform, BindingResult bindingResult, Principal principal,Model model){
        if(bindingResult.hasErrors()){
            return "apply/add";
        }

        try{
            Long num=Long.valueOf(1);

            Board board=boardService.getBoard(num);

            Member member=memberService.getMember(principal.getName());

            member.setAuthority(Role.OWNER);

            List<Member> members=new ArrayList<>();

            members.add(member);

            if(member.getMatchingApply()==null){
                applyService.saveApply(applysaveform,member,board);

                studyRoomService.saveStudyRoom(members);
            }

            else{
                return "redirect:/members/applyError";
            }




        }
        catch(Exception e){
            model.addAttribute("error_msg",e.getMessage());
            return "apply/add";
        }
        return "redirect:/";

    }

    @GetMapping("/members/applyError")
    public String showApplyError(){

        return "apply/matchingPopup";
    }

}
