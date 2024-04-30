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
	  * @Method Name : getCdList
	  * @Date : 2024. 3. 29.
	  * @Author : IK.MOON
	  * @Method Brief : 공통 코드 조회
	  * @param cdGroupId
	  * @return
	  */
	@Override
	public List<MozCmCd> getCdList(String cdGroupId) {
		return cmCdRepository.findAllCdList(cdGroupId);
	}
	
}
