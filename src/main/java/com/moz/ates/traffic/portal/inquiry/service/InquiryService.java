package com.moz.ates.traffic.portal.inquiry.service;

import java.util.List;
import java.util.Map;

import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;
import com.moz.ates.traffic.common.entity.finentc.MozFineNtcInfo;

public interface InquiryService {
	
	/**
	  * @Method Name : getEnfStatus
	  * @Date : 2024. 4. 18.
	  * @Author : IK.MOON
	  * @Method Brief : 단속 상태 검색
	  * @param paramMap
	  * @return
	  */
	public MozTfcEnfMaster getEnfStatus(Map<String, Object> paramMap);
	
	/**
	  * @Method Name : getEnfStatusDetail
	  * @Date : 2024. 4. 18.
	  * @Author : IK.MOON
	  * @Method Brief : 단속 상태 상세 조회
	  * @param paramMap
	  * @return
	  */
	public MozTfcEnfMaster getEnfStatusDetail(Map<String, Object> paramMap);
	
	/**
	  * @Method Name : getPymntStatus
	  * @Date : 2024. 4. 23.
	  * @Author : IK.MOON
	  * @Method Brief : 납부 상태 검색
	  * @param paramMap
	  * @return
	  */
	public MozFineNtcInfo getPymntStatus(Map<String, Object> paramMap);
	
	/**
	  * @Method Name : getPymntSatusDetail
	  * @Date : 2024. 4. 23.
	  * @Author : IK.MOON
	  * @Method Brief : 납부 상태 조회
	  * @param paramMap
	  * @return
	  */
	public MozTfcEnfMaster getPymntSatusDetail(Map<String, Object> paramMap);
	
	/**
	  * @Method Name : getLcenEnfCount
	  * @Date : 2024. 4. 25.
	  * @Author : IK.MOON
	  * @Method Brief : 라이센스 조회 - 단속 리스트 카운트 조회
	  * @param paramMap
	  * @return
	  */
	public int getLcenEnfCount(MozTfcEnfMaster tfcEnfMaster);
	
	/**
	  * @Method Name : getLcenEnfList
	  * @Date : 2024. 4. 25.
	  * @Author : IK.MOON
	  * @Method Brief : 라이센스 조회 - 단속 리스트 조회
	  * @param paramMap
	  * @return
	  */
	public List<MozTfcEnfMaster> getLcenEnfList(MozTfcEnfMaster tfcEnfMaster);
	
	/**
	  * @Method Name : getFineNtcInfo
	  * @Date : 2024. 4. 29.
	  * @Author : IK.MOON
	  * @Method Brief : 납부 고지서 정보 조회
	  * @param paramMap
	  * @return
	  */
	public MozFineNtcInfo getFineNtcInfo(Map<String, Object> paramMap);
}
