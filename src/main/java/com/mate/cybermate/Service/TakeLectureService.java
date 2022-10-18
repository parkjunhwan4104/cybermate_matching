package com.mate.cybermate.Service;

import com.mate.cybermate.Dao.TakeLectureHistoryRepository;
import com.mate.cybermate.domain.Member;
import com.mate.cybermate.domain.TakeLectureHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TakeLectureService {

    private final TakeLectureHistoryRepository takeLectureHistoryRepository;


    public List<TakeLectureHistory> getTakeLectureHistoryByMemberAndSrId(Member member,Long srId){
        List<TakeLectureHistory> allTakeLecture=takeLectureHistoryRepository.findAll();

        List<TakeLectureHistory> memberTakeLectureHistory=new ArrayList<>();

        for(int i=0;i<allTakeLecture.size();i++){
            if(allTakeLecture.get(i).getMember().equals(member)&&(allTakeLecture.get(i).getStudyRoom().getSrId()==srId)){

                memberTakeLectureHistory.add(allTakeLecture.get(i));
            }
        }
        return memberTakeLectureHistory;
    }




}
