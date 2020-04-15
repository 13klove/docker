package com.gdev.boot.controller.regist;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import com.gdev.boot.config.exception.RuntimeValidException;
import com.gdev.boot.domain.member.MemberRepository;
import com.gdev.boot.domain.member.MemberVo;
import com.gdev.boot.domain.sms.SmsVo;
import com.gdev.boot.util.sms.DsendSms;

@Service
public class RegistService {

	@Autowired
	private DsendSms dsendSms;
	@Autowired
	private MemberRepository memberRepository;
	
	
	public void hpCheck(HttpServletRequest request, MemberVo param) throws ClientProtocolException, JSONException, IOException {
		if(!param.getHp().matches("(01[016789])(\\d{3,4})(\\d{4})")) throw new RuntimeValidException("Check your hp");
		String code = getCode();
		dsendSms.send(SmsVo.builder().title("gDev 회원가입 인증").context("헨드폰 인증 번호에 ["+code+"]를 입력해 주세요.").receiver(Arrays.asList(param.getHp())).build());
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(60*5);
		session.setAttribute("hpCode", code);
	}
	
	public String getCode() {
		String code = "";
		Random random = new Random();
		for(int i = 0; i < 5; i++) {
			code += random.nextInt(10);
		}
		return code;
	}

	public Boolean codeComfirm(HttpServletRequest request, MemberVo param) {
		HttpSession session = request.getSession();
		if(session == null) throw new RuntimeValidException("Retry code");
		String code = (String) session.getAttribute("hpCode");
		if(param.getHpCode().equals(code)) return true;
		else return false;
	}

	public Boolean idCheck(MemberVo param) {
		if(Pattern.matches("^[a-zA-Z0-9]*$", param.getId())){
			throw new RuntimeValidException("아이디는 영문, 숫자조합과 5자이상 15자 이하만 가능합니다.");
		}
		Optional<MemberVo> member = memberRepository.findMemberById("findMemberById", param);
		if(!member.isPresent()) throw new RuntimeValidException("이미 존재하는 아이디 입니다.");
		return true;
	}
}
