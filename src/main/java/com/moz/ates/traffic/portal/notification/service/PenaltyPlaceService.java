package com.moz.ates.traffic.portal.notification.service;

import java.util.List;
import com.moz.ates.traffic.common.entity.payment.MozPlPymntInfo;

public interface PenaltyPlaceService {
	/**
	  * @Method Name : getPenaltyPlaceList
	  * @Date : 2024. 2. 26.
	  * @Author : IK.MOON
	  * @Method Brief : 법칙금 납부처 목록 조회
	  * @param plPymntInfo
	  * @return
	  */
	public List<MozPlPymntInfo> getPenaltyPlaceList(MozPlPymntInfo plPymntInfo);
	
	/**
	  * @Method Name : getPenaltyPlaceListCnt
	  * @Date : 2024. 2. 26.
	  * @Author : IK.MOON
	  * @Method Brief : 법칙금 납부처 count 조회
	  * @param plPymntInfo
	  * @return
	  */
	public int getPenaltyPlaceListCount(MozPlPymntInfo plPymntInfo);
	
	/**
	  * @Method Name : getPenaltyPlaceDetail
	  * @Date : 2024. 2. 26.
	  * @Author : IK.MOON
	  * @Method Brief : 범칙금 납부터 상세 조회
	  * @param plPymntInfo
	  * @return
	  */
	public MozPlPymntInfo getPenaltyPlaceDetail(MozPlPymntInfo plPymntInfo);
	
	
}
