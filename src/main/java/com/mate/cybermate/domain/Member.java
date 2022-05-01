package com.mate.cybermate.domain;


import com.mate.cybermate.Config.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Enumerated(EnumType.STRING)
    private Role authority;

    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private Long mateId;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private MatchingApply matchingApply;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="srId")
    private StudyRoom studyRoom;

    public static Member createMember(String loginId,String loginPw,String nickName,Role authority){
        Member member=new Member();
        member.loginId=loginId;
        member.loginPw=loginPw;
        member.nickName=nickName;
        member.authority=authority;

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
        return nickName;
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

    public void setApply(MatchingApply apply){
            this.matchingApply=apply;

    }

    public void setStudyRoom(StudyRoom studyRoom){
        this.studyRoom=studyRoom;
    }
}
