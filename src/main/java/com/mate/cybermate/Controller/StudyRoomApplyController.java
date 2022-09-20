package com.mate.cybermate.Controller;

import com.mate.cybermate.Service.MemberService;
import com.mate.cybermate.Service.StudyRoomApplyService;
import com.mate.cybermate.Service.StudyRoomService;
import com.mate.cybermate.domain.Member;
import com.mate.cybermate.domain.Study_Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

}
