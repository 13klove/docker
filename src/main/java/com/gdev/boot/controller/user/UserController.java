package com.gdev.boot.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gdev.boot.config.security.Holder;

@Controller
public class UserController {

	@GetMapping("/user")
	public ModelAndView user() {
		System.out.println(Holder.getMemberVo());
		return new ModelAndView("/user");
	}

}
