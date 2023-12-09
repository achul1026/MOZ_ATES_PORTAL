package com.moz.ates.traffic.portal.inguirymng.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moz.ates.traffic.common.entity.common.MozCmCd;
import com.moz.ates.traffic.portal.common.service.CommonService;
import com.moz.ates.traffic.portal.inguirymng.service.ObjectionMngService;

/**
 * @packageName : com.moz.ates.traffic.portal.inguirymng.controller 
 * @fileName    : ObjectionMngController.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
@Controller
@RequestMapping(value = "")
public class ObjectionMngController {
	
	@Autowired
	private ObjectionMngService objectionMngService;

	@Autowired
	private CommonService commonService;
	
    /**
     * @Method 		: objectionInfo
     * @Author 		: NK.KIM
     * @Return 		: String
     * @Description : 이의제기 화면
     * <PRE>
     * 2022. 2. 25. kim : 최초 작성
     * </PRE>
     */
    @RequestMapping(value = "/objectionInfo")
    public String objectionInfo(Model model){

    	Map<String,Object> cdMap = new HashMap<String, Object>();
    	
    	//이의제기 공통 코드 Map
    	cdMap.put("cdGroupId", "obj");
    	List<MozCmCd> objectionCdList = commonService.getCommonCdList(cdMap);
    	
    	model.addAttribute("objectionCdList",objectionCdList);
    	
        return "views/inguirymng/objectionInfo";
    }
    
    /**
     * @Method 		: regComplnInquiry
     * @Author 		: NK.KIM
     * @Return 		: String
     * @Description : 이의제기 등록
     * <PRE>
     * 2022. 2. 25. kim : 최초 작성
     * </PRE>
     */
    @RequestMapping(value = "/objectionInquiryRegister")
    public String regComplnInquiry(Model model, @RequestParam Map<String,Object>paramMap,
    		RedirectAttributes redirectAttributes){
    	try {
    		
    		//컴플레인 등록
    		objectionMngService.regObjectionInfo(paramMap);
    		redirectAttributes.addFlashAttribute("resultMsg","Your objection registration was successful.");
    		redirectAttributes.addFlashAttribute("resultMsgType","success");
    	}catch (Exception e) {
    		redirectAttributes.addFlashAttribute("resultMsg","Your objection registration failed.");
    		redirectAttributes.addFlashAttribute("resultMsgType","warning");
		}
    	
    	return "redirect:/inquirymng/objectionmng/objectionInfo";
    }
    
    @RequestMapping(value = "/searchTrafficList")
    @ResponseBody
    public List<Map<String,Object>> searchTrafficList(@RequestParam Map<String,Object> paramMap){
    	List<Map<String,Object>> result = objectionMngService.getTrafficList(paramMap);
    	return result;
    }
}
