package com.moz.ates.traffic.portal.contactus.service;

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
	  * @Method Name : getFaqList
	  * @Date : 2024. 2. 21.
	  * @Author : IK.MOON
	  * @Method Brief : FAQ 목록 조회
	  * @param mozFaq
	  * @return
	  */
	public List<MozFaq> getFaqList(MozFaq mozFaq);
	
	/**
	  * @Method Name : getFaqCount
	  * @Date : 2024. 2. 21.
	  * @Author : IK.MOON
	  * @Method Brief : FAQ count 조회
	  * @param mozFaq
	  * @return
	  */
	public int getFaqCount(MozFaq mozFaq);
	
	/**
	  * @Method Name : getFaqDetail
	  * @Date : 2024. 2. 23.
	  * @Author : IK.MOON
	  * @Method Brief : FAQ 상세 조회
	  * @param mozFaq
	  * @return
	  */
	public MozFaq getFaqDetail(MozFaq mozFaq);
}
