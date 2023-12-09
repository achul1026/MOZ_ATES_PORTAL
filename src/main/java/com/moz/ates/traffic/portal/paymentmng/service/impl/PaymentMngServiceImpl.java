package com.moz.ates.traffic.portal.paymentmng.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;
import com.moz.ates.traffic.common.entity.payment.MozFinePymntInfo;
import com.moz.ates.traffic.common.repository.enforcement.MozTfcEnfMasterRepository;
import com.moz.ates.traffic.common.repository.payment.MozFinePymntInfoRepository;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.portal.paymentmng.service.PaymentMngService;

/**
 * @packageName : com.moz.ates.traffic.portal.paymentmng.service.impl 
 * @fileName    : PaymentMngServiceImpl.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
@Service
public class PaymentMngServiceImpl implements PaymentMngService {

    @Autowired
    MozTfcEnfMasterRepository tfcEnfMasterRepository;
    
    @Autowired
    MozFinePymntInfoRepository finePymntInfoRepository;

	@Override
	public MozTfcEnfMaster getPaymentInfo(Map<String, Object> paramMap) {
		return tfcEnfMasterRepository.selectPaymentInfo(paramMap);
	}

	/**
	 * @class  PaymentMngServiceImpl.java
	 * @Method modifyPaymentInfo
	 * @Author NK.KIM
	 * <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 * </PRE>
	 */
	@Override
	public void modifyPaymentInfo(Map<String, Object> paramMap) {
		MozFinePymntInfo finePymntInfo = new MozFinePymntInfo();
		String pymntMethod 	= !MozatesCommonUtils.isNull(paramMap.get("pymntMethod"))  ? String.valueOf(paramMap.get("pymntMethod")) : "";
		String pymntId 		= !MozatesCommonUtils.isNull(paramMap.get("pymntId"))  	   ? String.valueOf(paramMap.get("pymntId")) : "";
		
		finePymntInfo.setPymntMethod(pymntMethod);
		finePymntInfo.setPymntId(pymntId);
		
		finePymntInfoRepository.updatePaymentInfo(finePymntInfo);
	}
}
