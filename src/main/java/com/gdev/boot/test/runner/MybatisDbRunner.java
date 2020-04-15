package com.gdev.boot.test.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gdev.boot.domain.member.MemberRepository;

@Component
public class MybatisDbRunner implements ApplicationRunner{

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		//memberRepository.insert("MemberRepository.insert", MemberVo.builder().id("jhb").password(passwordEncoder.encode("1234")).roleGroup(RoleGroup.GROUP_DEV).build());
	}

}
