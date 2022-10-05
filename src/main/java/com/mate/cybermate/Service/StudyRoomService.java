package com.mate.cybermate.Service;

import com.mate.cybermate.DTO.StudyRoom.StudyRoomListDTO;
import com.mate.cybermate.DTO.StudyRoom.StudyRoomSaveForm;
import com.mate.cybermate.Dao.StudyRoomBoardRepository;
import com.mate.cybermate.Dao.StudyRoomRepository;
import com.mate.cybermate.Dao.TakeLectureHistoryRepository;
import com.mate.cybermate.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;
    private final StudyRoomBoardRepository studyRoomBoardRepository;
    private final TakeLectureHistoryRepository takeLectureHistoryRepository;
    private final TakeLectureService takeLectureService;

    @Transactional
    public void saveRoom(StudyRoomSaveForm studyRoomSaveForm, Member member,Board board){
        Study_Room studyRoom=Study_Room.createRoom(
                studyRoomSaveForm.getRoomName(),
                studyRoomSaveForm.getMaxNum(),
                studyRoomSaveForm.getSubject(),
                studyRoomSaveForm.getDescription(),
                studyRoomSaveForm.getRequirement(),
                studyRoomSaveForm.getContentNo(),
                studyRoomSaveForm.getIsPermitAuto()

        );

        StudyRoomBoard studyRoomBoard=StudyRoomBoard.createBoard(studyRoomSaveForm.getRoomName());

        studyRoomBoard.setStudyRoom(studyRoom);

        studyRoom.setSrBoard(studyRoomBoard);

        member.setLectureNo(studyRoomSaveForm.getContentNo());
        member.setCurrentLectureNo(Long.valueOf(0));
        member.setLecturePercent(0);

        studyRoom.setMember(member);
        studyRoom.setBoard(board);

        initialLectureNo(studyRoom);

        studyRoomBoardRepository.save(studyRoomBoard);
        studyRoomRepository.save(studyRoom);

    }


    @Transactional
    public void initialLectureNo(Study_Room studyRoom){

        studyRoom.setMatesLectureNo(Long.valueOf(0));

    }

    @Transactional
    public void updateLectureNo(Long count,Study_Room studyRoom,Member member){

        Long oldValue=Long.valueOf(0);
        Long newValue= member.getCurrentLectureNo()+count;

        Long oldMatesValue=Long.valueOf(0);
        Long newMatesValue=studyRoom.getMatesLectureNo()+count;

        TakeLectureHistory takeLectureHistory=TakeLectureHistory.createTakeLectureHistory(count);

        List<TakeLectureHistory> takeLectureHistories=takeLectureService.getTakeLectureHistoryByMember(member);

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
        String formatedNow = now.format(formatter);


        String formatedNow2 = takeLectureHistory.getRegDate().format(formatter);

        if(takeLectureHistories.size()==0){
            takeLectureHistory.setStudyRoom(studyRoom);
            takeLectureHistory.setRegDay(formatedNow);
            takeLectureHistory.setMember(member);
            takeLectureHistoryRepository.save(takeLectureHistory);
        }
        else{

            for(int i=0;i<takeLectureHistories.size();i++){


                String formatedNow3 = takeLectureHistories.get(i).getRegDate().format(formatter);

                if(formatedNow2.equals(formatedNow3)){
                    newValue=oldValue+count;
                    newMatesValue=newMatesValue-takeLectureHistories.get(i).getLectureNum()+count;
                    takeLectureHistories.get(i).setLectureNum(count);
                    break;
                }
                else{
                    takeLectureHistory.setStudyRoom(studyRoom);
                    takeLectureHistory.setRegDay(formatedNow);
                    takeLectureHistory.setMember(member);
                    takeLectureHistoryRepository.save(takeLectureHistory);




                }
                oldValue+=takeLectureHistories.get(i).getLectureNum();

            }


        }


        if(newValue<=member.getLectureNo()) {
            member.setCurrentLectureNo(newValue);
        }


        List<StudyRoomApply>list=studyRoom.getStudyRoomApply();
        List<Member> memberList=new ArrayList<>();
        for(int j=0;j<list.size();j++){
            if(list.get(j).isAccept()==true){
                memberList.add(list.get(j).getMember());
            }
        }
        Long sum=Long.valueOf(0);
        for(int j=0;j<memberList.size();j++){
            sum+=memberList.get(j).getLectureNo();
        }

        if(newMatesValue<=sum) {
            studyRoom.setMatesLectureNo(newMatesValue);
        }







    }

    @Transactional
    public void updateStudyRoomPercent(Study_Room studyRoom,Member member){

        List<StudyRoomApply>list=studyRoom.getStudyRoomApply();

        float matesTotalNo=0;


        List<Member> memberList=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).isAccept()==true){
                memberList.add(list.get(i).getMember());
            }
        }

        if(!memberList.contains(studyRoom.getMember())){
            memberList.add(studyRoom.getMember());
        }

        float CLN=(float)member.getCurrentLectureNo();
        float CN=(float)member.getLectureNo();
        float MLN=0;


        for(int i=0;i<memberList.size();i++){
            MLN+=(float)memberList.get(i).getLectureNo();

        }


        float lecturePercent=CLN/CN;




        float a=(float)(Math.round(lecturePercent * 100) / 100.0);



        member.setLecturePercent(a);

        for(int i=0;i<memberList.size();i++){
            matesTotalNo+= memberList.get(i).getCurrentLectureNo();
        }

        float matesPercent=matesTotalNo/MLN;

        float b=(float)(Math.round(matesPercent * 100) / 100.0);


        studyRoom.setMatesPercent(b);
    }


    @Transactional
    public void updateGoal(Long id,String goal){
        Optional<Study_Room> srOptional=studyRoomRepository.findById(id);

        srOptional.orElseThrow(
                ()->new IllegalStateException("존재하지 않는 스터디룸입니다.")

        );

        Study_Room studyRoom=srOptional.get();

        studyRoom.setGoal(goal);
    }




    public Study_Room findById(Long id){
        List<Study_Room> list=getRoomList();
        Study_Room makeRoom=null;

        for(int i=0;i<list.size();i++) {
            if (list.get(i).getSrId()== id){
                makeRoom=list.get(i);
            }
        }
        return makeRoom;
    }

    public List<Study_Room> getRoomList(){
        List<Study_Room> StudyRoomList= studyRoomRepository.findAll();




        return StudyRoomList;
    }

    public List<StudyRoomListDTO> getRoomListByBoardId(Long id) {
        List<Study_Room> roomList = getRoomList();


        //System.out.println(roomList.get(0).getTotal().size());
        List<StudyRoomListDTO> RoomLists = new ArrayList<>();

        for (Study_Room room : roomList) {


            if (room.getBoard().getBoardId() == id) {

                StudyRoomListDTO RoomListDTO = new StudyRoomListDTO(room);

                RoomLists.add(RoomListDTO);

            }
        }
        return RoomLists;

    }

    public List<StudyRoomListDTO> getSrListByLoginId(String loginId){
        List<Study_Room> srList=studyRoomRepository.findAll();

        List<StudyRoomListDTO> tempList=new ArrayList<>();

        for(int i=0;i<srList.size();i++){
            if(srList.get(i).getMember().getLoginId().equals(loginId)){

                StudyRoomListDTO RoomListDTO = new StudyRoomListDTO(srList.get(i));
                tempList.add(RoomListDTO);
            }
        }

        return tempList;
    }



}