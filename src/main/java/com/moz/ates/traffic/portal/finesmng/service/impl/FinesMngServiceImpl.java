package com.moz.ates.traffic.portal.finesmng.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;
import com.moz.ates.traffic.common.repository.enforcement.MozTfcEnfMasterRepository;
import com.moz.ates.traffic.portal.finesmng.service.FinesMngService;

/**
 * @packageName : com.moz.ates.traffic.portal.finesmng.service.impl 
 * @fileName    : FinesMngServiceImpl.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
@Service
public class FinesMngServiceImpl implements FinesMngService {
	
	@Autowired
	MozTfcEnfMasterRepository tfcEnfMasterRepository;
	/**
	 * @Method 		: getFinesDetail
	 * @Author 		: NK.KIM
	 * @Description : 벌금 상세 조회
	 * <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 * </PRE>
	 */
	@Override
	public MozTfcEnfMaster getFinesDetail(Map<String, Object> paramMap) {
		return tfcEnfMasterRepository.selectFinesDetail(paramMap);
	}

	/**
	 * @Method 		: getFinesList
	 * @Author 		: NK.KIM
	 * @Description : 벌금 내역 조회
	 * <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 * </PRE>
	 */
	@Override
	public List<MozTfcEnfMaster> getFinesList(Map<String, Object> paramMap) {
		return tfcEnfMasterRepository.selectFinesList(paramMap);
	}

	@Override
	public int getFinesListTotalCnt(Map<String, Object> paramMap) {
		return tfcEnfMasterRepository.selectFinesListTotalCnt(paramMap);
	}
}
