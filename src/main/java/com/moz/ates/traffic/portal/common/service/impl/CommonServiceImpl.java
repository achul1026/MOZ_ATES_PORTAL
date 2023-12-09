package com.moz.ates.traffic.portal.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moz.ates.traffic.common.entity.common.MozCmCd;
import com.moz.ates.traffic.common.repository.common.MozCmCdRepository;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.portal.common.service.CommonService;

/**
 * @packageName : com.moz.ates.traffic.portal.common.service.impl 
 * @fileName    : CommonServiceImpl.java 
 * @author      : NK.KIM
 * @date        : 2022.03.10 
 * @description : 최초 생성
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    MozCmCdRepository cmCdRepository;

	/**
	 * @Method 		: getCommonCdList
	 * @Author 		: NK.KIM
	 * @Description : 공통 코드 조회
	 * <PRE>
	 * 2022. 3. 10. kim : 최초 작성
	 * </PRE>
	 */
	@Override
	public List<MozCmCd> getCommonCdList(Map<String, Object> paramMap) {
		String cdGroupId = !MozatesCommonUtils.isNull(paramMap.get("cdGroupId")) ? String.valueOf(paramMap.get("cdGroupId")) : "";
		return cmCdRepository.selectCdList(cdGroupId);
	}
	
}
