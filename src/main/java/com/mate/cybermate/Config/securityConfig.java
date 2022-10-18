package com.mate.cybermate.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class securityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {  //시큐리티가 관리하는 파일들을 정함
        web.ignoring().mvcMatchers("/css/**", "/js/**", "/img/**", "/error/**", "/lib/**"); //시큐리티가 우리가 가지고 있는 파일들을 모두 관리해줌
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {



        http

                .authorizeRequests(authorize ->authorize
                        .mvcMatchers("/members/join","/members/login","/members/check/**").anonymous() //URL이 간소화될수있도록함,  회원가입하는 url, 어나니머스는 로그인이 되지 않은 사람도 해당 페이지를 들어갈수 있도록함
                        .mvcMatchers("/members/makeRoom","/boards/1","/members/studyRoom/**","/members/studyRoom/setGoal/**","/members/myPage","/boards/1/**","/members/accept/**","/members/deny/**","/studyRoom/**","/articles/**","/board/**").authenticated()
                        .mvcMatchers("/","/members/loginFail","/members/loginSucess").permitAll()

                        .anyRequest()
                        .denyAll() //위의 3개 페이지말고는 모두 다 거절해라
                )

                .formLogin()
                .loginPage("/members/login")
                .loginProcessingUrl("/members/doLogin")   //로그인이 이루어지는 페이지
                .usernameParameter("loginId")
                .passwordParameter("loginPw")
                .failureUrl("/members/login")
                .defaultSuccessUrl("/")         //로그인 성공후에 인덱스 페이지로 보내줌
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)  //권한 정보를 삭제(true하면)
                .and()
                .sessionManagement()
                .invalidSessionUrl("/") //유효하지 않은 세션이면 인덱스 페이지로 넘어감
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)  //동시접속을 차단해줌
                .expiredUrl("/");  //세션이 만료시에 이동할 url을 정해줌


    }

    @Bean //메소드에서 선언이되어 개발자들이 수동으로 등록해주는거
    public PasswordEncoder passwordEncoder(){  //비밀번호를 암호화시키는거
        return new BCryptPasswordEncoder();
    }

}