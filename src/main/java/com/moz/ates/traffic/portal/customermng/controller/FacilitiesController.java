package com.moz.ates.traffic.portal.customermng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moz.ates.traffic.portal.customermng.service.FacilitiesService;

/**
 * @packageName : com.moz.ates.traffic.portal.customermng.controller 
 * @fileName    : FacilitiesController.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
@Controller
@RequestMapping(value = "/customer/service")
public class FacilitiesController {
	
	@Autowired
	private FacilitiesService facilitiesService;
	
    /**
     * @Method 		: facilitiesInfo
     * @Author 		: NK.KIM
     * @Return 		: String
     * @Description : 시설 안내 정보 화면
     * <PRE>
     * 2022. 2. 25. kim : 최초 작성
     * </PRE>
     */
    @RequestMapping(value = "/facilitiesInfo")
    public String facilitiesInfo(Model model){

        return "views/customermng/facilitiesInfo";
    }
}
