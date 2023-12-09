package com.moz.ates.traffic.portal.inguirymng.service;

import java.util.List;
import java.util.Map;

public interface ObjectionMngService {
	
	/**
	 * @Method 		: regObjectionInfo
	 * @Author 		: NK.KIM
	 * @Return 		: void
	 * @Description : 이의제기 등록
	 * <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 * </PRE>
	 */
	public void regObjectionInfo(Map<String,Object>paramMap);
	
	
	public List<Map<String, Object>> getTrafficList(Map<String,Object>paramMap);
	
}
