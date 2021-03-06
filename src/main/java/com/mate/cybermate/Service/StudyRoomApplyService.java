package com.mate.cybermate.Service;


import com.mate.cybermate.CybermateApplication;
import com.mate.cybermate.DTO.ApplyHistory.ApplyHistoryDTO;
import com.mate.cybermate.DTO.ApplyHistory.StudyRoomApplyDTO;
import com.mate.cybermate.DTO.StudyRoomApply.StudyRoomApplySaveForm;
import com.mate.cybermate.Dao.AcceptHistoryRepository;
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
    private final StudyRoomRepository studyRoomRepository;
    private final StudyRoomService studyRoomService;
    private final ApplyHistoryRepository applyHistoryRepository;
    private final ApplyHistoryService applyHistoryService;
    private final AcceptHistoryRepository acceptHistoryRepository;


    @Transactional
    public void saveRoom(StudyRoomApplySaveForm studyRoomApplySaveForm, Member member, Board board){

        List<Member> members=new ArrayList<>();
        members.add(member);

        StudyRoomApply roomApply = StudyRoomApply.createRoomApplyForOwner(
                studyRoomApplySaveForm.getRoomName(),
                studyRoomApplySaveForm.getMaxNum(),
                studyRoomApplySaveForm.getRequirement(),
                studyRoomApplySaveForm.getDescription(),
                studyRoomApplySaveForm.getSubject(),
                studyRoomApplySaveForm.getContentNo()


        );

        roomApply.setSex(member.getSex());
        roomApply.setAge(member.getAge());
        ApplyHistory applyHistory=ApplyHistory.createApplyHistory(
                roomApply.getRoomName(),
                member.getNickName(),
                roomApply.getSubject(),
                member.getAge(),
                member.getSex()
        );

        roomApply.setMember(member);

        Study_Room studyRoom=Study_Room.createRoom(roomApply);
        studyRoom.setBoard(board);

        roomApply.setStudyRoom(studyRoom);
        roomApply.setAccept(true);

        applyHistory.setHistoryApply(roomApply);
        applyHistory.setHistoryMember(member);
        applyHistory.setHistoryStudyRoom(studyRoom);



        applyHistoryRepository.save(applyHistory);

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
                member.getSex(),
                studyRoom.getContentNo()


        );
        roomApply.setAccept(false);

        roomApply.setMember(member);

        roomApply.setStudyRoom(studyRoom);



        studyRoomApplyRepository.save(roomApply);

        AcceptHistory acceptHistory=AcceptHistory.createAcceptHistory(
                roomApply.getRoomName(),
                roomApply.getSubject(),
                roomApply.getAge(),
                roomApply.getSex(),
                roomApply.getSraId()
        );


        acceptHistory.setStudyRoomApply(roomApply);
        acceptHistoryRepository.save(acceptHistory);


    }


    @Transactional
    public void setAccept(Long sraId,boolean accept){
        StudyRoomApply studyRoomApply=findById(sraId);
        studyRoomApply.setAccept(accept);

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


    public List<StudyRoomApplyDTO> getApplyListByMemberId(Member member){
        List<StudyRoomApply> roomApplyList=getRoomList();

        List<StudyRoomApplyDTO> RoomApplyLists =new ArrayList<>();

        for(StudyRoomApply roomApply:roomApplyList){

            if(roomApply.getMember().getMemberId()==member.getMemberId()){
                //System.out.println(room.getMember().getLoginId());
                StudyRoomApplyDTO RoomApplyListDTO =new StudyRoomApplyDTO(roomApply,member);

                RoomApplyLists.add(RoomApplyListDTO);
            }
        }
        return RoomApplyLists;

    }


    public List<StudyRoomApplyDTO> getApplyListByMemberIdAndIsAccept(Member member){
        List<StudyRoomApply> roomApplyList=getRoomList();

        for(int i=0;i<roomApplyList.size();i++){
            if(roomApplyList.get(i).isAccept()==false){
                roomApplyList.remove(i);
            }
        }

        List<StudyRoomApplyDTO> RoomApplyLists =new ArrayList<>();


        for(StudyRoomApply roomApply:roomApplyList){

            if(roomApply.getMember().getMemberId()==member.getMemberId()){
                //System.out.println(room.getMember().getLoginId());
                StudyRoomApplyDTO RoomApplyListDTO =new StudyRoomApplyDTO(roomApply,member);

                RoomApplyLists.add(RoomApplyListDTO);
            }
        }
        return RoomApplyLists;

    }

    public List<StudyRoomApply> getListBySrId(Long srId,Member member){
        List<StudyRoomApply> roomApplyList=getRoomList();

        List<StudyRoomApply> list=new ArrayList<>();

        for(int i=0;i<roomApplyList.size();i++){
            if(roomApplyList.get(i).getStudyRoom().getSrId()==srId){
                if(roomApplyList.get(i).getMember().equals(member)){
                    list.add(roomApplyList.get(i));
                }
            }
        }
        return list;
    }

    public List<StudyRoomApplyDTO> getApplyListBySrId(Long srId,Member member){
        List<ApplyHistoryDTO> applyHistoryDTOList=applyHistoryService.getApplyHistoryAll();
        List<ApplyHistoryDTO> list=new ArrayList<>();

        for(int i=0;i<applyHistoryDTOList.size();i++){
            if(applyHistoryDTOList.get(i).getMemberName().equals(member.getNickName())){

                list.add(applyHistoryDTOList.get(i));
            }
        }

        List<StudyRoomApply> roomApplyList=getRoomList();

        List<StudyRoomApplyDTO> RoomApplyLists =new ArrayList<>();

        for(StudyRoomApply roomApply:roomApplyList){

            if(roomApply.getStudyRoom().getSrId()==srId){
                //System.out.println(room.getMember().getLoginId());
                StudyRoomApplyDTO RoomApplyListDTO =new StudyRoomApplyDTO(roomApply,member);

                RoomApplyLists.add(RoomApplyListDTO);
            }
        }

        for(int i=0;i<RoomApplyLists.size();i++){
            for(int j=0;j<applyHistoryDTOList.size();j++){
                if(applyHistoryDTOList.get(j).getSrId()==srId){
                    if((applyHistoryDTOList.get(j).getMemberName().equals(RoomApplyLists.get(i).getMemberName())||(RoomApplyLists.get(i).isAccept()==false))){
                        RoomApplyLists.remove(i);
                    }

                }
            }
        }

        return RoomApplyLists;



    }

    @Transactional
    public void deleteApply(Long sraId){

        StudyRoomApply studyRoomApply=findById(sraId);

        studyRoomApplyRepository.delete(studyRoomApply);

    }

    public List<AcceptHistory> getAcceptHistory(){
        List<AcceptHistory> acceptHistories= acceptHistoryRepository.findAll();

        return acceptHistories;

    }

    public List<AcceptHistory> getAcceptHistoryBySrId(Long srId){
        List<AcceptHistory> acceptHistories= acceptHistoryRepository.findAll();

        List<AcceptHistory> acceptHistoryList=new ArrayList<>();

        for(int i=0;i<acceptHistories.size();i++){
            if(acceptHistories.get(i).getStudyRoomApply().getStudyRoom().getSrId()==srId){
                acceptHistoryList.add(acceptHistories.get(i));
            }
        }

        return acceptHistoryList;

    }

    public AcceptHistory findHistoryById(Long id){
        List<AcceptHistory> list=getAcceptHistory();
        AcceptHistory history=null;

        for(int i=0;i<list.size();i++) {
            if (list.get(i).getAhId()== id){
                history=list.get(i);
            }
        }
        return history;
    }

    @Transactional
    public void deleteAcceptHistory(Long id){
        AcceptHistory history=findHistoryById(id);
        acceptHistoryRepository.delete(history);
    }










}
