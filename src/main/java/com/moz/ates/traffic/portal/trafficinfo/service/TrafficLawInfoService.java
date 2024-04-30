package com.moz.ates.traffic.portal.trafficinfo.service;

import java.util.List;
import com.moz.ates.traffic.common.entity.law.MozTfcLwInfo;

public interface TrafficLawInfoService {
	
	/**
	  * @Method Name : getTrafficLawCount
	  * @Date : 2024. 4. 2.
	  * @Author : IK.MOON
	  * @Method Brief : 법률 카운트 조회
	  * @param tfcLwInfo
	  * @return
	  */
	public int getTrafficLawCount(MozTfcLwInfo tfcLwInfo);
	
	/**
	  * @Method Name : getTrafficLawList
	  * @Date : 2024. 4. 2.
	  * @Author : IK.MOON
	  * @Method Brief : 법률 리스트 조회
	  * @param mozTfcLwInfo
	  * @return
	  */
	public List<MozTfcLwInfo> getTrafficLawList(MozTfcLwInfo tfcLwInfo);
	
	/**
	  * @Method Name : getTrafficLawDetail
	  * @Date : 2024. 4. 2.
	  * @Author : IK.MOON
	  * @Method Brief : 법률 상세 조회
	  * @param mozTfcLwInfo
	  * @return
	  */
	public MozTfcLwInfo getTrafficLawDetail(MozTfcLwInfo tfcLwInfo);
}
