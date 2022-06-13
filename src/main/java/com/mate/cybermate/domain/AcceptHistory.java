package com.mate.cybermate.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class AcceptHistory {

    @Id
    @Column(name="ahId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ahId;

    private String roomName;


    private String subject;

    private Long age;

    private String sex;

    private Long studyApplyId;


    private LocalDateTime regDate=LocalDateTime.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sraId")
    private StudyRoomApply studyRoomApply;

   public static AcceptHistory createAcceptHistory(String roomName,String subject,Long age,String sex,Long studyApplyId){
       AcceptHistory acceptHistory=new AcceptHistory();
       acceptHistory.roomName=roomName;
       acceptHistory.subject=subject;
       acceptHistory.age=age;
       acceptHistory.sex=sex;
       acceptHistory.studyApplyId=studyApplyId;

       return acceptHistory;

   }

   public void setStudyRoomApply(StudyRoomApply studyRoomApply){
       this.studyRoomApply=studyRoomApply;

   }

}
