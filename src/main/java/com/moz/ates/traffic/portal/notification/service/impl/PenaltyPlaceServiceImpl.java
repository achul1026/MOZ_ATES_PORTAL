package com.moz.ates.traffic.portal.notification.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moz.ates.traffic.common.entity.payment.MozPlPymntInfo;
import com.moz.ates.traffic.common.repository.payment.MozPlPymntInfoRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.portal.notification.service.PenaltyPlaceService;

@Service
public class PenaltyPlaceServiceImpl implements PenaltyPlaceService {

	@Autowired
	MozPlPymntInfoRepository mozPlPymntInfoRepository;
	/**
	  * @Method Name : getPenaltyPlaceList
	  * @Date : 2024. 2. 26.
	  * @Author : IK.MOON
	  * @Method Brief : 법칙금 납부처 목록 조회
	  * @param plPymntInfo
	  * @return
	  */
	@Override
	public List<MozPlPymntInfo> getPenaltyPlaceList(MozPlPymntInfo plPymntInfo) {
		
		return mozPlPymntInfoRepository.findAllPenaltyPlaceList(plPymntInfo);
	}
	/**
	  * @Method Name : getPenaltyPlaceListCnt
	  * @Date : 2024. 2. 26.
	  * @Author : IK.MOON
	  * @Method Brief : 법칙금 납부처 count 조회
	  * @param plPymntInfo
	  * @return
	  */
	@Override
	public int getPenaltyPlaceListCount(MozPlPymntInfo plPymntInfo) {
		return mozPlPymntInfoRepository.countPenaltyPlaceList(plPymntInfo);
		}
	
	/**
	  * @Method Name : getPenaltyPlaceDetail
	  * @Date : 2024. 2. 26.
	  * @Author : IK.MOON
	  * @Method Brief : 범칙금 납부터 상세 조회
	  * @param plPymntInfo
	  * @return
	  */
	@Override
	public MozPlPymntInfo getPenaltyPlaceDetail(MozPlPymntInfo plPymntInfo) {
		// TODO :: lat, lng 데이터 필요
		String placePymntId = plPymntInfo.getPlacePymntId();
		if(MozatesCommonUtils.isNull(placePymntId)) {
			throw new CommonException(ErrorCode.REQUIRED_FIELDS);
		}
		
		MozPlPymntInfo dbPlPymntInfo = mozPlPymntInfoRepository.findOnePenaltyPlaceDetail(placePymntId);
		
		if(MozatesCommonUtils.isNull(dbPlPymntInfo)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}
		
		return dbPlPymntInfo;
	}
	
	

}
