package com.moz.ates.traffic.portal.inquiry.service;

import java.util.Map;

import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;

/**
 * @packageName : com.moz.ates.traffic.portal.paymentmng.service 
 * @fileName    : PaymentMngService.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
public interface PaymentMngService {

	/**
	 * @Method 		: getPaymentInfo
	 * @Author 		: NK.KIM
	 * @Return 		: Map<String,Object>
	 * @Description : 결제 정보 조회
	 * <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 * </PRE>
	 */
	public MozTfcEnfMaster getPaymentInfo(Map<String,Object>paramMap);
	
	/**
	 * @Method 		: modifyPaymentInfo
	 * @Author 		: NK.KIM
	 * @Return 		: void
	 * @Description : 결제 정보 수정
	 * <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 * </PRE>
	 */
	public void modifyPaymentInfo(Map<String,Object>paramMap);
}
