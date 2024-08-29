package com.moz.ates.traffic.portal.common.service;

import com.moz.ates.traffic.common.entity.common.MozCmCd;
import com.moz.ates.traffic.common.repository.common.MozCmCdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @packageName : com.moz.ates.traffic.portal.common.service 
 * @fileName    : CommonService.java 
 * @author      : NK.KIM
 * @date        : 2022.03.10 
 * @description : 최초 생성
 */
@Service
public class CommonService {

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
	public List<MozCmCd> getCdList(String cdGroupId) {
		return cmCdRepository.findAllCdList(cdGroupId);
	}

}
