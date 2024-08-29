package com.moz.ates.traffic.portal.reception.service;

import com.moz.ates.traffic.common.component.FileUploadComponent;
import com.moz.ates.traffic.common.entity.board.MozAtchFile;
import com.moz.ates.traffic.common.entity.board.MozComplaintsReg;
import com.moz.ates.traffic.common.entity.common.UploadFileInfo;
import com.moz.ates.traffic.common.enums.BoradType;
import com.moz.ates.traffic.common.enums.RegistantType;
import com.moz.ates.traffic.common.repository.board.MozAtchFileRepository;
import com.moz.ates.traffic.common.repository.board.MozComplaintsRegRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.Base64PasswordUtils;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
public class ComplaintService {

	@Autowired
	MozComplaintsRegRepository complaintsRegRepository;

	@Autowired
	MozAtchFileRepository atchFileRepository;

	@Autowired
	FileUploadComponent fileUploadComponent;

	/**
	 * @Method Name : countComplaints
	 * @Date : 2024. 3. 29.
	 * @Author : IK.MOON
	 * @Method Brief : 민원 카운트 조회
	 * @param mozComplaintsReg
	 * @return
	 */
	public int countComplaints(MozComplaintsReg mozComplaintsReg) {

		return complaintsRegRepository.countAllMozComplaintsReg(mozComplaintsReg);
	}

	/**
	 * @Method Name : getComplaintsList
	 * @Date : 2024. 3. 29.
	 * @Author : IK.MOON
	 * @Method Brief : 민원 리스트 조회
	 * @param mozComplaintsReg
	 * @return
	 */
	public List<MozComplaintsReg> getComplaintsList(MozComplaintsReg mozComplaintsReg) {
		return complaintsRegRepository.findAllMozComplaintsRegJoinMozCmCd(mozComplaintsReg);
	}

	/**
	 * @Method Name : getComplaintsDetail
	 * @Date : 2024. 4. 4.
	 * @Author : IK.MOON
	 * @Method Brief : 민원 상세 조회
	 * @param mozComplaintsReg
	 * @return
	 */
	public MozComplaintsReg getComplaintsDetail(MozComplaintsReg mozComplaintsReg) {
		if (MozatesCommonUtils.isNull(mozComplaintsReg.getComplaintsIdx())) {
			throw new CommonException(ErrorCode.REQUIRED_FIELDS);
		}

		MozComplaintsReg complaintsDetail = complaintsRegRepository
				.findOneMozComplaintsRegJoinMozCmCdAndMozWebOprtrByComplaintsIdx(mozComplaintsReg);

		if (MozatesCommonUtils.isNull(complaintsDetail)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}

		complaintsDetail.setQstAtchFileList(atchFileRepository.findAllMozAtchFileByAtchIdxAndRgsTy(
				mozComplaintsReg.getComplaintsIdx(), RegistantType.PORTAL_USER));

		complaintsDetail.setAnsAtchFileList(atchFileRepository.findAllMozAtchFileByAtchIdxAndRgsTy(
				mozComplaintsReg.getComplaintsIdx(), RegistantType.ADMIN_USER));

		return complaintsDetail;
	}

