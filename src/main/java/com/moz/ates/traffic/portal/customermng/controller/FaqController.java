package com.moz.ates.traffic.portal.customermng.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moz.ates.traffic.common.entity.board.MozFaq;
import com.moz.ates.traffic.common.entity.common.MozCmCd;
import com.moz.ates.traffic.portal.common.service.CommonService;
import com.moz.ates.traffic.portal.customermng.service.FaqService;

/**
 * @packageName : com.moz.ates.traffic.portal.customermng.controller 
 * @fileName    : FaqController.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
@Controller
@RequestMapping(value = "/customer/service")
public class FaqController {
	
	@Autowired
	private FaqService faqService;
	
	@Autowired
	private CommonService commonService;
	
    /**
     * @Method 		: faqList
     * @Author 		: NK.KIM
     * @Return 		: String
     * @Description : FAQ화면
     * <PRE>
     * 2022. 2. 25. kim : 최초 작성
     * </PRE>
     */
    @RequestMapping(value = "/faqList")
    public String faqList(Model model,@RequestParam Map<String,Object> paramMap){
    	Map<String,Object> cdMap = new HashMap<String, Object>();
    	
    	//컴플레인 공통 코드 Map
    	cdMap.put("cdGroupId", "faq");
    	List<MozCmCd> faqCdList = commonService.getCommonCdList(cdMap);
    	
    	//FAQ 목록 조회
    	List<MozFaq> faqList = faqService.getFaqList(paramMap);
    	
    	model.addAttribute("faqList",faqList);
    	model.addAttribute("faqCdList",faqCdList);
    	
        return "views/customermng/faqList";
    }
}
