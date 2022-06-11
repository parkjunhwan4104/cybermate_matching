package com.mate.cybermate.Service;


import com.mate.cybermate.CybermateApplication;
import com.mate.cybermate.DTO.StudyRoomApply.StudyRoomApplySaveForm;
import com.mate.cybermate.Dao.StudyRoomApplyRepository;
import com.mate.cybermate.Dao.StudyRoomRepository;
import com.mate.cybermate.domain.Board;
import com.mate.cybermate.domain.StudyRoomApply;
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
public class StudyRoomApplyService {
    private final StudyRoomApplyRepository studyRoomApplyRepository;
    private final StudyRoomRepository studyRoomRepository;
    private final StudyRoomService studyRoomService;


    @Transactional
    public void saveRoom(StudyRoomApplySaveForm studyRoomApplySaveForm, Member member, Board board){

        List<Member> members=new ArrayList<>();
        members.add(member);

        StudyRoomApply roomApply = StudyRoomApply.createRoomApplyForOwner(
                studyRoomApplySaveForm.getRoomName(),
                studyRoomApplySaveForm.getMaxNum(),
                studyRoomApplySaveForm.getRequirement(),
                studyRoomApplySaveForm.getDescription(),
                studyRoomApplySaveForm.getSubject()

        );
        roomApply.setMember(member);

        Study_Room studyRoom=Study_Room.createRoom(roomApply);

        studyRoom.setBoard(board);


        roomApply.setStudyRoomAndMember(studyRoom,members);


        studyRoomRepository.save(studyRoom);


        studyRoomApplyRepository.save(roomApply);



    }

    @Transactional
    public void addStudyRoomApply(Long srId,Member member){
        Study_Room studyRoom=studyRoomService.findById(srId);

        StudyRoomApply roomApply = StudyRoomApply.createRoomApply(
                studyRoom.getRoomName(),
                studyRoom.getSubject(),
                member.getAge(),
                member.getSex()


        );

        roomApply.setMember(member);

        studyRoomApplyRepository.save(roomApply);



    }

    public StudyRoomApply findById(Long id){
        List<StudyRoomApply> list=getRoomList();
        StudyRoomApply roomApply=null;

        for(int i=0;i<list.size();i++) {
            if (list.get(i).getSraId()== id){
                roomApply=list.get(i);
            }
        }
        return roomApply;
    }



    public List<StudyRoomApply> getRoomList(){
        List<StudyRoomApply> RoomApplyList= studyRoomApplyRepository.findAll();

        return RoomApplyList;
    }


    /*public List<StudyRoomApplyListDTO> getApplyListByBoardId(Long id, Member member){
        List<StudyRoomApply> roomApplyList=getRoomList();

        List<StudyRoomApplyListDTO> RoomApplyLists =new ArrayList<>();

        for(StudyRoomApply roomApply:roomApplyList){

            if(roomApply.getBoard().getBoardId()==id){
                //System.out.println(room.getMember().getLoginId());
                StudyRoomApplyListDTO MakeRoomListDTO =new StudyRoomApplyListDTO(roomApply,member);

                RoomApplyLists.add(MakeRoomListDTO);
            }
        }
        return RoomApplyLists;

    }

     */





}
