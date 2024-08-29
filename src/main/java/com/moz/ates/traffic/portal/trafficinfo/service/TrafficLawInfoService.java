package com.moz.ates.traffic.portal.trafficinfo.service;

import com.moz.ates.traffic.common.entity.law.MozTfcLwFineInfo;
import com.moz.ates.traffic.common.entity.law.MozTfcLwInfo;
import com.moz.ates.traffic.common.repository.law.MozTfcLwFineInfoRepository;
import com.moz.ates.traffic.common.repository.law.MozTfcLwInfoRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrafficLawInfoService {

	@Autowired
	MozTfcLwInfoRepository mozTfcLwInfoRepository;
	
	@Autowired
	MozTfcLwFineInfoRepository tfcLwFineInfoRepository;

	/**
	 * @Method Name : getTrafficLawCount
	 * @Date : 2024. 4. 2.
	 * @Author : IK.MOON
	 * @Method Brief : 법률 카운트 조회
	 * @param tfcLwInfo
	 * @return
	 */
	public int getTrafficLawCount(MozTfcLwInfo tfcLwInfo) {
		return mozTfcLwInfoRepository.countAllMozTfcLwInfo(tfcLwInfo);
	}

	/**
	 * @Method Name : getTrafficLawList
	 * @Date : 2024. 4. 2.
	 * @Author : IK.MOON
	 * @Method Brief : 법률 리스트 조뢰
	 * @param tfcLwInfo
	 * @return
	 */
	public List<MozTfcLwInfo> getTrafficLawList(MozTfcLwInfo tfcLwInfo) {
		return mozTfcLwInfoRepository.findAllMozTfcLwInfo(tfcLwInfo);
	}

	/**
	 * @Method Name : getTrafficLawDetail
	 * @Date : 2024. 4. 2.
	 * @Author : IK.MOON
	 * @Method Brief : 법률 상세 조회
	 * @param tfcLwInfo
	 * @return
	 */
	public MozTfcLwInfo getTrafficLawDetail(MozTfcLwInfo tfcLwInfo) {
		return mozTfcLwInfoRepository.findOneMozTfcLwInfoByTfcLwId(tfcLwInfo);
	}
	
	/**
	  * @Method Name : getLawFineInfoDetail
	  * @Date : 2024. 6. 11.
	  * @Author : IK.MOON
	  * @Method Brief : 법률 상세 : 범칙금 리스트 조회
	  * @param tfcLwInfo
	  * @return
	  */
	public List<MozTfcLwFineInfo> getLawFineInfoList(MozTfcLwInfo tfcLwInfo) {
		String tfcLawId = tfcLwInfo.getTfcLawId();
		
		return tfcLwFineInfoRepository.findMozTfcLwFineInfoByTfcLawIdJoinMozCmCdForPortal(tfcLawId);
	}
}
