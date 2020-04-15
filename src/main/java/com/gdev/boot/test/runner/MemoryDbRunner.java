package com.gdev.boot.test.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gdev.boot.domain.member.MemberRepository;

@Component
public class MemoryDbRunner implements ApplicationRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		//memberRepository.save(MemberVo.builder().username("jhb").password(passwordEncoder.encode("1234")).role("USER").build());
		//memberRepository.save(MemberVo.builder().userId("jhb").password(passwordEncoder.encode("1234")).role("USER").build());
	}

}
