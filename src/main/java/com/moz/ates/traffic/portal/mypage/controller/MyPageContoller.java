package com.moz.ates.traffic.portal.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyPageContoller {

	@RequestMapping(value = "/mypage")
	public String mypage(Model model) {

		return "views/mypage/mypage";
	}

	/**
	 * @Method Name : mypageModify
	 * @작성일 : 2024. 01. 15.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 내정보 수정
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/mypage/modify")
	public String mypageModify(Model model) {

		return "views/mypage/mypageModify";
	}

	/**
	 * @Method Name : mypagePwModify
	 * @작성일 : 2024. 01. 15.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 내정보 비밀번호 수정
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/mypage/pw/modify")
	public String mypagePwModify(Model model) {

		return "views/mypage/mypagePwModify";
	}
}
