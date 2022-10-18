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
    public void saveRoom(StudyRoomSaveForm studyRoomSaveForm, Member member,Board board){  //스터디룸 생성
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


        studyRoom.setContentNo(studyRoomSaveForm.getContentNo());

        studyRoom.setMember(member);
        studyRoom.setBoard(board);

        initialLectureNo(studyRoom);

        studyRoomBoardRepository.save(studyRoomBoard);
        studyRoomRepository.save(studyRoom);

    }


    @Transactional
    public void initialLectureNo(Study_Room studyRoom){    //초기 스터디룸 강의 개수 초기화

        studyRoom.setMatesLectureNo(Long.valueOf(0));

    }

    public Long getCurrentLectureNumByMemberAndSrId(Member member,Long srId){  //회원이 속해있는 스터디룸에서 진도 체크한 강의 수

        Study_Room study_room=findById(srId);

        List<TakeLectureHistory> takeLectureHistories=takeLectureService.getTakeLectureHistoryByMemberAndSrId(member,srId);

        Long sum=Long.valueOf(0);

        for(int i=0;i<takeLectureHistories.size();i++){
            sum+=takeLectureHistories.get(i).getLectureNum();
        }

        if(sum>study_room.getContentNo()){
            sum=study_room.getContentNo();
        }

        return sum;
    }

    public Long getCurrentMatesLectureNumBySrId(Study_Room studyRoom){ //회원이 속해있는 스터디룸 메이트들의 진도체크 강의 수수

       List<StudyRoomApply>list=studyRoom.getStudyRoomApply();

        List<Member> memberList=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).isAccept()==true){
                memberList.add(list.get(i).getMember());
            }
        }

        if(!memberList.contains(studyRoom.getMember())){
            memberList.add(studyRoom.getMember());
        }

        Long totalLectureNum=Long.valueOf(0);

        for(int i=0;i<memberList.size();i++){
            totalLectureNum+=getCurrentLectureNumByMemberAndSrId(memberList.get(i),studyRoom.getSrId());
        }

        if(totalLectureNum>studyRoom.getContentNo()*memberList.size()){
            totalLectureNum=studyRoom.getContentNo()*memberList.size();
        }

        return totalLectureNum;
    }


    public float getPersonalLecturePercentBySrId(Member member,Long srId){ //스터디룸의 개인 강의 수강율 퍼센트

        Study_Room studyRoom=findById(srId);

        Long currentNum=getCurrentLectureNumByMemberAndSrId(member,srId);

        Long totalLectureNum=studyRoom.getContentNo();

        if(currentNum>totalLectureNum){
            currentNum=totalLectureNum;
        }

        float lecturePercent=(float)currentNum/(float)totalLectureNum;

        float result=(float)(Math.round(lecturePercent * 100) / 100.0);

        return result;
    }

    public float getMatesLecturePercentBySrId(Study_Room studyRoom){
        Long currentNum=getCurrentMatesLectureNumBySrId(studyRoom);

        List<StudyRoomApply>list=studyRoom.getStudyRoomApply();

        List<Member> memberList=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).isAccept()==true){
                memberList.add(list.get(i).getMember());
            }
        }

        if(!memberList.contains(studyRoom.getMember())){
            memberList.add(studyRoom.getMember());
        }

        Long totalLectureNum=Long.valueOf(0);

        for(int i=0;i<memberList.size();i++){
            totalLectureNum+=studyRoom.getContentNo();
        }

        float matesLecturePercent=(float)currentNum/(float)totalLectureNum;

        float result=(float)(Math.round(matesLecturePercent * 100) / 100.0);

        return result;
    }


    @Transactional
    public void updateLectureNo(Long count,Study_Room studyRoom,Member member){   //스터디룸 강의 개수 업데이트

        Long oldValue=Long.valueOf(0);
        Long newValue=getCurrentLectureNumByMemberAndSrId(member,studyRoom.getSrId())+count;

        Long oldMatesValue=Long.valueOf(0);
        Long newMatesValue=studyRoom.getMatesLectureNo()+count;

        TakeLectureHistory takeLectureHistory=TakeLectureHistory.createTakeLectureHistory(count);

        List<TakeLectureHistory> takeLectureHistories=takeLectureService.getTakeLectureHistoryByMemberAndSrId(member,studyRoom.getSrId());

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
        String formatedNow = now.format(formatter);


        String formatedNow2 = takeLectureHistory.getRegDate().format(formatter);  //추가 하려는 강의 학습 개수

        int toDistinguishSameDay=0;

       if(takeLectureHistories.size()==0){
            takeLectureHistory.setStudyRoom(studyRoom);
            takeLectureHistory.setRegDay(formatedNow);
            takeLectureHistory.setMember(member);
            takeLectureHistoryRepository.save(takeLectureHistory);
        }
        else{
           for(int i=0;i<takeLectureHistories.size();i++){
               String formatNowToDistinguish=takeLectureHistories.get(i).getRegDate().format(formatter);

               if(formatedNow2.equals(formatNowToDistinguish)){
                   toDistinguishSameDay++;
               }
           }

            for(int i=0;i<takeLectureHistories.size();i++){


                String formatedNow3 = takeLectureHistories.get(i).getRegDate().format(formatter); //기존에 기록한 강의 학습 개수


                if(formatedNow2.equals(formatedNow3)){  //같은 날에 이미 학습한 강의 개수를 기록을 했을 경우
                    newValue=oldValue+count;


                    newMatesValue=newMatesValue-takeLectureHistories.get(i).getLectureNum()+count;
                    takeLectureHistories.get(i).setLectureNum(count);
                    break;
                }
                else{

                    if(toDistinguishSameDay>=1){
                        oldValue+=takeLectureHistories.get(i).getLectureNum();
                        continue;
                    }
                    else {
                        takeLectureHistory.setStudyRoom(studyRoom);
                        takeLectureHistory.setRegDay(formatedNow);
                        takeLectureHistory.setMember(member);
                        takeLectureHistoryRepository.save(takeLectureHistory);
                    }



                }
                oldValue+=takeLectureHistories.get(i).getLectureNum();

            }


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
            sum+=studyRoom.getContentNo();
        }

        if(newMatesValue<=sum) {
            studyRoom.setMatesLectureNo(newMatesValue);
        }



    }


    @Transactional
    public void updateGoal(Long id,String goal){   //스터디룸 목표 설정(방장 고유 기능)
        Optional<Study_Room> srOptional=studyRoomRepository.findById(id);

        srOptional.orElseThrow(
                ()->new IllegalStateException("존재하지 않는 스터디룸입니다.")

        );

        Study_Room studyRoom=srOptional.get();

        studyRoom.setGoal(goal);
    }




    public Study_Room findById(Long id){   //스터디룸 고유 아이디를 통한 스터디룸 찾기
        List<Study_Room> list=getRoomList();
        Study_Room makeRoom=null;

        for(int i=0;i<list.size();i++) {
            if (list.get(i).getSrId()== id){
                makeRoom=list.get(i);
            }
        }
        return makeRoom;
    }

    public List<Study_Room> getRoomList(){  // 모든 스터디룸 리스트 리턴
        List<Study_Room> StudyRoomList= studyRoomRepository.findAll();




        return StudyRoomList;
    }

    public List<StudyRoomListDTO> getRoomListByBoardId(Long id) {  //스터디룸 목록 리스트 리턴
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

    public List<StudyRoomListDTO> getSrListByLoginId(String loginId){  // 회원의 로그인 아이디를 통해 해당 회원이 만든 스터디룸 리스트 리턴
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
    
    public List<Study_Room> getPermitAutoMatchingStudyRoomList(){  // 자동 매칭을 허용한 스터디룸 리스트
        List<Study_Room> studyRoomList=getRoomList();

        List<Study_Room> goalList=new ArrayList<>();

        for(int i=0;i<studyRoomList.size();i++){
            if(studyRoomList.get(i).getIsPermitAuto().equals("true")){
                goalList.add(studyRoomList.get(i));
            }
        }

        return goalList;
    }

    public List<Study_Room> getSameSubjectAndSameLectureNoStudyRoomList(StudyRoomApply studyRoomApply){ // 스터디룸 중 자동 매칭 신청에서의 강의명과 목차 개수가 스터디룸의 강의명, 목차개수와 모두 같은 스터디룸 리스트 출력
        List<Study_Room> studyRoomList=getPermitAutoMatchingStudyRoomList();

        List<Study_Room> goalStudyRoomList=new ArrayList<>();
        for(int i=0;i<studyRoomList.size();i++){
            if((studyRoomApply.getSubject().equals(studyRoomList.get(i).getSubject())&&(studyRoomApply.getContentNo()==studyRoomList.get(i).getContentNo()))){
                goalStudyRoomList.add(studyRoomList.get(i));
            }
        }

        return goalStudyRoomList;
    }

    @Transactional
    public void doMatching(List<StudyRoomApply> matchAutoApplyList){ // 회원이 입력한 자동 매칭 신청에 부합하는 스터디룸을 찾아 매칭을 해줌


        Long miniMum=Long.valueOf(0); //가장 적은 회원 수가 포함된 스터디룸의 회원 수

        if(matchAutoApplyList.size()!=0) {

            for (int i = 0; i < matchAutoApplyList.size(); i++) {
                List<Study_Room> studyRoomList = getSameSubjectAndSameLectureNoStudyRoomList(matchAutoApplyList.get(i));
                if (studyRoomList.size() != 0) {
                    Study_Room target = new Study_Room(); //회원이 매칭될 스터디룸

                    for (int j = 0; j < studyRoomList.size(); j++) {


                        if (j == 0) {
                            miniMum = studyRoomList.get(j).getCurrentNum();
                            target = studyRoomList.get(j);
                        } else {
                            if (studyRoomList.get(j).getCurrentNum() < miniMum) {
                                miniMum = studyRoomList.get(j).getCurrentNum();
                                target = studyRoomList.get(j);
                            }

                            if (studyRoomList.get(j).getCurrentNum() == miniMum) {
                                if (target.getSrId() > studyRoomList.get(j).getSrId()) {
                                    target = studyRoomList.get(j);
                                }
                            }

                        }

                    }
                    if(!(matchAutoApplyList.get(i).getMember().getNickName().equals(target.getMember().getNickName()))){
                        matchAutoApplyList.get(i).setStudyRoom(target);
                        matchAutoApplyList.get(i).setRoomName(target.getRoomName());
                        matchAutoApplyList.get(i).setAccept(true);
                        target.setCurrentNum();

                        matchAutoApplyList.get(i).setIsAuto(false);
                    }

                }
            }
        }
    }




}