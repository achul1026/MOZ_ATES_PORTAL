package com.moz.ates.traffic.portal.trafficinfo.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moz.ates.traffic.common.entity.law.MozTfcLwInfo;
import com.moz.ates.traffic.common.repository.law.MozTfcLwInfoRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.portal.trafficinfo.service.TrafficLawInfoService;

@Service
public class TrafficLawInfoServiceImpl implements TrafficLawInfoService {

	@Autowired
	MozTfcLwInfoRepository mozTfcLwInfoRepository;
	
	/**
	  * @Method Name : getTrafficLawCount
	  * @Date : 2024. 4. 2.
	  * @Author : IK.MOON
	  * @Method Brief : 법률 카운트 조회
	  * @param tfcLwInfo
	  * @return
	  */
	@Override
	public int getTrafficLawCount(MozTfcLwInfo tfcLwInfo) {
		return mozTfcLwInfoRepository.countAllMozTfcLwInfo(tfcLwInfo);
	}

	/**
	  * @Method Name : getTrafficLawList
	  * @Date : 2024. 4. 2.
	  * @Author : IK.MOON
	  * @Method Brief : 법률 리스트 조뢰
	  * @param mozTfcLwInfo
	  * @return
	  */
	@Override
	public List<MozTfcLwInfo> getTrafficLawList(MozTfcLwInfo tfcLwInfo) {
		return mozTfcLwInfoRepository.findAllMozTfcLwInfo(tfcLwInfo);
	}

	/**
	  * @Method Name : getTrafficLawDetail
	  * @Date : 2024. 4. 2.
	  * @Author : IK.MOON
	  * @Method Brief : 법률 상세 조회
	  * @param mozTfcLwInfo
	  * @return
	  */
	@Override
	public MozTfcLwInfo getTrafficLawDetail(MozTfcLwInfo tfcLwInfo) {
		
		if (MozatesCommonUtils.isNull(tfcLwInfo.getTfcLawId())) {
			throw new CommonException(ErrorCode.REQUIRED_FIELDS);
		}
		
		MozTfcLwInfo tfcLwInfoDetail = mozTfcLwInfoRepository.findOneMozTfcLwInfoByTfcLwId(tfcLwInfo);
		
		if (MozatesCommonUtils.isNull(tfcLwInfoDetail)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}
		
		return tfcLwInfoDetail;
	}

}
