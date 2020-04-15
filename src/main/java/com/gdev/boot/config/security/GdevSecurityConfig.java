package com.gdev.boot.config.security;


import java.util.Arrays;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//https://coding-start.tistory.com/153
@Configuration
public class GdevSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	public void configure(WebSecurity web) throws Exception {
		//path에서 static resource에 접근 하는 것은 시큐리티를 안거친다.
		//여기서 세팅을 해주면 시큐리티 필터를 타지 않는다.
		//아래 configure에서도 세팅이 가능하지만 아래에서 하면 필터를 타기는 한다.
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
		web.ignoring().requestMatchers(new AntPathRequestMatcher("/vendor/**"));
		web.ignoring().requestMatchers(new AntPathRequestMatcher("/scss/**"));
		web.ignoring().requestMatchers(new AntPathRequestMatcher("/webjars/**"));
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		setMappingUrl(http.authorizeRequests());
		setFormLogin(http.formLogin());
		setLogout(http.logout());
		setException(http.exceptionHandling());
		http.addFilterAfter(getHolder(), FilterSecurityInterceptor.class);
		
		//securityContext 공유 범위 기본은 ThreadLocal
		//아래 설정은 메인 스레드에서 생성된 자식 스레드까지 가능
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}
	
	public void setMappingUrl(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry menuResource) {
		Arrays.stream(Menu.values()).forEach(a -> {
			if(a.getRole().length > 1) {
				menuResource.mvcMatchers(a.getUrl()+"/**").hasAnyAuthority(a.getMappingRole());
			}else {
				RoleAttribute roleAttribute = a.getRole()[0];
				if(roleAttribute.equals(RoleAttribute.ALL)) {
					menuResource.mvcMatchers(a.getUrl()+"/**").permitAll();
				}
				else if(roleAttribute.equals(RoleAttribute.ANONYMOUS)) {
					menuResource.mvcMatchers(a.getUrl()+"/**").anonymous();
				}
				else {
					menuResource.mvcMatchers(a.getUrl()+"/**").hasAnyAuthority(roleAttribute.toString());
				}
			}
		});
		menuResource.anyRequest().authenticated();
	}	
	
	@Bean
	public GdevAccessDeniedHandler accessDeniedBean() {
		return new GdevAccessDeniedHandler();
	}	
	
	public void setException(ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling) {
		exceptionHandling.accessDeniedHandler(accessDeniedBean());
	}

	public void setLogout(LogoutConfigurer<HttpSecurity> logout) {
		logout.logoutSuccessUrl("/home");
	}
	
	@Bean
	public GdevFormLoginFailHandler loginFailBean() {
		return new GdevFormLoginFailHandler();
	}
	
	public void setFormLogin(FormLoginConfigurer<HttpSecurity> formLogin) {
		formLogin.loginPage("/loginPage").usernameParameter("id").passwordParameter("password").failureHandler(loginFailBean()).permitAll();
	}
	
	@Bean
	public Holder getHolder() {
		return new Holder();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		//시큐리티는 암호화를 안하면 에러난다.//내부적인 정책으로 변경됨.
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}	
	
}
