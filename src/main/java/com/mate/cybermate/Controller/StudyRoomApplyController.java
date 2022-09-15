package com.mate.cybermate.Controller;

import com.mate.cybermate.DTO.StudyRoomApply.StudyRoomApplySetLectureForm;
import com.mate.cybermate.Service.MemberService;
import com.mate.cybermate.Service.StudyRoomApplyService;
import com.mate.cybermate.Service.StudyRoomService;
import com.mate.cybermate.domain.AcceptHistory;
import com.mate.cybermate.domain.Member;
import com.mate.cybermate.domain.Study_Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudyRoomApplyController {

    private final StudyRoomService studyRoomService;
    private final MemberService memberService;
    private final StudyRoomApplyService studyRoomApplyService;

    @PostMapping("/boards/1/{srId}")
    public String doApply(@PathVariable(name = "srId") Long srId, Principal principal, Model model) {
        Study_Room makeRoom = studyRoomService.findById(srId);
        Member member = memberService.getMember(principal.getName());


        List<AcceptHistory> list=studyRoomApplyService.getAcceptHistoryBySrId(srId);
        for (int i = 0; i < list.size(); i++) {
            if ((list.get(i).getStudyRoomApply().getStudyRoom().getSrId()== srId) && (list.get(i).getStudyRoomApply().getMember().equals(member))) {
                return "redirect:/boards/1";

            }

        }
        studyRoomApplyService.addStudyRoomApply(srId, member);
        model.addAttribute("studyRoomApplySetLectureForm",new StudyRoomApplySetLectureForm());
        model.addAttribute("srId",srId);
        return  "StudyRoomApply/lectureSet";

    }
}
