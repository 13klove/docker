package com.gdev.boot.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.gdev.boot.domain.member.MemberVo;
import com.google.common.collect.Sets;

public class Holder extends GenericFilterBean{

	private static MemberVo memberVo;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(SecurityContextHolder.getContext().getAuthentication() != null && !SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
			Object userDetail = (Object) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			memberVo = (MemberVo) userDetail;
		}
		chain.doFilter(request, response);
	}
	
	public static MemberVo getMemberVo() {
		if(memberVo == null) {
			memberVo = MemberVo.builder().authorities(Sets.newHashSet(new SimpleGrantedAuthority(RoleAttribute.ANONYMOUS.toString()))).build();
		}
		return memberVo;
	}

}

