package com.mate.cybermate;

import com.mate.cybermate.domain.Member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CybermateApplication {

	public static List<Member> memberList=new ArrayList<>();

	public static void main(String[] args) {
		SpringApplication.run(CybermateApplication.class, args);
	}

}
