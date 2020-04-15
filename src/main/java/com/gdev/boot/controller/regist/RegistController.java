package com.gdev.boot.controller.regist;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gdev.boot.config.exception.RuntimeValidException;
import com.gdev.boot.domain.member.MemberVo;

@Controller
public class RegistController {

	@Autowired
	private RegistService registService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/regist")
	public ModelAndView regist() {
		return new ModelAndView("/regist");
	}

	@PostMapping("/regist/reg")
	public void reg(@Validated(MemberVo.Regist.class) @RequestBody MemberVo param, BindingResult bindingResult) {
		RuntimeValidException.valid(bindingResult);
	}
	
	@ResponseBody
	@PostMapping("/regist/hpCheck")
	public void hpCheck(HttpServletRequest request, @RequestBody MemberVo param) throws ClientProtocolException, JSONException, IOException {
		registService.hpCheck(request, param);
	}
	
	@ResponseBody
	@PostMapping("/regist/codeComfirm")
	public Boolean codeComfirm(HttpServletRequest request, @RequestBody MemberVo param) throws ClientProtocolException, JSONException, IOException {
		return registService.codeComfirm(request, param);
	}
	
	@ResponseBody
	@PostMapping("/regist/idCheck")
	public void idCheck(@RequestBody MemberVo param) {
		registService.idCheck(param);
	}	
	
}
