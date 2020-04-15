package com.gdev.boot.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gdev.boot.domain.member.MemberRepository;
import com.gdev.boot.domain.member.MemberVo;

@Service
public class MemberService implements UserDetailsService{

	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<MemberVo> opMember = memberRepository.finduUserById("MemberRepository.finduUserById", username);
		opMember.orElseThrow(() -> new UsernameNotFoundException(username+"의 사용자를 찾을 수 없습니다."));
		MemberVo mv = opMember.get();
		mv.setAuthorities();
		return mv;
	}

}
