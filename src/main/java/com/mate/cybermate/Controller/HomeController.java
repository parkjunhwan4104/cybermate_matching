package com.mate.cybermate.Controller;

import com.mate.cybermate.DTO.ApplyHistory.ApplyHistoryDTO;
import com.mate.cybermate.DTO.ApplyHistory.StudyRoomApplyDTO;
import com.mate.cybermate.DTO.StudyRoom.StudyRoomListDTO;
import com.mate.cybermate.Service.ApplyHistoryService;
import com.mate.cybermate.Service.MemberService;
import com.mate.cybermate.Service.StudyRoomApplyService;
import com.mate.cybermate.Service.StudyRoomService;
import com.mate.cybermate.domain.Member;
import com.mate.cybermate.domain.StudyRoomApply;
import com.mate.cybermate.domain.Study_Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final ApplyHistoryService applyHistoryService;
    private final StudyRoomApplyService studyRoomApplyService;
    private final StudyRoomService studyRoomService;

    @GetMapping("/")
    public String showHome(Model model,Principal principal)  {



        if(principal!=null) {
            Member member=memberService.getMember(principal.getName());

            int belongSrNum=studyRoomService.getSrListByLoginId(member.getLoginId()).size()+studyRoomApplyService.getAcceptApplyListByMemberId(member).size();

            int totalApplyNum=applyHistoryService.getTotalApplyByLoginId(member.getLoginId());

            List<Study_Room> allSrList=studyRoomService.getRoomList();

            List<StudyRoomListDTO> srList=studyRoomService.getSrListByLoginId(member.getLoginId());

            List<StudyRoomApplyDTO> sraList=studyRoomApplyService.getAcceptApplyListByMemberId(member);

            for(int i=0;i< allSrList.size();i++){
                for(int j=0;j<sraList.size();j++){
                    if(allSrList.get(i).getSrId()==sraList.get(j).getSrId()){

                        StudyRoomListDTO RoomListDTO = new StudyRoomListDTO(allSrList.get(i));
                        srList.add(RoomListDTO);


                    }
                }

            }

            /*List<StudyRoomApplyDTO> applyDTOList=studyRoomApplyService.getApplyListByMemberIdAndIsAccept(member);
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
            */








/*
            if(applyDTOList.size()!=0) {
                for (int i = 0; i < applyDTOList.size(); i++) {


                    for (int j = 0; j < list.size(); j++) {


                        if ((applyDTOList.get(i).getSrId()==list.get(j).getSrId())) {

                            applyDTOList.remove(i);

                        }
                    }

                }
            }
*/
            model.addAttribute("belongSrNum",belongSrNum);

            model.addAttribute("totalApplyNum",totalApplyNum);

            model.addAttribute("belongSrList",srList);

            model.addAttribute("nickName",member.getNickName());
        }

        return "index";

    }
}
