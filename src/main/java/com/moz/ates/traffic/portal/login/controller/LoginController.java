package com.moz.ates.traffic.portal.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@RequestMapping(value = "/login")
	public String login(Model model) {

		return "views/login/login";
	}

	/**
	 * @Method Name : loginPwFindInfo
	 * @작성일 : 2024. 01. 12.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 로그인 비밀번호 찾기
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login/pwfindinfo")
	public String loginPwFindInfo(Model model) {

		return "views/login/loginPwFindInfo";
	}

	/**
	 * @Method Name : loginPwFindChange
	 * @작성일 : 2024. 01. 12.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 로그인 비밀번호 변경
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login/pwfindchange")
	public String loginPwFindChange(Model model) {

		return "views/login/loginPwFindChange";
	}

	/**
	 * @Method Name : loginJoin
	 * @작성일 : 2024. 01. 12.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 회원가입
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login/join")
	public String loginJoin(Model model) {

		return "views/login/join";
	}

	/**
	 * @Method Name : loginJoinComplete
	 * @작성일 : 2024. 01. 12.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 회원가입 완료
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login/join/complete")
	public String loginJoinComplete(Model model) {

		return "views/login/joinComplete";
	}

}
