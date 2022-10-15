package com.mate.cybermate.Controller;

import com.mate.cybermate.DTO.StudyRoomApply.AutoMatchingApplySaveForm;
import com.mate.cybermate.Service.MemberService;
import com.mate.cybermate.Service.StudyRoomApplyService;
import com.mate.cybermate.Service.StudyRoomService;
import com.mate.cybermate.domain.Member;
import com.mate.cybermate.domain.Study_Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class StudyRoomApplyController {

    private final StudyRoomService studyRoomService;
    private final MemberService memberService;
    private final StudyRoomApplyService studyRoomApplyService;

    @PostMapping("/boards/1/{srId}")
    public String doApply(@PathVariable(name = "srId") Long srId, Principal principal) {
        Study_Room makeRoom = studyRoomService.findById(srId);
        Member member = memberService.getMember(principal.getName());

        if(makeRoom.getMaxNum()== makeRoom.getCurrentNum()){
            return "redirect:/boards/1";
        }

        int toDupleApplyCheck=0;

        if(makeRoom.getStudyRoomApply().size()==0){
            studyRoomApplyService.addStudyRoomApply(srId, member);
        }

        else{

            for (int i = 0; i < makeRoom.getStudyRoomApply().size(); i++) {

                if(makeRoom.getStudyRoomApply().get(i).getMember().getLoginId().equals(member.getLoginId())){
                    toDupleApplyCheck++;
                }

                if(i==makeRoom.getStudyRoomApply().size()-1){
                    if(toDupleApplyCheck==0){
                        studyRoomApplyService.addStudyRoomApply(srId, member);
                    }
                }


            }

        }
        return "redirect:/boards/1";

    }

    @GetMapping("/boards/1/autoMatchingApply")
    public String showAutoMatching(Model model){


        model.addAttribute("autoMatchingApplySaveForm",new AutoMatchingApplySaveForm());

        return "studyRoomList/autoMatchingApply";
    }

    @PostMapping("/boards/1/autoMatchingApply")
    public String doAutoMatching(@Validated AutoMatchingApplySaveForm autoMatchingApplySaveForm, BindingResult bindingResult,Model model, Principal principal){


        if(bindingResult.hasErrors()){
            return "studyRoomList/autoMatchingApply";
        }

        try{
            Member member = memberService.getMember(principal.getName());
            studyRoomApplyService.addStudyRoomAutoMatchingApply(member,autoMatchingApplySaveForm);

        }
        catch (Exception e){
            model.addAttribute("err_msg",e.getMessage());
        }

        return "redirect:/boards/1";
    }

}