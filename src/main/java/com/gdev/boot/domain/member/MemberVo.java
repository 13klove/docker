package com.gdev.boot.domain.member;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.groups.Default;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gdev.boot.config.security.RoleAttribute;
import com.gdev.boot.config.security.RoleGroup;
import com.google.common.collect.Sets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MemberVo implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	@NotBlank(groups= {Regist.class}, message="Write your Id")
	private String id;
	@NotBlank(groups= {Regist.class}, message="Write your Password")
	private String password;
	@NotBlank(groups= {Regist.class}, message="Write your Name")
	private String name;
	@Email(groups={Regist.class}, message="Write your Mail")
	private String mail;
	//@Email(groups={Regist.class}, message="Write your HP")
	private String hp;
	private String hpCode;
	private RoleGroup roleGroup;
	private Set<GrantedAuthority> authorities = Sets.newHashSet();
	private String shId;
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(RoleAttribute ...authoritie) {
		Arrays.stream(authoritie).forEach(a -> {
			authorities.add(new SimpleGrantedAuthority(a.toString()));
		});;
		authorities.add(new SimpleGrantedAuthority(authoritie.toString()));
		roleGroup.getRole().forEach(a -> {
			authorities.add(new SimpleGrantedAuthority(a.toString()));	
		});		
	}
	
	public void setAuthorities() {
		roleGroup.getRole().forEach(a -> {
			authorities.add(new SimpleGrantedAuthority(a.toString()));	
		});		
	}	

	@Override
	public String getUsername() {
		return id;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public interface Regist extends Default{};
	
}