package com.mate.cybermate.Controller;

import com.mate.cybermate.DTO.AcceptHistory.AcceptHistoryDTO;
import com.mate.cybermate.DTO.ApplyHistory.ApplyHistoryDTO;
import com.mate.cybermate.DTO.ApplyHistory.StudyRoomApplyDTO;
import com.mate.cybermate.DTO.StudyRoomApply.StudyRoomApplySaveForm;
import com.mate.cybermate.DTO.StudyRoomApply.StudyRoomApplySetLectureForm;
import com.mate.cybermate.Service.ApplyHistoryService;
import com.mate.cybermate.Service.MemberService;
import com.mate.cybermate.Service.StudyRoomApplyService;
import com.mate.cybermate.Service.StudyRoomService;
import com.mate.cybermate.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudyRoomController {

    private final MemberService memberService;
    private final StudyRoomService studyRoomService;
    private final ApplyHistoryService applyHistoryService;
    private final StudyRoomApplyService studyRoomApplyService;

    @GetMapping("/members/studyRoom/{srId}")
    public String showStudyRoom(@PathVariable(name="srId") Long srId, Model model, Principal principal){


        Member member=memberService.getMember(principal.getName());

        Study_Room studyRoom=studyRoomService.findById(srId);
        ApplyHistory applyHistory=applyHistoryService.getApplyHistoryFindById(srId);

        if(applyHistory.getMember().getNickName().equals(member.getNickName())){
            model.addAttribute("myPercent",member.getLecturePercent()*100);
            model.addAttribute("teamPercent",studyRoom.getMatesPercent()*100);
            model.addAttribute("currentNo",member.getLectureNo());

            model.addAttribute("srId",srId);

            model.addAttribute("goal",studyRoom.getGoal());

            return "studyRoom/detailForOwner";
        }
        else{

            model.addAttribute("myPercent",member.getLecturePercent()*100);
            model.addAttribute("teamPercent",studyRoom.getMatesPercent()*100);
            model.addAttribute("currentNo",member.getLectureNo());

            model.addAttribute("srId",srId);

            model.addAttribute("goal",studyRoom.getGoal());

            return "studyRoom/detail";
        }









    }

    @PostMapping("/members/studyRoom/{srId}")
    public String doCheck(@PathVariable(name="srId") Long srId,@RequestParam(name="count")String count,Model model,Principal principal){
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
        String formatedNow = now.format(formatter);




        Member member=memberService.getMember(principal.getName());

        Study_Room studyRoom=studyRoomService.findById(srId);
        ApplyHistory applyHistory=applyHistoryService.getApplyHistoryFindById(srId);

        if(applyHistory.getMember().getNickName().equals(member.getNickName())){

            studyRoomService.updateLectureNo(Long.valueOf(Integer.parseInt(count)),studyRoom,member);
            studyRoomService.updateStudyRoomPercent(studyRoom,member);


            model.addAttribute("myPercent",member.getLecturePercent()*100);
            model.addAttribute("teamPercent",studyRoom.getMatesPercent()*100);
            model.addAttribute("goal",studyRoom.getGoal());
            model.addAttribute("now",formatedNow);
            model.addAttribute("count",count);
            model.addAttribute("srName",studyRoom.getRoomName());

            return "studyRoom/detailForOwner";
        }

        else{
            studyRoomService.updateLectureNo(Long.valueOf(Integer.parseInt(count)),studyRoom,member);
            studyRoomService.updateStudyRoomPercent(studyRoom,member);



            model.addAttribute("myPercent",member.getLecturePercent()*100);
            model.addAttribute("teamPercent",studyRoom.getMatesPercent()*100);
            model.addAttribute("goal",studyRoom.getGoal());
            model.addAttribute("now",formatedNow);
            model.addAttribute("count",count);
            model.addAttribute("srName",studyRoom.getRoomName());
            return "studyRoom/detail";
        }




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

    @GetMapping("/members/accept/{srId}")
    public String showAccept(@PathVariable(name="srId") Long srId,Principal principal,Model model){

        Member member=memberService.getMember(principal.getName());

        StudyRoomApply sra=studyRoomApplyService.findById(srId);

        List<AcceptHistoryDTO> acceptHistoryDTOList=studyRoomApplyService.getAcceptHistoryDTOListById(srId,member);



        model.addAttribute("historyList",acceptHistoryDTOList);
        model.addAttribute("srId",srId);
        model.addAttribute("srName",sra.getRoomName());


        return "member/accept";
    }

    @PostMapping("/members/accept/{srId}/apply/{sraId}")
    public String doAccept(@PathVariable(name="srId") Long srId,@PathVariable(name="sraId") Long sraId, Principal principal,Model model){
        Member member=memberService.getMember(principal.getName());

        List<StudyRoomApply> applyList=studyRoomApplyService.getListBySrId(srId,member);

        List<AcceptHistory> acceptHistories= studyRoomApplyService.getAcceptHistoryBySrId(srId);

        for(int i=0;i<acceptHistories.size();i++){
            if(acceptHistories.get(i).getStudyApplyId()==sraId){


                studyRoomApplyService.setAccept(sraId,true);
                Study_Room studyRoom=studyRoomService.findById( acceptHistories.get(i).getStudyRoomApply().getStudyRoom().getSrId());
                studyRoom.setCurrentNum();
                studyRoomApplyService.deleteAcceptHistory(acceptHistories.get(i).getAhId());
                acceptHistories.remove(i);


            }
        }

        model.addAttribute("historyList",acceptHistories);
        model.addAttribute("srId",srId);


        return "member/accept";
    }

    @PostMapping("/members/deny/{srId}/apply/{sraId}")
    public String doDeny(@PathVariable(name="srId") Long srId,@PathVariable(name="sraId") Long sraId,Principal principal,Model model){
        Member member=memberService.getMember(principal.getName());

        List<StudyRoomApply> applyList=studyRoomApplyService.getListBySrId(srId,member);

        List<AcceptHistory> acceptHistories= studyRoomApplyService.getAcceptHistoryBySrId(srId);

        for(int i=0;i<acceptHistories.size();i++){
            if(acceptHistories.get(i).getStudyApplyId()==sraId){
                studyRoomApplyService.deleteAcceptHistory(acceptHistories.get(i).getAhId());
                acceptHistories.remove(i);


            }
        }

        model.addAttribute("historyList",acceptHistories);
        model.addAttribute("srId",srId);

        return "member/accept";

    }

    /*@GetMapping("/members/studyRoom/set/lectureNo/{srId}")
    public String showApply(Model model,Principal principal,@PathVariable(name="srId") Long srId){

        Member member=memberService.getMember(principal.getName());

        model.addAttribute("studyRoomApplySetLectureForm",new StudyRoomApplySetLectureForm());

        model.addAttribute("srId",srId);
        return "StudyRoomApply/lectureSet";
    }
*/

    @PostMapping("/members/studyRoom/set/lectureNo/{srId}")
    public String doSet(@PathVariable(name="srId") Long srId, @Validated StudyRoomApplySetLectureForm studyRoomApplySetLectureForm, BindingResult bindingResult, Principal principal, Model model){
        if(bindingResult.hasErrors()){
            return "StudyRoomApply/lectureSet";
        }
        try{


            Member member=memberService.getMember(principal.getName());
            memberService.setLectureNo(studyRoomApplySetLectureForm,member.getLoginId());

            model.addAttribute("srId",srId);

        }
        catch(Exception e){
            model.addAttribute("error_msg",e.getMessage());
            return "StudyRoomApply/lectureSet";
        }
        return "redirect:/";

    }

    @GetMapping("/members/studyRoom/boards")
    public String showBoard(){

        return "studyRoom/articleList";
    }





}
