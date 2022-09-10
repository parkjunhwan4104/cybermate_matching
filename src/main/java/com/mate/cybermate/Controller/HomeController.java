package com.mate.cybermate.Controller;

import com.mate.cybermate.DTO.ApplyHistory.ApplyHistoryDTO;
import com.mate.cybermate.DTO.ApplyHistory.StudyRoomApplyDTO;
import com.mate.cybermate.Service.ApplyHistoryService;
import com.mate.cybermate.Service.MemberService;
import com.mate.cybermate.Service.StudyRoomApplyService;
import com.mate.cybermate.domain.Member;
import com.mate.cybermate.domain.StudyRoomApply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final ApplyHistoryService applyHistoryService;
    private final StudyRoomApplyService studyRoomApplyService;
    @GetMapping("/")
    public String showHome(Model model,Principal principal)  {


        List<Long> studyRoomList=new ArrayList<>();

        if(principal!=null) {
            Member member=memberService.getMember(principal.getName());

            List<StudyRoomApplyDTO> applyDTOList=studyRoomApplyService.getApplyListByMemberIdAndIsAccept(member);
            for(int i=0;i<applyDTOList.size();i++){
                if(applyDTOList.get(i).isAccept()==false){
                    applyDTOList.remove(i);
                }
            }
            System.out.println("밑에 갯수"+applyDTOList.size());

            List<ApplyHistoryDTO> applyHistoryDTOList=applyHistoryService.getApplyHistoryAll();

            List<ApplyHistoryDTO> list=new ArrayList<>();

            for(int i=0;i<applyHistoryDTOList.size();i++){
                if(applyHistoryDTOList.get(i).getMemberName().equals(member.getNickName())){

                    list.add(applyHistoryDTOList.get(i));

                }
            }

            /*for(int k=0;k<list.size();k++){

                List<StudyRoomApply> studyRoomApplies=studyRoomApplyService.getListBySrId(list.get(k).getSrId(),member);
                for(int j=0;j<studyRoomApplies.size();j++){
                    studyRoomList.add(studyRoomApplies.get(k).getStudyRoom().getSrId());
            }

*/




            if(applyDTOList.size()!=0) {
                for (int i = 0; i < applyDTOList.size(); i++) {


                    for (int j = 0; j < list.size(); j++) {


                        if ((applyDTOList.get(i).getSrId()==list.get(j).getSrId())) {

                            applyDTOList.remove(i);

                        }
                    }

                }
            }



            model.addAttribute("applyHistoryList",list);

            model.addAttribute("onlyApplyList",applyDTOList);
        }

        return "index";

    }
}
