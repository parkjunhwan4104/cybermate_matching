package com.mate.cybermate.Controller;


import com.mate.cybermate.DTO.ApplyHistory.ApplyHistoryDTO;
import com.mate.cybermate.DTO.StudyRoom.StudyRoomSaveForm;
import com.mate.cybermate.Service.*;
import com.mate.cybermate.domain.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudyRoomController {

    private final MemberService memberService;
    private final StudyRoomService studyRoomService;
    private final ApplyHistoryService applyHistoryService;
    private final StudyRoomApplyService studyRoomApplyService;
    private final BoardService boardService;
    private final StudyRoomBoardService studyRoomBoardService;
    private final TakeLectureService takeLectureService;

    @GetMapping("/members/makeRoom")
    public String showApply(Model model,Principal principal){

        Member member=memberService.getMember(principal.getName());

        model.addAttribute("studyRoomSaveForm",new StudyRoomSaveForm());


        return "studyRoom/studyRoomAdd";
    }

    @PostMapping("/members/makeRoom")
    public String doApply(@Validated StudyRoomSaveForm studyRoomSaveForm, BindingResult bindingResult, Principal principal, Model model){
        if(bindingResult.hasErrors()){
            return "studyRoom/studyRoomAdd";
        }

        try{
            Long num=Long.valueOf(1);

            Board board=boardService.getBoard(num);

            Member member=memberService.getMember(principal.getName());

            studyRoomService.saveRoom(studyRoomSaveForm,member,board);



        }
        catch(Exception e){
            model.addAttribute("error_msg",e.getMessage());
            return "studyRoom/studyRoomAdd";
        }
        return "redirect:/";

    }

    @GetMapping("/members/studyRoom/{srId}")
    public String showStudyRoom(@PathVariable(name="srId") Long srId, Model model, Principal principal, HttpServletResponse response) throws Exception{

        response.setContentType("text/html; charset=euc-kr");

        PrintWriter out = response.getWriter();

        Member member=memberService.getMember(principal.getName());

        Study_Room studyRoom=studyRoomService.findById(srId);

        StudyRoomBoard studyRoomBoard=studyRoomBoardService.getSrBoardById(srId);

        List<TakeLectureHistory> takeLectureHistories=takeLectureService.getTakeLectureHistoryByMemberAndSrId(member,srId);

        LocalDateTime now=LocalDateTime.now();
        if(studyRoom.getGoalLectureNo()!=0&&studyRoom.getGoalTime()!=0) {
            LocalDateTime deadLineTime = studyRoom.getGoalSettingTime().plusHours(24);


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
            String formattedSetGoalTime = studyRoom.getGoalSettingTime().format(formatter);

            if (takeLectureHistories.size() == 0) {
                if (now.isAfter(deadLineTime)) {
                    studyRoomService.setInitialGoal(studyRoom);
                    out.println("<script>alert('설정한 목표의 2/3이상을 달성하지 못했습니다.'); history.go(-1); </script>");

                    out.flush();


                }
            }
            else {
                for (int i = 0; i < takeLectureHistories.size(); i++) {
                    if (takeLectureHistories.get(i).getRegDay().equals(formattedSetGoalTime)) {


                        if (now.isAfter(deadLineTime)) {

                            if (takeLectureHistories.get(i).getLectureNum() < (2 / 3) * studyRoom.getGoalLectureNo()) {

                                studyRoomService.setInitialGoal(studyRoom);
                                out.println("<script>alert('설정한 목표의 2/3이상을 달성하지 못했습니다.'); history.go(-1); </script>");

                                out.flush();


                            }
                            else{
                                break;
                            }

                        }

                    }

                    if (i == takeLectureHistories.size() - 1) {

                        studyRoomService.setInitialGoal(studyRoom);
                        out.println("<script>alert('설정한 목표의 2/3이상을 달성하지 못했습니다.'); history.go(-1); </script>");

                        out.flush();


                    }
                }
            }
        }





        if(studyRoom.getMember().getLoginId().equals(member.getLoginId())){



          //  List<TakeLectureHistory> takeLectureHistories=takeLectureService.getTakeLectureHistoryByMemberAndSrId(member,srId);

            model.addAttribute("myPercent",studyRoomService.getPersonalLecturePercentBySrId(member,srId)*100);
            model.addAttribute("teamPercent",studyRoomService.getMatesLecturePercentBySrId(studyRoom)*100);
            model.addAttribute("srName",studyRoom.getRoomName());
            model.addAttribute("srId",srId);
            model.addAttribute("srBoardId",studyRoomBoard.getSrBoardId());
            model.addAttribute("goal",studyRoom.getGoal());
            model.addAttribute("takeLectureHistoryList",takeLectureHistories);

            return "studyRoom/detailForOwner";
        }
        else{
        //    List<TakeLectureHistory> takeLectureHistories=takeLectureService.getTakeLectureHistoryByMemberAndSrId(member,srId);

            model.addAttribute("myPercent",studyRoomService.getPersonalLecturePercentBySrId(member,srId)*100);
            model.addAttribute("teamPercent",studyRoomService.getMatesLecturePercentBySrId(studyRoom)*100);
            model.addAttribute("srName",studyRoom.getRoomName());
            model.addAttribute("srId",srId);
            model.addAttribute("srBoardId",studyRoomBoard.getSrBoardId());
            model.addAttribute("goal",studyRoom.getGoal());
            model.addAttribute("takeLectureHistoryList",takeLectureHistories);

            return "studyRoom/detail";
        }









    }

    @PostMapping("/members/studyRoom/{srId}")
    public String doCheck(@PathVariable(name="srId") Long srId,@RequestParam(name="count")String count,Model model,Principal principal){

        Member member=memberService.getMember(principal.getName());

        Study_Room studyRoom=studyRoomService.findById(srId);



        StudyRoomBoard studyRoomBoard=studyRoomBoardService.getSrBoardById(srId);

        if(studyRoom.getMember().getNickName().equals(member.getNickName())){

            studyRoomService.updateLectureNo(Long.valueOf(Integer.parseInt(count)),studyRoom,member);

            List<TakeLectureHistory> takeLectureHistories=takeLectureService.getTakeLectureHistoryByMemberAndSrId(member,srId);

            model.addAttribute("myPercent",studyRoomService.getPersonalLecturePercentBySrId(member,srId)*100);
            model.addAttribute("teamPercent",studyRoomService.getMatesLecturePercentBySrId(studyRoom)*100);
            model.addAttribute("goal",studyRoom.getGoal());

            model.addAttribute("count",count);
            model.addAttribute("srName",studyRoom.getRoomName());
            model.addAttribute("srBoardId",studyRoomBoard.getSrBoardId());
            model.addAttribute("takeLectureHistoryList",takeLectureHistories);

            return "studyRoom/detailForOwner";
        }

        else{
            studyRoomService.updateLectureNo(Long.valueOf(Integer.parseInt(count)),studyRoom,member);

            List<TakeLectureHistory> takeLectureHistories=takeLectureService.getTakeLectureHistoryByMemberAndSrId(member,srId);

            model.addAttribute("myPercent",studyRoomService.getPersonalLecturePercentBySrId(member,srId)*100);
            model.addAttribute("teamPercent",studyRoomService.getMatesLecturePercentBySrId(studyRoom)*100);
            model.addAttribute("goal",studyRoom.getGoal());

            model.addAttribute("count",count);
            model.addAttribute("srName",studyRoom.getRoomName());
            model.addAttribute("srBoardId",studyRoomBoard.getSrBoardId());
            model.addAttribute("takeLectureHistoryList",takeLectureHistories);

            return "studyRoom/detail";
        }




    }

    @PostMapping("/members/studyRoom/setGoal/{srId}")
    public String doSetGoal(@PathVariable(name="srId") Long srId,@RequestParam(name="period")String period,@RequestParam(name="count")String count){

        Study_Room studyRoom=studyRoomService.findById(srId);



        int lectureNum=count.charAt(0);



        if(period.equals("1day")){
            period="1일";
            studyRoom.setGoalTime(1);
        }
        if(period.equals("1week")){
            period="1주일";
            studyRoom.setGoalTime(7);
        }
        if(period.equals("1month")){
            period="1달";
            studyRoom.setGoalTime(30);
        }

        LocalDateTime now = LocalDateTime.now();

        studyRoom.setGoalSettingTime(now);

        studyRoom.setGoalLectureNo(lectureNum);

        String goal="";

        if(period.equals("1일")||period.equals("1주")||period.equals("1달")){
            
            goal=period+" 동안 총 "+count+"개의 강의 수강";
        }



        studyRoomService.updateGoal(srId,goal);

        return "redirect:/members/studyRoom/"+srId;
    }

    @GetMapping("/members/accept/{srId}")
    public String showAccept(@PathVariable(name="srId") Long srId,Principal principal,Model model){

        Member member=memberService.getMember(principal.getName());

        Study_Room sra=studyRoomService.findById(srId);

        List<ApplyHistoryDTO> applyHistoryDTOList=applyHistoryService.getApplyHistoryDTOListFindById(srId);



        model.addAttribute("historyList",applyHistoryDTOList);
        model.addAttribute("srId",srId);
        model.addAttribute("srName",sra.getRoomName());


        return "member/accept";
    }

    @PostMapping("/members/accept/{srId}/apply/{sraId}")
    public String doAccept(@PathVariable(name="srId") Long srId,@PathVariable(name="sraId") Long sraId,Model model){

        List<ApplyHistoryDTO> applyHistoryDTOList=applyHistoryService.getApplyHistoryDTOListFindById(srId);

        Study_Room sra=studyRoomService.findById(srId);

        for(int i=0;i<applyHistoryDTOList.size();i++){
            if(applyHistoryDTOList.get(i).getSraId()==sraId){


                studyRoomApplyService.doAccept(sraId,true);
                Study_Room studyRoom=studyRoomService.findById(srId);
                studyRoom.setCurrentNum();
                applyHistoryService.deleteApplyHistory(applyHistoryDTOList.get(i).getApplyHistoryId());
                applyHistoryDTOList.remove(i);


            }
        }

        model.addAttribute("historyList",applyHistoryDTOList);
        model.addAttribute("srId",srId);
        model.addAttribute("srName",sra.getRoomName());

        return "member/accept";
    }

    @PostMapping("/members/deny/{srId}/apply/{sraId}")
    public String doDeny(@PathVariable(name="srId") Long srId,@PathVariable(name="sraId") Long sraId,Model model){

        List<ApplyHistoryDTO> applyHistoryDTOList=applyHistoryService.getApplyHistoryDTOListFindById(srId);

        Study_Room sra=studyRoomService.findById(srId);

        for(int i=0;i<applyHistoryDTOList.size();i++){
            if(applyHistoryDTOList.get(i).getSraId()==sraId){
                applyHistoryService.deleteApplyHistory(applyHistoryDTOList.get(i).getApplyHistoryId());
                applyHistoryDTOList.remove(i);


            }
        }

        model.addAttribute("historyList",applyHistoryDTOList);
        model.addAttribute("srId",srId);
        model.addAttribute("srName",sra.getRoomName());

        return "member/accept";

    }










}