package com.moz.ates.traffic.portal.reception.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.moz.ates.traffic.common.component.FileUploadComponent;
import com.moz.ates.traffic.common.entity.board.MozAtchFile;
import com.moz.ates.traffic.common.entity.board.MozObjReg;
import com.moz.ates.traffic.common.entity.common.UploadFileInfo;
import com.moz.ates.traffic.common.entity.finentc.MozFineNtcInfo;
import com.moz.ates.traffic.common.entity.payment.MozFinePymntInfo;
import com.moz.ates.traffic.common.enums.BoradType;
import com.moz.ates.traffic.common.enums.RegistantType;
import com.moz.ates.traffic.common.repository.board.MozAtchFileRepository;
import com.moz.ates.traffic.common.repository.board.MozObjRegRepository;
import com.moz.ates.traffic.common.repository.driver.MozVioInfoRepository;
import com.moz.ates.traffic.common.repository.finentc.MozFineNtcInfoRepository;
import com.moz.ates.traffic.common.repository.payment.MozFinePymntInfoRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.Base64PasswordUtils;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.portal.reception.service.ObjectionMngService;

@Service
public class ObjectionMngServiceImpl implements ObjectionMngService {

	@Autowired
	MozObjRegRepository objRegRepository;

	@Autowired
	MozFineNtcInfoRepository fineNtcInfoRepository;

	@Autowired
	private FileUploadComponent fileUploadComponent;

	@Autowired
	private MozAtchFileRepository mozAtchFileRepository;
	
	@Autowired
	private MozAtchFileRepository atchFileRepository;

	/**
	 * @Method Name : countObjList
	 * @Date : 2024. 4. 5.
	 * @Author : IK.MOON
	 * @Method Brief : 이의제기 카운트 조회
	 * @param objReg
	 * @return
	 */
	@Override
	public int countObjRegList(MozObjReg objReg) {
		return objRegRepository.countAllMozObjRegJoinMozFineNtcInfoAndMozCmCd(objReg);
	}

	/**
	 * @Method Name : getObjList
	 * @Date : 2024. 4. 5.
	 * @Author : IK.MOON
	 * @Method Brief : 이의제기 리스트 조회
	 * @param objReg
	 * @return
	 */
	@Override
	public List<MozObjReg> getObjRegList(MozObjReg objReg) {
		return objRegRepository.findAllMozObjRegJoinMozFineNtcInfoAndMozCmCd(objReg);
	}

	/**
	 * @Method Name : getFineNtcInfoList
	 * @Date : 2024. 4. 5.
	 * @Author : IK.MOON
	 * @Method Brief : 고지서 조회
	 * @param fineNtcInfo
	 * @return
	 */
	@Override
	public MozFineNtcInfo getFineNtcInfo(MozFineNtcInfo fineNtcInfo) {
		// TODO :: 컨트롤러에서 FINE_NTC_ID null 체크 필요
		return fineNtcInfoRepository
				.findOneMozFineNtcInfoJoinMozTfcEnfMasterAndMozFinePymntInfoAndMozCmCdByTfcEnfId(fineNtcInfo);
	}

	/**
	 * @Method Name : checkPostPw
	 * @Date : 2024. 4. 5.
	 * @Author : IK.MOON
	 * @Method Brief : 이의제기 게시물 비밀번호 일치 확인
	 * @param objReg
	 * @return
	 */
	@Override
	public boolean checkPostPw(MozObjReg objReg) {
		boolean isPostPwMatches = false;

		if (MozatesCommonUtils.isNull(objReg.getPostPw())) {
			// 필수 입력이 누락되었습니다
			throw new CommonException(ErrorCode.REQUIRED_FIELDS, "A entrada obrigatória está faltando");
		}

		String postPw = objRegRepository.findOnePostPwbyObjIdx(objReg);

		if (MozatesCommonUtils.isNull(postPw)) {
			// 조회한 데이터가 존재하지 않습니다
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL, "Os dados que você pesquisou não existem");
		}

		if (Base64PasswordUtils.matches(objReg.getPostPw(), postPw)) {
			isPostPwMatches = true;
		}

