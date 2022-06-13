package com.mate.cybermate.Service;

import com.mate.cybermate.DTO.ApplyHistory.ApplyHistoryDTO;
import com.mate.cybermate.DTO.StudyRoom.StudyRoomListDTO;
import com.mate.cybermate.Dao.ApplyHistoryRepository;
import com.mate.cybermate.domain.ApplyHistory;
import com.mate.cybermate.domain.Study_Room;
import com.mate.cybermate.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplyHistoryService {

    private final ApplyHistoryRepository applyHistoryRepository;

    public List<ApplyHistory> getApplyHistory(){
        List<ApplyHistory> StudyRoomList= applyHistoryRepository.findAll();


        return StudyRoomList;
    }
    public List<ApplyHistoryDTO> getApplyHistoryAll() {
        List<ApplyHistory> histories = getApplyHistory();


        //System.out.println(roomList.get(0).getTotal().size());
        List<ApplyHistoryDTO> historyList = new ArrayList<>();

        for (ApplyHistory history : histories) {




            ApplyHistoryDTO historyListDTO = new ApplyHistoryDTO(history);

            historyList.add(historyListDTO);


        }
        return historyList;

    }

    public ApplyHistory getApplyHistoryFindById(Long srId){
        List<ApplyHistory> StudyRoomList=getApplyHistory();

        ApplyHistory applyHistory=null;

        for(int i=0;i<StudyRoomList.size();i++){
            if(StudyRoomList.get(i).getStudyRoom().getSrId()==srId){
                applyHistory=StudyRoomList.get(i);
            }
        }
        return applyHistory;
    }



}
