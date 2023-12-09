package com.moz.ates.traffic.portal.finesmng.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;
import com.moz.ates.traffic.portal.common.Pagination;
import com.moz.ates.traffic.portal.finesmng.service.FinesMngService;

/**
 * @packageName : com.moz.ates.traffic.portal.finesmng.controller 
 * @fileName    : FinesMngController.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
@Controller
@RequestMapping(value = "/finesmng")
public class FinesMngController {
	
	@Autowired
	private FinesMngService finesMngService; 

	/**
     * @Method 		: finesInquiryList
     * @Author 		: NK.KIM
     * @Return 		: String
     * @Description : 벌급 내역 조회 화면
     * <PRE>
     * 2022. 2. 25. kim : 최초 작성
     * </PRE>
     */
    @RequestMapping(value = "/finesInquiryList")
    public String finesInquiryList(Model model){
    	
        return "views/finesmng/finesInquiryList";
    }
    
    /**
     * @Method 		: searchFinesInquiryList
     * @Author 		: NK.KIM
     * @Return 		: String
     * @Description : 결제 정보 조회 
     * <PRE>
     * 2022. 2. 25. kim : 최초 작성
     * </PRE>
     */
    @RequestMapping(value = "/searchFinesInquiryList")
    public String searchFinesInquiryList(Model model, @RequestParam Map<String,Object>paramMap){

    	//벌금 목록 조회
    	int finesListTotalCnt = finesMngService.getFinesListTotalCnt(paramMap);
    	
    	int page = Integer.parseInt((String)paramMap.get("page"));
    	
    	// 생성인자로  총 게시물 수, 현재 페이지를 전달
        Pagination pagination = new Pagination(finesListTotalCnt, page);
        
        int startIndex = pagination.getStartIndex();
        paramMap.put("startIndex", startIndex);
        
        // 페이지 당 보여지는 게시글의 최대 개수
        int endIndex = pagination.getEndIndex();
        paramMap.put("endIndex", endIndex);
        
    	//벌금 목록 조회
    	List<MozTfcEnfMaster> finesList = finesMngService.getFinesList(paramMap);
    	
    	model.addAttribute("finesList",finesList);
    	model.addAttribute("pagination", pagination);
    	
    	return "views/finesmng/searchFinesInquiryList";
    }
    
    /**
     * @Method 		: finesInquiryDetail
     * @Author 		: NK.KIM
     * @Return 		: String
     * @Description : 벌급 내영 상세 조회
     * <PRE>
     * 2022. 3. 10. kim : 최초 작성
     * </PRE>
     */
    @RequestMapping(value = "/finesInquiryDetail")
    public String finesInquiryDetail(Model model, @RequestParam Map<String,Object>paramMap){

    	//벌금 상세 조회
    	MozTfcEnfMaster finesDetail = finesMngService.getFinesDetail(paramMap);
    	
    	model.addAttribute("finesDetail",finesDetail);
    	
    	return "views/finesmng/finesInquiryDetail";
    }
    
}