		return isPostPwMatches;
	}

	/**
	 * @Method Name : getObjDetail
	 * @Date : 2024. 4. 5.
	 * @Author : IK.MOON
	 * @Method Brief : 이의제기 상세 조회
	 * @param objReg
	 * @return
	 */
	@Override
	public MozObjReg getObjRegDetail(MozObjReg objReg) {

		if (MozatesCommonUtils.isNull(objReg.getObjIdx())) {
			throw new CommonException(ErrorCode.REQUIRED_FIELDS);
		}

		MozObjReg objRegDetail = objRegRepository
				.findOneMozObjRegJoinMozFineNtcInfoAndMozWebOprtrAndMozCmCdByObjIdx(objReg);

		if (MozatesCommonUtils.isNull(objRegDetail)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}

		objRegDetail.setQstAtchFileList(
				atchFileRepository.findAllMozAtchFileByAtchIdxAndRgsTy(objReg.getObjIdx(), RegistantType.PORTAL_USER));

		objRegDetail.setAnsAtchFileList(atchFileRepository.findAllMozAtchFileByAtchIdxAndRgsTy(objReg.getObjIdx(),
				RegistantType.ADMIN_USER));

		return objRegDetail;
	}

	/**
	 * @Method Name : saveObjReg
	 * @Date : 2024. 4. 7.
	 * @Author : IK.MOON
	 * @Method Brief : 이의제기 저장
	 * @param objReg
	 */
	@Override
	public void saveObjReg(MozObjReg objReg, MultipartFile[] uploadFile) {

		// 고지서 번호 유효성 검증 및 단속번호 조회
		String tfcEnfId = objReg.getTfcEnfId();

		if (MozatesCommonUtils.isNull(tfcEnfId)) {
			// 필수 입력이 누락되었습니다
			throw new CommonException(ErrorCode.REQUIRED_FIELDS, "A entrada obrigatória está faltando");
		}

		MozFineNtcInfo fineNtcInfo = new MozFineNtcInfo();
		fineNtcInfo.setSearchTxt(tfcEnfId);

		MozFineNtcInfo validFineNtcInfo = fineNtcInfoRepository
				.findOneMozFineNtcInfoJoinMozTfcEnfMasterAndMozFinePymntInfoAndMozCmCdByTfcEnfId(fineNtcInfo);

		if (MozatesCommonUtils.isNull(validFineNtcInfo)) {
			// 오류가 발생 했습니다.
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL, "Um erro ocorreu.");
		}

		// 이의제기 등록
		objReg.setTfcEnfId(validFineNtcInfo.getTfcEnfId());
		objReg.setObjIdx(MozatesCommonUtils.getUuid());
		objReg.setPostPw(Base64PasswordUtils.encodePassword(objReg.getPostPw()));
		objReg.setAnsStts("N");

		// 파일 갯수 제한
		if (uploadFile.length > 10) {
			throw new CommonException(ErrorCode.FILE_UPLOAD_FAIL);
		}

		for (MultipartFile file : uploadFile) {
			if (!file.isEmpty() && !MozatesCommonUtils.isNull(file.getOriginalFilename())) {
				// 파일 크기 제한 (5MB)
				if (file.getSize() > 5242880) {
					throw new CommonException(ErrorCode.FILE_UPLOAD_FAIL);
				}

				String[] extArr = { "jpg", "jpeg", "png"};
				UploadFileInfo uploadFileInfo = fileUploadComponent
						.uploadFileToUploadFileInfoChkExtension(file, extArr);
				MozAtchFile atchFile = new MozAtchFile();
				atchFile.setFileIdx(MozatesCommonUtils.getUuid());
				atchFile.setAtchIdx(objReg.getObjIdx());
				atchFile.setFileOrgNm(uploadFileInfo.getOriginalFileNm());
				atchFile.setFileSaveNm(uploadFileInfo.getFileNm());
				atchFile.setFilePath(uploadFileInfo.getFilePath());
				atchFile.setFileSize(uploadFileInfo.getFileSize());
				atchFile.setFileExts(uploadFileInfo.getFileExt());
				atchFile.setBrdTy(BoradType.OBJECTIONS);
				atchFile.setRgsTy(RegistantType.PORTAL_USER);
				mozAtchFileRepository.insertMozAtchFile(atchFile);
			}
		}
		objRegRepository.saveMozObjReg(objReg);
	}

	/**
	 * @Method Name : udsateObjReg
	 * @Date : 2024. 4. 7.
	 * @Author : IK.MOON
	 * @Method Brief : 이의제기 수정
	 * @param objReg
	 */
	@Override
	public void updateObjReg(MozObjReg objReg) {
		// TODO Auto-generated method stub

	}

}
