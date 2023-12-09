package com.moz.ates.traffic.portal.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moz.ates.traffic.portal.main.service.MainService;



/**
 * @packageName : com.moz.ates.traffic.portal.main.controller 
 * @fileName    : MainController.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
@Controller
public class MainController {
	
	@Autowired
	private MainService mainService; 
	
    /**
     * @Method main
     * @Author NK.KIM
     * @Return String
     * @Description : 메인페이지 이동
     * <PRE>
     * 2022. 2. 25. kim : 최초 작성
     * </PRE>
     */
    @RequestMapping(value = "/")
    public String main(Model model){

        return "views/main/main";
    }
}
