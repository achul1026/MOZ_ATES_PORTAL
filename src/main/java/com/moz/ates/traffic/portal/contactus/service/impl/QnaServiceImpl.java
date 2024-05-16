package com.moz.ates.traffic.portal.contactus.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.moz.ates.traffic.common.component.FileUploadComponent;
import com.moz.ates.traffic.common.entity.board.MozAtchFile;
import com.moz.ates.traffic.common.entity.board.MozInqry;
import com.moz.ates.traffic.common.entity.common.UploadFileInfo;
import com.moz.ates.traffic.common.enums.BoradType;
import com.moz.ates.traffic.common.enums.RegistantType;
import com.moz.ates.traffic.common.repository.board.MozAtchFileRepository;
import com.moz.ates.traffic.common.repository.board.MozInqryRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.portal.contactus.service.QnaService;

@Service
public class QnaServiceImpl implements QnaService {

	@Autowired
	private MozInqryRepository mozInqryRepository;
	
	@Autowired
    private FileUploadComponent fileUploadComponent;
	
	@Autowired
    private MozAtchFileRepository mozAtchFileRepository;
	
	/**
	  * @Method Name : getQnaList
	  * @작성일 : 2024. 3. 7.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 문의하기 리스트 조회
	  * @param mozInqry
	  * @return
	  */
	@Override
	public List<MozInqry> getQnaList(MozInqry mozInqry) {
		return mozInqryRepository.findAllMozInqryListPortal(mozInqry);
	}

	/**
	  * @Method Name : getQnaListCount
	  * @작성일 : 2024. 3. 7.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 문의하기 리스트 개수 조회
	  * @param mozInqry
	  * @return
	  */
	@Override
	public int getQnaListCount(MozInqry mozInqry) {
		return mozInqryRepository.countMozInqryPortal(mozInqry);
	}

	/**
	  * @Method Name : isValidatePostPw
	  * @작성일 : 2024. 3. 8.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 문의하기 열람 비밀번호 검증
	  * @param postPw
	  * @return
	  */
	@Override
	public Boolean isValidatePostPw(MozInqry mozInqry) {
		MozInqry existQna = mozInqryRepository.findOneMozInqryPortal(mozInqry.getInqryId());
		if (!MozatesCommonUtils.isNull(existQna) && mozInqry.getPostPw().equals(existQna.getPostPw())) {
			return true;
		}
		return false;
	}

	/**
	  * @Method Name : getQnaDetail
	  * @작성일 : 2024. 3. 8.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 문의하기 상세 조회
	  * @param inqryId
	  * @return
	  */
	@Override
	public MozInqry getQnaDetail(String inqryId) {
		if (MozatesCommonUtils.isNull(inqryId)) {
			throw new CommonException(ErrorCode.REQUIRED_FIELDS);
		}
		
		MozInqry inqryDetail = mozInqryRepository.findOneMozInqryPortal(inqryId);
		
		if (MozatesCommonUtils.isNull(inqryDetail)) {
			throw new CommonException(ErrorCode.REQUIRED_FIELDS);
		}
		
		inqryDetail.setQstAtchFileList(
				mozAtchFileRepository.findAllMozAtchFileByAtchIdxAndRgsTy(inqryId, RegistantType.PORTAL_USER));
		
		inqryDetail.setAnsAtchFileList(
				mozAtchFileRepository.findAllMozAtchFileByAtchIdxAndRgsTy(inqryId, RegistantType.ADMIN_USER));

		return inqryDetail;
	}

	/**
	  * @Method Name : save
	  * @작성일 : 2024. 3. 8.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 문의하기 등록
	  * @param mozInqry
	  */
	@Override
	@Transactional
	public void save(MozInqry mozInqry, MultipartFile[] uploadFile) {

		mozInqry.setInqryId(MozatesCommonUtils.getUuid());

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
				atchFile.setAtchIdx(mozInqry.getInqryId());
				atchFile.setFileOrgNm(uploadFileInfo.getOriginalFileNm());
				atchFile.setFileSaveNm(uploadFileInfo.getFileNm());
				atchFile.setFilePath(uploadFileInfo.getFilePath());
				atchFile.setFileSize(uploadFileInfo.getFileSize());
				atchFile.setFileExts(uploadFileInfo.getFileExt());
				atchFile.setBrdTy(BoradType.QNA);
				atchFile.setRgsTy(RegistantType.PORTAL_USER);
				mozAtchFileRepository.insertMozAtchFile(atchFile);
			}
		}
		mozInqryRepository.saveMozInqry(mozInqry);
	}

	/**
	  * @Method Name : delete
	  * @작성일 : 2024. 3. 11.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 문의하기 삭제
	  * @param mozInqry
	  * @param uploadFile
	  */
	@Override
	@Transactional
	public void delete(MozInqry mozInqry) {
		MozInqry inqry = mozInqryRepository.findOneMozInqryPortal(mozInqry.getInqryId());
		try {
			mozInqryRepository.deleteMozInqryByInqryId(inqry.getInqryId());
			// TODO :: 로직 수정 필요
			if (!MozatesCommonUtils.isNull(inqry.getAtchFile())) {
				for(MozAtchFile atchFile : inqry.getAnsAtchFileList()) {
					mozAtchFileRepository.deleteMozAtchFileByFileIdx(atchFile.getFileIdx());
					fileUploadComponent.deleteUploadFile(atchFile.getFilePath());
				}
			}
		} catch (RuntimeException e) {
	        throw e;
	    }
	}

}
