package com.moz.ates.traffic.portal.contactus.service;

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
import com.moz.ates.traffic.common.util.Base64PasswordUtils;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class QnaService {

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
	public int getQnaListCount(MozInqry mozInqry) {
		return mozInqryRepository.countMozInqryPortal(mozInqry);
	}

	/**
	 * @Method Name : isValidatePostPw
	 * @작성일 : 2024. 3. 8.
	 * @작성자 : SM.KIM
	 * @Method 설명 : 문의하기 열람 비밀번호 검증
	 * @return
	 */
	public Boolean isValidatePostPw(MozInqry mozInqry) {
		MozInqry existQna = mozInqryRepository.findOneMozInqryPortal(mozInqry.getInqryId());
		return Base64PasswordUtils.matches(mozInqry.getPostPw(), existQna.getPostPw());
	}

	/**
	 * @Method Name : getQnaDetail
	 * @작성일 : 2024. 3. 8.
	 * @작성자 : SM.KIM
	 * @Method 설명 : 문의하기 상세 조회
	 * @param inqryId
	 * @return
	 */
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
	@Transactional
	public void save(MozInqry mozInqry, MultipartFile[] uploadFile) {

		mozInqry.setInqryId(MozatesCommonUtils.getUuid());
		mozInqry.setPostPw(Base64PasswordUtils.encodePassword(mozInqry.getPostPw()));

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
	 */
	@Transactional
	public void delete(MozInqry mozInqry) {
		if (MozatesCommonUtils.isNull(mozInqry.getInqryId())) {
			throw new CommonException(ErrorCode.REQUIRED_FIELDS);
		}

		int validCount = mozInqryRepository.countByAnsYnAndNcsYn(mozInqry);

		if (validCount < 1) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}

		List<MozAtchFile> qstFileList =  mozAtchFileRepository.findAllMozAtchFileByAtchIdxAndRgsTy(
				mozInqry.getInqryId(), RegistantType.PORTAL_USER);
		List<MozAtchFile> ansFileList = mozAtchFileRepository.findAllMozAtchFileByAtchIdxAndRgsTy(
				mozInqry.getInqryId(), RegistantType.ADMIN_USER);

		if(qstFileList != null) {
			for(MozAtchFile deleteFileInfo : qstFileList) {
				if(!MozatesCommonUtils.isNull(deleteFileInfo.getFileIdx())) {
					MozAtchFile fileInfo = new MozAtchFile();
					fileInfo = mozAtchFileRepository.findOneMozAtchFileByFileIdx(deleteFileInfo.getFileIdx());
					fileUploadComponent.deleteUploadFile(fileInfo.getFilePath());
					mozAtchFileRepository.deleteMozAtchFileByFileIdx(fileInfo.getFileIdx());
				}
			}
		}

		if(ansFileList != null) {
			for(MozAtchFile deleteFileInfo : ansFileList) {
				if(!MozatesCommonUtils.isNull(deleteFileInfo.getFileIdx())) {
					MozAtchFile fileInfo = new MozAtchFile();
					fileInfo = mozAtchFileRepository.findOneMozAtchFileByFileIdx(deleteFileInfo.getFileIdx());
					fileUploadComponent.deleteUploadFile(fileInfo.getFilePath());
					mozAtchFileRepository.deleteMozAtchFileByFileIdx(fileInfo.getFileIdx());
				}
			}
		}
		mozInqryRepository.deleteMozInqryByInqryId(mozInqry.getInqryId());
	}

}
