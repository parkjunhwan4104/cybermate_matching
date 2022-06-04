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
public class MatchingApply {

    @Id
    @Column(name="applyId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyId;

    private String roomName;

    private Long maxNum;

    private String requirement;

    private String description;

    private String subject;

    private LocalDateTime regDate=LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="boardId")
    private Board board;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "board",fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<MatchingApply> applyList=new ArrayList<>();

    public static MatchingApply createApply(String roomName,Long maxNum,String requirement, String description,String subject){
        MatchingApply matchingApply=new MatchingApply();
        matchingApply.roomName=roomName;
        matchingApply.maxNum=maxNum;
        matchingApply.requirement=requirement;
        matchingApply.description=description;
        matchingApply.subject=subject;


        return matchingApply;
    }

    public void setMember(Member member){
        this.member=member;
        member.setMatchingApply(this);


    }

    public void setBoard(Board board){
        this.board=board;
        board.getApplyList().add(this);
    }








}
