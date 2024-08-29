package com.moz.ates.traffic.portal.inquiry.service;

import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;
import com.moz.ates.traffic.common.entity.equipment.MozCameraEnfOrg;
import com.moz.ates.traffic.common.entity.equipment.MozCameraEnfOrgFile;
import com.moz.ates.traffic.common.entity.equipment.MozTfcEnfFileInfo;
import com.moz.ates.traffic.common.entity.equipment.MozTfcEnfFineInfo;
import com.moz.ates.traffic.common.entity.finentc.MozFineNtcInfo;
import com.moz.ates.traffic.common.entity.law.MozTfcLwInfo;
import com.moz.ates.traffic.common.enums.NtcTypeCd;
import com.moz.ates.traffic.common.repository.enforcement.MozTfcEnfMasterRepository;
import com.moz.ates.traffic.common.repository.equipment.MozCameraEnfOrgFileRepository;
import com.moz.ates.traffic.common.repository.equipment.MozCameraEnfOrgRepository;
import com.moz.ates.traffic.common.repository.equipment.MozTfcEnfFileInfoRepository;
import com.moz.ates.traffic.common.repository.equipment.MozTfcEnfFineInfoRepository;
import com.moz.ates.traffic.common.repository.finentc.MozFineNtcInfoRepository;
import com.moz.ates.traffic.common.repository.law.MozTfcLwInfoRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InquiryService {

	@Autowired
	MozTfcEnfMasterRepository tfcEnfMasterRepository;

	@Autowired
	MozTfcLwInfoRepository tfcLwInfoRepository;

	@Autowired
	MozTfcEnfFileInfoRepository tfcEnfFileInfoRepository;

	@Autowired
	MozFineNtcInfoRepository fineNtcInfoRepository;
	
	@Autowired
	MozTfcEnfFineInfoRepository mozTfcEnfFineInfoRepository;

	@Autowired
	MozCameraEnfOrgRepository cameraEnfOrgRepository;

	@Autowired
	MozCameraEnfOrgFileRepository cameraEnfOrgFileRepository;

	/**
	 * @Method Name : getEnfStatus
	 * @Date : 2024. 4. 18.
	 * @Author : IK.MOON
	 * @Method Brief : 단속 상태 검색
	 * @param paramMap
	 * @return
	 */
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

		List<MozTfcEnfFileInfo> tfcEnfFileInfoList =  tfcEnfFileInfoRepository.findTfcEnfFileInfoByTfcEnfId(tfcEnfId);
		tfcEnfStatusDetail.setTfcEnfFileInfoList(tfcEnfFileInfoList);
		return tfcEnfStatusDetail;
	}
	
	public List<MozTfcEnfFineInfo> getAllTfcEnfFineInfo(Map<String, Object> paramMap) {
		String tfcEnfId = (String)paramMap.get("tfcEnfId");
		
		List<MozTfcEnfFineInfo> lawfineList = mozTfcEnfFineInfoRepository.findAllTfcEnfFineInfoJoinTfcLwFineInfoAndTfcLwInfoByTfcEnfId(tfcEnfId);
		for (MozTfcEnfFineInfo lawFine : lawfineList) {
			String lawType = lawFine.getTfcLwInfo().getLawType();
			String lawArticleNo = lawFine.getTfcLwInfo().getLawArticleNo();
			String artclNo = lawFine.getTfcLwFineInfo().getArtclNo();
			String par = lawFine.getTfcLwFineInfo().getPar();
			
			StringBuilder titleBuilder = new StringBuilder();
			
			if (MozatesCommonUtils.isNull(artclNo) && MozatesCommonUtils.isNull(par)) {
				titleBuilder.append("[").append(lawType).append("] ")
					.append(lawArticleNo);
			} else {
				if (MozatesCommonUtils.isNull(artclNo)) {
					titleBuilder.append("[").append(lawType).append("] ")
						.append(lawArticleNo).append("--").append(par);
				} else if (MozatesCommonUtils.isNull(par)) {
					titleBuilder.append("[").append(lawType).append("] ")
					.append(lawArticleNo).append("-").append(artclNo);
				} else {
					titleBuilder.append("[").append(lawType).append("] ")
					.append(lawArticleNo).append("-").append(artclNo).append("-").append(par);
				}
				
			}
			
			lawFine.getTfcLwInfo().setLawNm(titleBuilder.toString());
		}
		
		return lawfineList;
	}

	/**
	 * @Method Name : getPymntStatus
	 * @Date : 2024. 4. 23.
	 * @Author : IK.MOON
	 * @Method Brief : 납부 상태 검색
	 * @param paramMap
	 * @return
	 */
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
	public MozFineNtcInfo getFineNtcInfo(Map<String, Object> paramMap) {
		paramMap.put("delYn", "N");
		paramMap.put("ntcTy", NtcTypeCd.FIRST_NOTICE.getCode());

		MozFineNtcInfo fineNtcInfo = fineNtcInfoRepository.findOneFineNtcInfoForNoticeDetail(paramMap);

		if (MozatesCommonUtils.isNull(fineNtcInfo)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}

		return fineNtcInfo;
	}

	/**
	 * 2024.07.31
	 * 단속 카메라 감지 내역 카운트 조회
	 * @param cameraEnfOrg
	 * @return
	 */
	public int getDetectionCount(MozCameraEnfOrg cameraEnfOrg) {
		return cameraEnfOrgRepository.countBySearchOptionPortal(cameraEnfOrg);
	}

	/**
	 * 2024.07.31
	 * 단속 카메라 감지 내역 리스트 조회
	 * @param cameraEnfOrg
	 * @return
	 */
	public List<MozCameraEnfOrg> getDetectionList(MozCameraEnfOrg cameraEnfOrg) {
		return cameraEnfOrgRepository.findAllBySearchOptionPortal(cameraEnfOrg);
	}

	/**
	 * 2024.07.31
	 * 단속 카메라 감지 내역 상세 조회
	 * @param cameraEnfOrg
	 * @return
	 */
	public MozCameraEnfOrg getDetectionDetail(MozCameraEnfOrg cameraEnfOrg) {
		if (MozatesCommonUtils.isNull(cameraEnfOrg.getIdx())) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}
		MozCameraEnfOrg foundCameraEnfOrg = cameraEnfOrgRepository.fineOneByIdPortal(cameraEnfOrg.getIdx());

		if (MozatesCommonUtils.isNull(foundCameraEnfOrg)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}

		return foundCameraEnfOrg;
	}

	/**
	 * 2024.08.01
	 * 단속 카메라 감지 내역 이미지 조회
	 * @param cameraEnfOrg
	 * @return
	 */
	public List<MozCameraEnfOrgFile> getDetectionImgList(MozCameraEnfOrg cameraEnfOrg) {

		return cameraEnfOrgFileRepository.findAllByOrgIdx(cameraEnfOrg.getIdx());
	}

	/**
	 * 2024.08.01
	 * 단속 카메라 감지 이미지 Path조회
	 * @param idx
	 * @return
	 */
	public String getDetectionImgPath(Long idx) {
		return cameraEnfOrgFileRepository.findOneByIdx(idx);
	}

}
