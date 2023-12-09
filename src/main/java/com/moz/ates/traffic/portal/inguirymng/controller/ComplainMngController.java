package com.moz.ates.traffic.portal.inguirymng.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moz.ates.traffic.common.entity.common.MozCmCd;
import com.moz.ates.traffic.portal.common.service.CommonService;
import com.moz.ates.traffic.portal.inguirymng.service.ComplainMngService;

/**
 * @packageName : com.moz.ates.traffic.portal.complainmng.controller 
 * @fileName    : ComplainMngController.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
@Controller
@RequestMapping(value = "/inquirymng/complainmng")
public class ComplainMngController {
	
	@Autowired
	private ComplainMngService complainMngService;

	@Autowired
	private CommonService commonService;
	
    /**
     * @Method 		: complainInfo
     * @Author 		: NK.KIM
     * @Return 		: String
     * @Description : 컴플레인 등록 화면 
     * <PRE>
     * 2022. 2. 25. kim : 최초 작성
     * </PRE>
     */
    @RequestMapping(value = "/complainInfo")
    public String complainInfo(Model model){
    	
    	Map<String,Object> cdMap = new HashMap<String, Object>();
    	
    	//컴플레인 공통 코드 Map
    	cdMap.put("cdGroupId", "cmp");
    	List<MozCmCd> complainCdList = commonService.getCommonCdList(cdMap);
    	
    	model.addAttribute("complainCdList",complainCdList);
    	
        return "views/inguirymng/complainInfo";
    }
    
    /**
     * @Method 		: regComplnInquiry
     * @Author 		: NK.KIM
     * @Return 		: String
     * @Description : 컴플레인 등록
     * <PRE>
     * 2022. 2. 25. kim : 최초 작성
     * </PRE>
     */
    @RequestMapping(value = "/complnInquiryRegister")
    public String regComplnInquiry(Model model, @RequestParam Map<String,Object>paramMap,
    		RedirectAttributes redirectAttributes){
    	try {
    		
    		//컴플레인 등록
    		complainMngService.regComplainInfo(paramMap);
    		redirectAttributes.addFlashAttribute("resultMsg","Your complaint registration was successful.");
    		redirectAttributes.addFlashAttribute("resultMsgType","success");
    	}catch (Exception e) {
    		redirectAttributes.addFlashAttribute("resultMsg","Your complaint registration failed.");
    		redirectAttributes.addFlashAttribute("resultMsgType","warning");
		}
    	
    	return "redirect:/inquirymng/complainmng/complainInfo";
    }
}
