package com.moz.ates.traffic.portal.customermng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moz.ates.traffic.portal.customermng.service.TrafficSafetyService;

/**
 * @packageName : com.moz.ates.traffic.portal.customermng.controller 
 * @fileName    : TrafficSafetyController.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
@Controller
@RequestMapping(value = "/customer/service")
public class TrafficSafetyController {
	
	@Autowired
	private TrafficSafetyService trafficSafetyService;
	
	/**
	 * @Method 		: trafficSafeInfo
	 * @Author 		: NK.KIM
	 * @Return 		: String
	 * @Description : 교통 안전 정보 화면
	 * <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 * </PRE>
	 */
	@RequestMapping(value = "/trafficSafeInfo")
    public String trafficSafeInfo(Model model){

        return "views/customermng/trafficSafeInfo";
    }
	
	/**
	 * @Method 		: trafficSafeTraingPrgm
	 * @Author 		: NK.KIM
	 * @Return 		: String
	 * @Description : 교통 정보 트레이닝 프로그램 화면
	 * <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 * </PRE>
	 */
	@RequestMapping(value = "/trafficSafeTraingPrgm")
	public String trafficSafeTraingPrgm(Model model){
		
		return "views/customermng/trafficSafeTraingPrgm";
	}
}
