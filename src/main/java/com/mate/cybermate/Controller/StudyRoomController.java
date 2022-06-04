package com.mate.cybermate.Controller;

import com.mate.cybermate.Service.MemberService;
import com.mate.cybermate.Service.StudyRoomService;
import com.mate.cybermate.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class StudyRoomController {

    private final MemberService memberService;
    private final StudyRoomService studyRoomService;

    @GetMapping("/members/studyRoom/{srId}")
    public String showStudyRoom(@PathVariable(name="srId") Long srId, Model model, Principal principal){


        Member member=memberService.getMember(principal.getName());

        model.addAttribute("srId",srId);


        return "studyRoom/detail";



    }

    @PostMapping("/members/studyRoom/setGoal/{srId}")
    public String doSetGoal(@PathVariable(name="srId") Long srId,@RequestParam(name="period")String period,@RequestParam(name="count")String count){
        String goal="";

        if(period.equals("1일")||period.equals("1주일")||period.equals("1달")){
            goal=period+" 동안 총 "+count+"개의 강의 수강";
        }

        studyRoomService.updateGoal(srId,goal);

        return "redirect:/members/studyRoom/"+srId;
    }

    @PostMapping("/members/apply/{srId}")
    public void doApply(){

    }


}