	/**
	 * @Method Name : registComplaints
	 * @Date : 2024. 4. 4.
	 * @Author : IK.MOON
	 * @Method Brief : 민원 등록
	 * @param mozComplaintsReg
	 * @param files
	 */
	@Transactional
	public void registComplaints(MozComplaintsReg mozComplaintsReg, MultipartFile[] uploadFile) {

		mozComplaintsReg.setComplaintsIdx(MozatesCommonUtils.getUuid());
		mozComplaintsReg.setPostPw(Base64PasswordUtils.encodePassword(mozComplaintsReg.getPostPw()));
		mozComplaintsReg.setAnsStts("N");

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

				String[] extArr = {"jpg", "jpeg", "png"};
				UploadFileInfo uploadFileInfo = fileUploadComponent
						.uploadFileToUploadFileInfoChkExtension(file, extArr);
				MozAtchFile atchFile = new MozAtchFile();
				atchFile.setFileIdx(MozatesCommonUtils.getUuid());
				atchFile.setAtchIdx(mozComplaintsReg.getComplaintsIdx());
				atchFile.setFileOrgNm(uploadFileInfo.getOriginalFileNm());
				atchFile.setFileSaveNm(uploadFileInfo.getFileNm());
				atchFile.setFilePath(uploadFileInfo.getFilePath());
				atchFile.setFileSize(uploadFileInfo.getFileSize());
				atchFile.setFileExts(uploadFileInfo.getFileExt());
				atchFile.setBrdTy(BoradType.COMPLAINTS);
				atchFile.setRgsTy(RegistantType.PORTAL_USER);
				atchFileRepository.insertMozAtchFile(atchFile);
			}
		}
		complaintsRegRepository.insertComaplainInfo(mozComplaintsReg);
	}

	/**
	 * @Method Name : updateComplaints
	 * @Date : 2024. 4. 4.
	 * @Author : IK.MOON
	 * @Method Brief : 민원 업데이트
	 * @param mozComplaintsReg
	 */
	@Transactional
	public void updateComplaints(MozComplaintsReg complaintsReg, MultipartFile[] uploadFiles) {

		List<MozAtchFile> deleteAtchFileList = new ArrayList<MozAtchFile>();
		deleteAtchFileList = complaintsReg.getQstAtchFileList();

		if(deleteAtchFileList != null) {
			for(MozAtchFile deleteFileInfo : deleteAtchFileList) {
				if(!MozatesCommonUtils.isNull(deleteFileInfo.getFileIdx())) {
					MozAtchFile fileInfo = new MozAtchFile();
					fileInfo = atchFileRepository.findOneMozAtchFileByFileIdx(deleteFileInfo.getFileIdx());
					fileUploadComponent.deleteUploadFile(fileInfo.getFilePath());
					atchFileRepository.deleteMozAtchFileByFileIdx(fileInfo.getFileIdx());
				}
			}
		}

		// 답변 파일 저장
		if(uploadFiles != null && uploadFiles.length > 0) {
			for(MultipartFile uploadFile : uploadFiles) {
				if(!MozatesCommonUtils.isNull(uploadFile.getOriginalFilename())) {
					String[] extArr = {"jpg", "jpeg", "png"};
					UploadFileInfo uploadFileInfo = fileUploadComponent.uploadFileToUploadFileInfoChkExtension(uploadFile, extArr);
					MozAtchFile atchFile = new MozAtchFile();
					String fileIdx = MozatesCommonUtils.getUuid();
					atchFile.setFileIdx(fileIdx);
					atchFile.setAtchIdx(complaintsReg.getComplaintsIdx());
					atchFile.setFilePath(uploadFileInfo.getFilePath());
					atchFile.setFileOrgNm(uploadFileInfo.getOriginalFileNm());
					atchFile.setFileExts(uploadFileInfo.getFileExt());
					atchFile.setFileSaveNm(uploadFileInfo.getFileNm());
					atchFile.setFileSize(uploadFileInfo.getFileSize());
					atchFile.setBrdTy(BoradType.COMPLAINTS);
					atchFile.setRgsTy(RegistantType.PORTAL_USER);
					atchFileRepository.insertMozAtchFile(atchFile);
				}
			}
		}

		complaintsReg.setPostPw(Base64PasswordUtils.encodePassword(complaintsReg.getPostPw()));
		complaintsRegRepository.updateCmpQst(complaintsReg);
	}

	/**
	 * @Method Name : checkPw
	 * @Date : 2024. 4. 4.
	 * @Author : IK.MOON
	 * @Method Brief : 비밀번호 확인
	 * @param mozComplaintsReg
	 * @return
	 */
	public boolean checkPw(MozComplaintsReg mozComplaintsReg) {

		boolean isPwMathes = false;

		if(MozatesCommonUtils.isNull(mozComplaintsReg.getPostPw())) {
			//필수 입력이 누락되었습니다
			throw new CommonException(ErrorCode.REQUIRED_FIELDS, "A entrada obrigatória está faltando");
		}

		String postPw = complaintsRegRepository.findOnePostPwByComplaintsIdx(mozComplaintsReg);


		if (MozatesCommonUtils.isNull(postPw)) {
			// 조회한 데이터가 존재하지 않습니다
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL, "Os dados que você pesquisou não existem");
		}

		if (Base64PasswordUtils.matches(mozComplaintsReg.getPostPw(), postPw)) {
			isPwMathes = true;
		}

		return isPwMathes;
	}

	/**
	 * @Method Name : checkAnsStts
	 * @Date : 2024. 5. 23.
	 * @Author : IK.MOON
	 * @Method Brief : 답변상태 조회
	 * @param mozComplaintsReg
	 * @return
	 */
	public String checkAnsStts(MozComplaintsReg mozComplaintsReg) {
		return complaintsRegRepository.findOneAnsStts(mozComplaintsReg);
	}

	/**
	 * @Method Name : deleteComplaints
	 * @Date : 2024. 5. 23.
	 * @Author : IK.MOON
	 * @Method Brief : 민원 삭제
	 * @param mozComplaintsReg
	 */
	public void deleteComplaints(MozComplaintsReg mozComplaintsReg) {
		List<MozAtchFile> qstFileList =  atchFileRepository.findAllMozAtchFileByAtchIdxAndRgsTy(
				mozComplaintsReg.getComplaintsIdx(), RegistantType.PORTAL_USER);
		List<MozAtchFile> ansFileList = atchFileRepository.findAllMozAtchFileByAtchIdxAndRgsTy(
				mozComplaintsReg.getComplaintsIdx(), RegistantType.ADMIN_USER);

		if(qstFileList != null) {
			for(MozAtchFile deleteFileInfo : qstFileList) {
				if(!MozatesCommonUtils.isNull(deleteFileInfo.getFileIdx())) {
					MozAtchFile fileInfo = new MozAtchFile();
					fileInfo = atchFileRepository.findOneMozAtchFileByFileIdx(deleteFileInfo.getFileIdx());
					fileUploadComponent.deleteUploadFile(fileInfo.getFilePath());
					atchFileRepository.deleteMozAtchFileByFileIdx(fileInfo.getFileIdx());
				}
			}
		}

		if(ansFileList != null) {
			for(MozAtchFile deleteFileInfo : ansFileList) {
				if(!MozatesCommonUtils.isNull(deleteFileInfo.getFileIdx())) {
					MozAtchFile fileInfo = new MozAtchFile();
					fileInfo = atchFileRepository.findOneMozAtchFileByFileIdx(deleteFileInfo.getFileIdx());
					fileUploadComponent.deleteUploadFile(fileInfo.getFilePath());
					atchFileRepository.deleteMozAtchFileByFileIdx(fileInfo.getFileIdx());
				}
			}
		}

		complaintsRegRepository.deleteComplaints(mozComplaintsReg);
	}
}
