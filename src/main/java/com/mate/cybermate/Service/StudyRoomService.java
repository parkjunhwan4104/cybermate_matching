package com.mate.cybermate.Service;

import com.mate.cybermate.DTO.StudyRoom.StudyRoomListDTO;
import com.mate.cybermate.Dao.StudyRoomRepository;
import com.mate.cybermate.domain.Member;
import com.mate.cybermate.domain.StudyRoomApply;
import com.mate.cybermate.domain.Study_Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;





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


        List<StudyRoomListDTO> RoomLists = new ArrayList<>();

        for (Study_Room room : roomList) {

            if (room.getBoard().getBoardId() == id) {

                StudyRoomListDTO RoomListDTO = new StudyRoomListDTO(room);

                RoomLists.add(RoomListDTO);

            }
        }
        return RoomLists;

    }



}
