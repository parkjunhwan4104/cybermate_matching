package com.mate.cybermate.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class StudyRoom {

    @Id
    @Column(name="srId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sr_id;

    private String goal;

    private Long goalLectureNo;

    private LocalDateTime regDate=LocalDateTime.now();

    @OneToMany(mappedBy = "studyRoom",fetch = FetchType.LAZY)
    private List<Member> studyRoomMember=new ArrayList<>();


    public void setGoal(String goal){
        this.goal=goal;
    }

    public void setGoalLectureNo(Long goalLectureNo){

        this.goalLectureNo=goalLectureNo;
    }





}
