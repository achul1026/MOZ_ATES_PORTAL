package com.moz.ates.traffic.portal.customermng.service;

import java.util.List;
import java.util.Map;

import com.moz.ates.traffic.common.entity.board.MozFaq;

/**
 * @packageName : com.moz.ates.traffic.portal.customermng.service 
 * @fileName    : FaqService.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
public interface FaqService {

	/**
	 * @Method 		: getFaqList
	 * @Author 		: NK.KIM
	 * @Return 		: List<Map<String,Object>>
	 * @Description : FAQ 목록 조회
	 * <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 * </PRE>
	 */
	public List<MozFaq> getFaqList(Map<String,Object> paramMap);
}
