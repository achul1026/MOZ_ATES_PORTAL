package com.moz.ates.traffic.portal.inguirymng.service;

import java.util.Map;

/**
 * @packageName : com.moz.ates.traffic.portal.complainmng.service 
 * @fileName    : ComplainMngService.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
public interface ComplainMngService {
	
	/**
	 * @Method 		: regComplainInfo
	 * @Author 		: NK.KIM
	 * @Return 		: void
	 * @Description : 컴플레인 등록
	 * <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 * </PRE>
	 */
	public void regComplainInfo(Map<String,Object>paramMap);
}
