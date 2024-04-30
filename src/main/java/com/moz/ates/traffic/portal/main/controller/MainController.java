package com.moz.ates.traffic.portal.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.moz.ates.traffic.common.entity.board.MozBrd;
import com.moz.ates.traffic.common.entity.board.MozFaq;
import com.moz.ates.traffic.portal.main.service.MainService;



/**
 * @packageName : com.moz.ates.traffic.portal.main.controller
 * @fileName : MainController.java
 * @author : NK.KIM
 * @date : 2022.02.25
 * @description : 최초 생성
 */
@Controller
public class MainController {

	@Autowired
	private MainService mainService;


	/**
	  * @Method Name : main
	  * @Date : 2024. 4. 8.
	  * @Author : IK.MOON
	  * @Method Brief : 메인페이지 이동
	  * @param model
	  * @return
	  */
	@GetMapping(value = "/")
	public String main(Model model, MozBrd brd, MozFaq faq) {
		
		model.addAttribute("noticeList", mainService.getNoticeList(brd));
		model.addAttribute("faqList", mainService.getFaqList(faq));
		return "views/main/main";
	}
}
