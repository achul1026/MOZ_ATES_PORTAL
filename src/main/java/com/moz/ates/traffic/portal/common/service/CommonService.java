package com.moz.ates.traffic.portal.common.service;

import java.util.List;
import java.util.Map;

import com.moz.ates.traffic.common.entity.common.MozCmCd;

/**
 * @packageName : com.moz.ates.traffic.portal.common.service 
 * @fileName    : CommonService.java 
 * @author      : NK.KIM
 * @date        : 2022.03.10 
 * @description : 최초 생성
 */
public interface CommonService {
	
	/**
	  * @Method Name : getCommonCdList
	  * @Date : 2024. 3. 29.
	  * @Author : IK.MOON
	  * @Method Brief : 공통 코드 조뢰
	  * @param cdGroupId
	  * @return
	  */
	public List<MozCmCd> getCdList(String cdGroupId);
	
}
