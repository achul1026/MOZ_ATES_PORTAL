package com.moz.ates.traffic.portal.main.service;

import java.util.List;
import com.moz.ates.traffic.common.entity.board.MozBrd;
import com.moz.ates.traffic.common.entity.board.MozFaq;

/**
 * @packageName : com.moz.ates.traffic.portal.main.service 
 * @fileName    : MainService.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
public interface MainService {
	
	/**
	  * @Method Name : getNoticeList
	  * @Date : 2024. 4. 8.
	  * @Author : IK.MOON
	  * @Method Brief : 공지사항 리스트 조회
	  * @param brd
	  * @return
	  */
	public List<MozBrd> getNoticeList(MozBrd brd);
	
	/**
	  * @Method Name : getFaqList
	  * @Date : 2024. 4. 8.
	  * @Author : IK.MOON
	  * @Method Brief : FAQ리스트 조회
	  * @param faq
	  * @return
	  */
	public List<MozFaq> getFaqList(MozFaq faq);
}
