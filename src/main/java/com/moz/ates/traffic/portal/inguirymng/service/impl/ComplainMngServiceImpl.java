package com.moz.ates.traffic.portal.inguirymng.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moz.ates.traffic.common.entity.board.MozComplaintsReg;
import com.moz.ates.traffic.common.repository.board.MozComplaintsRegRepository;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.portal.inguirymng.service.ComplainMngService;

/**
 * @packageName : com.moz.ates.traffic.portal.complainmng.service.impl 
 * @fileName    : ComplainMngServiceImpl.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
@Service
public class ComplainMngServiceImpl implements ComplainMngService {

    @Autowired
    MozComplaintsRegRepository complaintsRegRepository; 

	/**
	 * @Method 		: regComplainInfo
	 * @Author 		: NK.KIM
	 * @Description : 컴플레인 등록
	 * <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 * </PRE>
	 */
	@Override
	public void regComplainInfo(Map<String, Object> paramMap) {
		MozComplaintsReg complaintsReg = new MozComplaintsReg();
		String cateCd 		= !MozatesCommonUtils.isNull(paramMap.get("cateCd")) 	? String.valueOf(paramMap.get("cateCd")) 	: "";
		String wrtrEmail 	= !MozatesCommonUtils.isNull(paramMap.get("wrtrEmail")) ? String.valueOf(paramMap.get("wrtrEmail")) : "";
		String postTtl 		= !MozatesCommonUtils.isNull(paramMap.get("postTtl")) 	? String.valueOf(paramMap.get("postTtl")) 	: "";
		String postCn 		= !MozatesCommonUtils.isNull(paramMap.get("postCn")) 	? String.valueOf(paramMap.get("postCn")) 	: "";
		
		complaintsReg.setCateCd(cateCd);
		complaintsReg.setWrtrEmail(wrtrEmail);
		complaintsReg.setPostTtl(postTtl);
		complaintsReg.setPostCn(postCn);
		complaintsRegRepository.insertComaplainInfo(complaintsReg);
	}
}
