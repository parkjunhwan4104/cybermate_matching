package com.mate.cybermate.Controller;

import com.mate.cybermate.DTO.StudyRoomApply.StudyRoomApplySaveForm;
import com.mate.cybermate.Service.StudyRoomApplyService;
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
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MakeRoomController {

    private final BoardService boardService;
    private final StudyRoomApplyService studyRoomApplyService;
    private final MemberService memberService;
    private final StudyRoomService studyRoomService;


    @GetMapping("/members/makeRoom")
    public String showApply(Model model,Principal principal){

        Member member=memberService.getMember(principal.getName());

        model.addAttribute("studyRoomApplySaveForm",new StudyRoomApplySaveForm());


        return "StudyRoomApply/add";
    }

    @PostMapping("/members/makeRoom")
    public String doApply(@Validated StudyRoomApplySaveForm studyRoomApplySaveForm, BindingResult bindingResult, Principal principal, Model model){
        if(bindingResult.hasErrors()){
            return "StudyRoomApply/add";
        }

        try{
            Long num=Long.valueOf(1);

            Board board=boardService.getBoard(num);

            Member member=memberService.getMember(principal.getName());


            studyRoomApplyService.saveRoom(studyRoomApplySaveForm,member,board);





        }
        catch(Exception e){
            model.addAttribute("error_msg",e.getMessage());
            return "StudyRoomApply/add";
        }
        return "redirect:/";

    }



}
