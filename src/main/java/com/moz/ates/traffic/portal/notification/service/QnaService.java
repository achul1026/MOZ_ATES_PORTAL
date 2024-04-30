package com.moz.ates.traffic.portal.notification.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.moz.ates.traffic.common.entity.board.MozInqry;

public interface QnaService {

	/**
	  * @Method Name : getQnaList
	  * @작성일 : 2024. 3. 7.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 문의하기 리스트 조회
	  * @param mozInqry
	  * @return
	  */
	List<MozInqry> getQnaList(MozInqry mozInqry);

	/**
	  * @Method Name : getQnaListCount
	  * @작성일 : 2024. 3. 7.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 문의하기 리스트 개수 조회
	  * @param mozInqry
	  * @return
	  */
	int getQnaListCount(MozInqry mozInqry);

	/**
	  * @Method Name : isValidatePostPw
	  * @작성일 : 2024. 3. 8.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 문의하기 열람 비밀번호 검증
	  * @param mozInqry
	  * @return
	  */
	Boolean isValidatePostPw(MozInqry mozInqry);

	/**
	  * @Method Name : getQnaDetail
	  * @작성일 : 2024. 3. 8.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 문의하기 상세 조회
	  * @param inqryId
	  * @return
	  */
	MozInqry getQnaDetail(String inqryId);

	/**
	  * @Method Name : save
	  * @작성일 : 2024. 3. 8.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 문의하기 등록
	  * @param mozInqry
	 * @param uploadFile 
	  */
	void save(MozInqry mozInqry, MultipartFile[] uploadFile);

	/**
	  * @Method Name : delete
	  * @작성일 : 2024. 3. 11.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 문의하기 삭제
	  * @param mozInqry
	  * @param uploadFile
	  */
	void delete(MozInqry mozInqry);

}
