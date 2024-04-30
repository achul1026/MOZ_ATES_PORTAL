package com.moz.ates.traffic.portal.notification.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.moz.ates.traffic.common.component.FileUploadComponent;
import com.moz.ates.traffic.common.entity.board.MozAtchFile;
import com.moz.ates.traffic.common.entity.board.MozInqry;
import com.moz.ates.traffic.common.entity.common.UploadFileInfo;
import com.moz.ates.traffic.common.repository.board.MozAtchFileRepository;
import com.moz.ates.traffic.common.repository.board.MozInqryRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.portal.notification.service.QnaService;

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
		return mozInqryRepository.findOneMozInqryPortal(inqryId);
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
		
		String inqryId = MozatesCommonUtils.getUuid();
		mozInqry.setInqryId(inqryId);
		
		try {
			if (uploadFile != null && uploadFile.length > 0) {
				// 파일 갯수 제한
				if (uploadFile.length > 4) {
					throw new CommonException(ErrorCode.FILE_UPLOAD_FAIL);
				}
				
				// 파일 크기 제한 (10MB)
				for (MultipartFile file : uploadFile) {
					if (file.getSize() > 10485760) {
						throw new CommonException(ErrorCode.FILE_UPLOAD_FAIL);
					}
				}
				
				String[] extArr = {"jpg", "jpeg", "png"};
				
				List<UploadFileInfo> uploadFileInfo = fileUploadComponent.uploadFilesToUploadFileInfoListChkExtension(uploadFile, extArr);    		
				
				for (UploadFileInfo newUploadFileInfo : uploadFileInfo) {

					MozAtchFile atchFile =new MozAtchFile();
					String fileIdx = MozatesCommonUtils.getUuid();
					atchFile.setFileIdx(fileIdx);
					atchFile.setAtchIdx(inqryId);
					atchFile.setAtchIdx(mozInqry.getInqryId());
					atchFile.setFileOrgNm(newUploadFileInfo.getOriginalFileNm());
					atchFile.setFileSaveNm(newUploadFileInfo.getFileNm());
					atchFile.setFilePath(newUploadFileInfo.getFilePath());
					atchFile.setFileSize(newUploadFileInfo.getFileSize());
					atchFile.setFileExts(newUploadFileInfo.getFileExt());
					mozAtchFileRepository.insertMozAtchFile(atchFile);
				}
			}
			mozInqryRepository.saveMozInqry(mozInqry);
		} catch (Exception e) {
			throw new CommonException(ErrorCode.FILE_UPLOAD_FAIL);
		}
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
