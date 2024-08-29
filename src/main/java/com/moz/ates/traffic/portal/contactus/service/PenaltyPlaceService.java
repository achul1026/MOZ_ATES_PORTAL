package com.moz.ates.traffic.portal.contactus.service;

import com.moz.ates.traffic.common.entity.payment.MozPlPymntInfo;
import com.moz.ates.traffic.common.repository.payment.MozPlPymntInfoRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PenaltyPlaceService {

	@Autowired
	public MozPlPymntInfoRepository mozPlPymntInfoRepository;

	/**
	 * @Method Name : getPenaltyPlaceList
	 * @Date : 2024. 2. 26.
	 * @Author : IK.MOON
	 * @Method Brief : 법칙금 납부처 목록 조회
	 * @param plPymntInfo
	 * @return
	 */
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
	public MozPlPymntInfo getPenaltyPlaceDetail(MozPlPymntInfo plPymntInfo) {
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
