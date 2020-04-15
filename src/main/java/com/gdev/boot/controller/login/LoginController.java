package com.gdev.boot.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gdev.boot.config.security.Holder;

@Controller
public class LoginController {

	@RequestMapping("/loginPage")
	public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/loginPage");	
	}
	
}
