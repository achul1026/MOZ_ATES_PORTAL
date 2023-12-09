package com.moz.ates.traffic.portal.finesmng.service;

import java.util.List;
import java.util.Map;

import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;

/**
 * @packageName : com.moz.ates.traffic.portal.finesmng.service 
 * @fileName    : FinesMngService.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
public interface FinesMngService {
	
	/**
	 * @Method 		: getFinesDetail
	 * @Author 		: NK.KIM
	 * @Return 		: Map<String,Object>
	 * @Description : 벌금 상세 조회
	 * <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 * </PRE>
	 */
	public MozTfcEnfMaster getFinesDetail(Map<String,Object> paramMap);

	/**
	 * @Method 		: getFinesListTotalCnt
	 * @Author 		: NK.KIM
	 * @Return 		: int
	 * @Description : 벌금 내역 개수 조회
	 * <PRE>
	 * 2022. 3. 10. kim : 최초 작성
	 * </PRE>
	 */
	public int getFinesListTotalCnt(Map<String,Object> paramMap);
	
	/**
	 * @Method 		: getFinesList
	 * @Author 		: NK.KIM
	 * @Return 		: List<Map<String,Object>>
	 * @Description : 벌금 내역 조회
	 * <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 * </PRE>
	 */
	public List<MozTfcEnfMaster> getFinesList(Map<String,Object> paramMap);
}
