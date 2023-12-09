package com.moz.ates.traffic.portal.inguirymng.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moz.ates.traffic.common.entity.board.MozObjReg;
import com.moz.ates.traffic.common.repository.board.MozObjRegRepository;
import com.moz.ates.traffic.common.repository.driver.MozVioInfoRepository;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.portal.inguirymng.service.ObjectionMngService;

@Service
public class ObjectionMngServiceImpl implements ObjectionMngService {

    @Autowired
    MozObjRegRepository objRegRepository; 
    
    @Autowired
    MozVioInfoRepository vioInfoRepository; 

	/**
	 * @Method 		: regObjectionInfo
	 * @Author 		: NK.KIM
	 * @Description : 이의제기 등록
	 * <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 * </PRE>
	 */
	@Override
	public void regObjectionInfo(Map<String, Object> paramMap) {
		
		MozObjReg objReg = new MozObjReg();
		String tfcEnfId 	= !MozatesCommonUtils.isNull(paramMap.get("tfcEnfId")) 	? String.valueOf(paramMap.get("tfcEnfId")) 	: "";
		String cateCd 		= !MozatesCommonUtils.isNull(paramMap.get("cateCd")) 	? String.valueOf(paramMap.get("cateCd")) 	: "";
		String wrtrEmail 	= !MozatesCommonUtils.isNull(paramMap.get("wrtrEmail"))	? String.valueOf(paramMap.get("wrtrEmail")) : "";
		String postTtl 		= !MozatesCommonUtils.isNull(paramMap.get("postTtl")) 	? String.valueOf(paramMap.get("postTtl")) 	: "";
		String postCn 		= !MozatesCommonUtils.isNull(paramMap.get("postCn")) 	? String.valueOf(paramMap.get("postCn")) 	: "";
		
		objReg.setTfcEnfId(tfcEnfId);
		objReg.setCateCd(cateCd);
		objReg.setWrtrEmail(wrtrEmail);
		objReg.setPostTtl(postTtl);
		objReg.setPostCn(postCn);
		objRegRepository.insertObjectionInfo(objReg);
	}

	@Override
	public List<Map<String, Object>> getTrafficList(Map<String, Object> paramMap) {
		return vioInfoRepository.searchTrafficList(paramMap);
	}
}
