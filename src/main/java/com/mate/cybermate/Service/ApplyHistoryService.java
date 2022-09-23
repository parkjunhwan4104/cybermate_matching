package com.mate.cybermate.Service;

import com.mate.cybermate.DTO.ApplyHistory.ApplyHistoryDTO;
import com.mate.cybermate.DTO.StudyRoom.StudyRoomListDTO;
import com.mate.cybermate.DTO.member.MemberMyPageForm;
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

    public int getTotalApplyByLoginId(String loginId){
        List<ApplyHistory> applyHistoryList=getApplyHistory();

        int count=0;

        for(int i=0;i<applyHistoryList.size();i++){
            if(applyHistoryList.get(i).getStudyRoomApply().isAccept()==false){
                if(applyHistoryList.get(i).getStudyRoom().getMember().getLoginId().equals(loginId)){
                    count++;
                }
            }
        }
        return count;
    }

    public List<ApplyHistoryDTO> getApplyHistoryDTOListFindById(Long srId){
        List<ApplyHistoryDTO> StudyRoomList=getApplyHistoryAll();

        List<ApplyHistoryDTO> applyHistoryList=new ArrayList<>();

        for(int i=0;i<StudyRoomList.size();i++){
            if(StudyRoomList.get(i).getSrId()==srId){
                applyHistoryList.add(StudyRoomList.get(i));
            }
        }

        return applyHistoryList;
    }

    public ApplyHistory getApplyHistoryFindById(Long Id){
        List<ApplyHistory> StudyRoomList=getApplyHistory();

        ApplyHistory applyHistory=null;

        for(int i=0;i<StudyRoomList.size();i++){
            if(StudyRoomList.get(i).getApplyHistoryId()==Id){
                applyHistory=StudyRoomList.get(i);
            }
        }
        return applyHistory;
    }

    @Transactional
    public void deleteApplyHistory(Long applyHistoryId){

        ApplyHistory applyHistory=getApplyHistoryFindById(applyHistoryId);

        applyHistoryRepository.delete(applyHistory);

    }


    @Transactional
    public void setPermitMyInformation(MemberMyPageForm memberMyPageForm,Member member, String sexPermit, String agePermit){

        List<ApplyHistory> applyHistories=member.getApplyList();


        for(int i=0; i<applyHistories.size();i++){

                if(sexPermit.equals("No")){
                    applyHistories.get(i).setSex("비공개");
                }

                if(sexPermit.equals("Yes")){
                    applyHistories.get(i).setSex(memberMyPageForm.getSex());
                }

                if(agePermit.equals("No")){
                    applyHistories.get(i).setAge("비공개");
                }
                if(agePermit.equals("Yes")){
                    applyHistories.get(i).setAge(String.valueOf(memberMyPageForm.getAge()));
                }


        }


    }

}
