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
	 * @Method 		: getCommonCdList
	 * @Author 		: NK.KIM
	 * @Return 		: List<Map<String,Object>>
	 * @Description : 공통코드 조회
	 * <PRE>
	 * 2022. 3. 10. kim : 최초 작성
	 * </PRE>
	 */
	public List<MozCmCd> getCommonCdList(Map<String,Object> paramMap);
	
}
