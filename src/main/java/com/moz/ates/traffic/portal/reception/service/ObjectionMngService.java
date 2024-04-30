package com.moz.ates.traffic.portal.reception.service;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
import com.moz.ates.traffic.common.entity.board.MozObjReg;
import com.moz.ates.traffic.common.entity.finentc.MozFineNtcInfo;

public interface ObjectionMngService {
	
	/**
	  * @Method Name : countObjList
	  * @Date : 2024. 4. 5.
	  * @Author : IK.MOON
	  * @Method Brief : 이의제기 카운트 조회
	  * @param objReg
	  * @return
	  */
	public int countObjRegList(MozObjReg objReg);
	
	/**
	  * @Method Name : getObjList
	  * @Date : 2024. 4. 5.
	  * @Author : IK.MOON
	  * @Method Brief : 이의제기 리스트 조회
	  * @param objReg
	  * @return
	  */
	public List<MozObjReg> getObjRegList(MozObjReg objReg);
	
	/**
	  * @Method Name : getFineNtcInfoList
	  * @Date : 2024. 4. 5.
	  * @Author : IK.MOON
	  * @Method Brief : 고지서 조회
	  * @param fineNtcInfo
	  * @return
	  */
	public MozFineNtcInfo getFineNtcInfo(MozFineNtcInfo fineNtcInfo);
	
	/**
	  * @Method Name : checkPostPw
	  * @Date : 2024. 4. 5.
	  * @Author : IK.MOON
	  * @Method Brief : 이의제기 게시물 비밀번호 일치 확인
	  * @param objReg
	  * @return
	  */
	public boolean checkPostPw(MozObjReg objReg);
	
	/**
	  * @Method Name : getObjDetail
	  * @Date : 2024. 4. 5.
	  * @Author : IK.MOON
	  * @Method Brief : 이의제기 상세 조회
	  * @param mozObjReg
	  * @return
	  */
	public MozObjReg getObjRegDetail(MozObjReg objReg);
	
	/**
	  * @Method Name : saveObjReg
	  * @Date : 2024. 4. 7.
	  * @Author : IK.MOON
	  * @Method Brief : 이의제기 등록
	  * @param objReg
	  */
	public void saveObjReg(MozObjReg objReg, MultipartFile[] files);
	
	/**
	  * @Method Name : udsateObjReg
	  * @Date : 2024. 4. 7.
	  * @Author : IK.MOON
	  * @Method Brief : 이의제기 수정
	  * @param objReg
	  */
	public void updateObjReg(MozObjReg objReg);
	
}
