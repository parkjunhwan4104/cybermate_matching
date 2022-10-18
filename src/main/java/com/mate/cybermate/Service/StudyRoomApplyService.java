package com.mate.cybermate.Service;



import com.mate.cybermate.DTO.ApplyHistory.ApplyHistoryDTO;
import com.mate.cybermate.DTO.ApplyHistory.StudyRoomApplyDTO;
import com.mate.cybermate.DTO.StudyRoomApply.AutoMatchingApplySaveForm;
import com.mate.cybermate.Dao.ApplyHistoryRepository;
import com.mate.cybermate.Dao.StudyRoomApplyRepository;
import com.mate.cybermate.Dao.StudyRoomRepository;
import com.mate.cybermate.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyRoomApplyService {
    private final StudyRoomApplyRepository studyRoomApplyRepository;

    private final StudyRoomService studyRoomService;
    private final ApplyHistoryRepository applyHistoryRepository;





    @Transactional
    public void addStudyRoomApply(Long srId,Member member){
        Study_Room studyRoom=studyRoomService.findById(srId);

        StudyRoomApply roomApply = StudyRoomApply.createRoomApply(
                studyRoom.getRoomName(),
                studyRoom.getSubject(),
                member.getAge(),
                member.getSex(),
                studyRoom.getContentNo()


        );


        roomApply.setAccept(false);
        roomApply.setIsAuto(false);
        roomApply.setMember(member);

        roomApply.setStudyRoom(studyRoom);

        studyRoomApplyRepository.save(roomApply);

        ApplyHistory applyHistory=ApplyHistory.createApplyHistory(
                roomApply.getRoomName(),
                member.getNickName(),
                roomApply.getSubject(),
                String.valueOf(roomApply.getAge()),
                roomApply.getSex()
        );

        applyHistory.setHistoryStudyRoom(studyRoom);
        applyHistory.setHistoryMember(member);
        applyHistory.setHistoryApply(roomApply);
        applyHistoryRepository.save(applyHistory);


    }

    @Transactional
    public void addStudyRoomAutoMatchingApply(Member member, AutoMatchingApplySaveForm autoMatchingApplySaveForm){


        StudyRoomApply roomApply = StudyRoomApply.crateAutoMatchingApply(

                autoMatchingApplySaveForm.getSubject(),
                member.getAge(),
                member.getSex(),
                autoMatchingApplySaveForm.getLectureNo(),
                autoMatchingApplySaveForm.getIntroduce(),
                true


        );
        roomApply.setAccept(false);

        roomApply.setMember(member);


        studyRoomApplyRepository.save(roomApply);
    }


    @Transactional
    public void doAccept(Long sraId,boolean accept){
        StudyRoomApply studyRoomApply=findById(sraId);

        studyRoomApply.setAccept(accept);

    }

    public StudyRoomApply findById(Long id){
        List<StudyRoomApply> list=getRoomApplyList();
        StudyRoomApply roomApply=null;

        for(int i=0;i<list.size();i++) {
            if (list.get(i).getSraId()== id){
                roomApply=list.get(i);
            }
        }
        return roomApply;
    }



    public List<StudyRoomApply> getRoomApplyList(){
        List<StudyRoomApply> RoomApplyList= studyRoomApplyRepository.findAll();

        return RoomApplyList;
    }



    public List<StudyRoomApplyDTO> getAcceptApplyListByMemberId(Member member){
        List<StudyRoomApply> roomApplyList=getRoomApplyList();

        List<StudyRoomApplyDTO> RoomApplyLists =new ArrayList<>();

        for(StudyRoomApply roomApply:roomApplyList){

            if(roomApply.isAccept()==true) {
                if (roomApply.getMember().getMemberId() == member.getMemberId()) {
                    //System.out.println(room.getMember().getLoginId());
                    StudyRoomApplyDTO RoomApplyListDTO =new StudyRoomApplyDTO(roomApply,member);

                    RoomApplyLists.add(RoomApplyListDTO);
                }
            }
        }
        return RoomApplyLists;

    }

    public List<StudyRoomApply> matchAutoApplyList(){ // 자동 매칭 신청을 완료한 신청 리스트
        List<StudyRoomApply> list=getRoomApplyList();

        List<StudyRoomApply> matchAutoList=new ArrayList<>();

        for(int i=0;i<list.size();i++){

            if(list.get(i).isAuto()==true){
                matchAutoList.add(list.get(i));
            }
        }

        return matchAutoList;
    }





















}