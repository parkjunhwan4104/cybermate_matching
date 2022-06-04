package com.mate.cybermate.Service;

import com.mate.cybermate.Dao.MemberRepository;
import com.mate.cybermate.Dao.StudyRoomRepository;
import com.mate.cybermate.domain.Member;
import com.mate.cybermate.domain.StudyRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyRoomService {

    private final StudyRoomRepository studyRoomRepository;



    @Transactional
    public void saveStudyRoom(List<Member> member){

        StudyRoom studyRoom=StudyRoom.createStudyRoom(member);

        studyRoom.setMember(member);

        studyRoomRepository.save(studyRoom);
    }

    @Transactional
    public void updateGoal(Long id,String goal){
        Optional<StudyRoom> srOptional=studyRoomRepository.findById(id);

        srOptional.orElseThrow(
                ()->new IllegalStateException("존재하지 않는 스터디룸입니다.")

        );

        StudyRoom studyRoom=srOptional.get();

        studyRoom.setGoal(goal);
    }





}
