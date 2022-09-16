package com.mate.cybermate.domain;


import com.mate.cybermate.Config.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Member implements UserDetails {

    @Id
    @Column(name="memberId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String loginId;

    private String loginPw;

    private String nickName;

    private LocalDateTime regDate=LocalDateTime.now();

    private String sex;

    private Long age;

    private String favorite;

    private String introduce;

    private Long lectureNo;

    private Long currentLectureNo;
    private float lecturePercent;

    @Enumerated(EnumType.STRING)
    private Role authority;


    @OneToMany(fetch=FetchType.LAZY)
    private List<ApplyHistory> applyList=new ArrayList<>();

    @OneToMany(mappedBy = "member",fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<StudyRoomApply> studyRoomApplies=new ArrayList<>();

    @OneToMany(mappedBy = "member",fetch=FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Study_Room> studyRoomList=new ArrayList<>();




    public static Member createMember(String loginId,String loginPw,String nickName,Role authority,String sex,Long age,String favorite){
        Member member=new Member();
        member.loginId=loginId;
        member.loginPw=loginPw;
        member.nickName=nickName;
        member.authority=authority;
        member.sex=sex;
        member.age=age;
        member.favorite=favorite;

        return member;
    }

    private boolean isAccountNonExpired= true;
    private boolean isAccountNonLocked=true;
    private boolean isCredentialsNonExpired=true;
    private boolean isEnabled=true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.authority.getValue()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return loginPw;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setAuthority(Role authority){
        this.authority=authority;
    }


    public void setIntroduce(String introduce){
        this.introduce=introduce;
    }

    public void setFavorite(String favorite){

        this.favorite=favorite;
    }

    public void setLectureNo(Long no){
        this.lectureNo=no;
    }

    public void setCurrentLectureNo(Long currentLectureNo){


        this.currentLectureNo=currentLectureNo;

    }

    public void setLecturePercent(float no){
        this.lecturePercent=no;
    }


}
