package com.moz.ates.traffic.portal.contactus.service;

import java.util.List;
import com.moz.ates.traffic.common.entity.board.MozBrd;

public interface NoticeService {
	
	/**
	  * @Method Name : getNoticeList
	  * @Date : 2024. 2. 19.
	  * @Author : IK.MOON
	  * @Method Brief : 공지사항 리스트 조회
	  * @return
	  */
	public List<MozBrd> getNoticeList(MozBrd mozBrd);
	
	/**
	  * @Method Name : getNoticeDetail
	  * @Date : 2024. 2. 20.
	  * @Author : IK.MOON
	  * @Method Brief : 공지사항 상세 조회
	  * @param boardIdx
	  * @return
	  */
	public MozBrd getNoticeDetail(String boardIdx);
	
	/**
	  * @Method Name : getNoticeListCount
	  * @Date : 2024. 2. 20.
	  * @Author : IK.MOON
	  * @Method Brief : 공지사항 count 조회
	  * @param mozBrd
	  * @return
	  */
	public int getNoticeListCount(MozBrd mozBrd);
}
