package com.moz.ates.traffic.portal.inquiry.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;
import com.moz.ates.traffic.common.entity.equipment.MozTfcEnfFileInfo;
import com.moz.ates.traffic.common.entity.finentc.MozFineNtcInfo;
import com.moz.ates.traffic.common.entity.law.MozTfcLwInfo;
import com.moz.ates.traffic.common.enums.NtcTypeCd;
import com.moz.ates.traffic.common.repository.enforcement.MozTfcEnfMasterRepository;
import com.moz.ates.traffic.common.repository.equipment.MozTfcEnfFileInfoRepository;
import com.moz.ates.traffic.common.repository.finentc.MozFineNtcInfoRepository;
import com.moz.ates.traffic.common.repository.law.MozTfcLwInfoRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.portal.inquiry.service.InquiryService;

@Service
public class InquiryServiceImpl implements InquiryService {
	
	@Autowired
	MozTfcEnfMasterRepository tfcEnfMasterRepository;
	
	@Autowired
	MozTfcLwInfoRepository tfcLwInfoRepository;
	
	@Autowired
	MozTfcEnfFileInfoRepository tfcEnfFileInfoRepository;
	
	@Autowired
	MozFineNtcInfoRepository fineNtcInfoRepository;
	
	/**
	  * @Method Name : getEnfStatus
	  * @Date : 2024. 4. 18.
	  * @Author : IK.MOON
	  * @Method Brief : 단속 상태 검색
	  * @param paramMap
	  * @return
	  */
	@Override
	public MozTfcEnfMaster getEnfStatus(Map<String, Object> paramMap) {
		paramMap.put("delYn", "N");
		MozTfcEnfMaster tfcEnfStatus = tfcEnfMasterRepository.findOneMozTfcEnfMasterStatus(paramMap);
		if (MozatesCommonUtils.isNull(tfcEnfStatus)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}
		
		return tfcEnfStatus;
	}

	/**
	  * @Method Name : getEnfStatusDetail
	  * @Date : 2024. 4. 18.
	  * @Author : IK.MOON
	  * @Method Brief : 단속 상태 상세 조회
	  * @param paramMap
	  * @return
	  */
	@Override
	public MozTfcEnfMaster getEnfStatusDetail(Map<String, Object> paramMap) {
		paramMap.put("delYn", "N");
		MozTfcEnfMaster tfcEnfStatusDetail = tfcEnfMasterRepository.findOneMozTfcEnfMasterStatusDetail(paramMap);
		if(MozatesCommonUtils.isNull(tfcEnfStatusDetail)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}
		
		// 위반 법률 조회
		String tfcEnfId = tfcEnfStatusDetail.getTfcEnfId();
		List<MozTfcLwInfo> lwList = tfcLwInfoRepository.findAllMozTfcLwInfoByTfcEnfId(tfcEnfId);
		tfcEnfStatusDetail.setTfcLwInfoList(lwList);
		
		// TODO :: 파일 단속리스트 조회
		
		List<MozTfcEnfFileInfo> tfcEnfFileInfoList =  tfcEnfFileInfoRepository.findTfcEnfFileInfoByTfcEnfId(tfcEnfId);
		tfcEnfStatusDetail.setTfcEnfFileInfoList(tfcEnfFileInfoList);
		return tfcEnfStatusDetail;
	}

	/**
	  * @Method Name : getPymntStatus
	  * @Date : 2024. 4. 23.
	  * @Author : IK.MOON
	  * @Method Brief : 납부 상태 검색
	  * @param paramMap
	  * @return
	  */
	@Override
	public MozFineNtcInfo getPymntStatus(Map<String, Object> paramMap) {
		paramMap.put("delYn", "N");
		MozFineNtcInfo fineNtcInfo = fineNtcInfoRepository.findOneMozFineNtcInfoStatus(paramMap);
		if (MozatesCommonUtils.isNull(fineNtcInfo)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}
		
		return fineNtcInfo;
	}

	/**
	  * @Method Name : getPymntSatusDetail
	  * @Date : 2024. 4. 23.
	  * @Author : IK.MOON
	  * @Method Brief : 납부 상태 조회
	  * @param paramMap
	  * @return
	  */
	@Override
	public MozTfcEnfMaster getPymntSatusDetail(Map<String, Object> paramMap) {
		paramMap.put("delYn", "N");
		
		MozTfcEnfMaster pymntStatusDetail = tfcEnfMasterRepository.findOneMozTfcEnfMasterStatusDetail(paramMap);
		if(MozatesCommonUtils.isNull(pymntStatusDetail)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}
		
		// 위반 법률 조회
		String tfcEnfId = pymntStatusDetail.getTfcEnfId();
		List<MozTfcLwInfo> lwList = tfcLwInfoRepository.findAllMozTfcLwInfoByTfcEnfId(tfcEnfId);
		pymntStatusDetail.setTfcLwInfoList(lwList);
		
		return pymntStatusDetail;
	}

	/**
	  * @Method Name : getLcenEnfCount
	  * @Date : 2024. 4. 25.
	  * @Author : IK.MOON
	  * @Method Brief : 라이센스 조회 - 단속 리스트 카운트 조회
	  * @param paramMap
	  * @return
	  */
	@Override
	public int getLcenEnfCount(MozTfcEnfMaster tfcEnfMaster) {
		tfcEnfMaster.setDelYn("N");
		return tfcEnfMasterRepository.countAllMozTfcEnfMasterByDvrLcenIdAndVioBrthAndDelYn(tfcEnfMaster);
	}

	/**
	  * @Method Name : getLcenEnfList
	  * @Date : 2024. 4. 25.
	  * @Author : IK.MOON
	  * @Method Brief : 라이센스 조회 - 단속 리스트 조회
	  * @param paramMap
	  * @return
	  */
	@Override
	public List<MozTfcEnfMaster> getLcenEnfList(MozTfcEnfMaster tfcEnfMaster) {
		tfcEnfMaster.setDelYn("N");
		return tfcEnfMasterRepository.findAllMozTfcEnfMasterByDvrLcenIdAndVioBrthAndDelYn(tfcEnfMaster);
	}

	/**
	  * @Method Name : getFineNtcInfo
	  * @Date : 2024. 4. 29.
	  * @Author : IK.MOON
	  * @Method Brief : 납부 고지서 정보 조회
	  * @param paramMap
	  * @return
	  */
	@Override
	public MozFineNtcInfo getFineNtcInfo(Map<String, Object> paramMap) {
		paramMap.put("delYn", "N");
		paramMap.put("ntcTy", NtcTypeCd.FIRST_NOTICE.getCode());
		
		return fineNtcInfoRepository.findOneFineNtcInfoForNoticeDetail(paramMap);
	}

}
