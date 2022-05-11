package com.mate.cybermate.Service;


import com.mate.cybermate.Config.Role;
import com.mate.cybermate.DTO.member.MemberLoginForm;
import com.mate.cybermate.DTO.member.MemberSaveForm;
import com.mate.cybermate.Dao.MemberRepository;
import com.mate.cybermate.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByLoginId(username).get();
    }

    public void isExistedMember(String loginId,String nickName){
        if(memberRepository.existsByLoginId(loginId)){
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        }

        if(memberRepository.existsByNickName(nickName)){
            throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
        }

    }

    @Transactional
    public void saveMember(MemberSaveForm memberSaveForm){

        isExistedMember(memberSaveForm.getLoginId(), memberSaveForm.getNickName());

        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

        Member member=Member.createMember(
                memberSaveForm.getLoginId(),
                bCryptPasswordEncoder.encode(memberSaveForm.getLoginPw()),
                memberSaveForm.getNickName(),
                Role.MEMBER

        );

        memberRepository.save(member);


    }

    public Member findByLoginId(String loginId){
        Optional<Member> findMember=memberRepository.findByLoginId(loginId);
        findMember.orElseThrow(
                ()-> new IllegalStateException("존재하지 않는 회원입니다.")
        );

        return findMember.get();

    }

    public boolean isDupleLoginId(String loginId){
        boolean isDuple=memberRepository.existsByLoginId(loginId);

        return isDuple;
    }

    public boolean isDupleNickName(String nickName){
        boolean isDuple=memberRepository.existsByNickName(nickName);
        return isDuple;
    }


}
