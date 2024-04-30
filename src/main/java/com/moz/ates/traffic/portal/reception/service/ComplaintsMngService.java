package com.moz.ates.traffic.portal.reception.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.moz.ates.traffic.common.entity.board.MozComplaintsReg;

/**
 * @packageName : com.moz.ates.traffic.portal.complainmng.service 
 * @fileName    : ComplainMngService.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
public interface ComplaintsMngService {
	
	/**
	  * @Method Name : countComplain
	  * @Date : 2024. 3. 29.
	  * @Author : IK.MOON
	  * @Method Brief : 민원 카운트 조회
	  * @param mozComplaintsReg
	  * @return
	  */
	public int countComplaints(MozComplaintsReg mozComplaintsReg);
	
	/**
	  * @Method Name : getComplaintList
	  * @Date : 2024. 3. 29.
	  * @Author : IK.MOON
	  * @Method Brief : 민원 리스트 조회
	  * @param mozComplaintsReg
	  * @return
	  */
	public List<MozComplaintsReg> getComplaintsList(MozComplaintsReg mozComplaintsReg);
	
	/**
	  * @Method Name : getComplaintDetail
	  * @Date : 2024. 3. 29.
	  * @Author : IK.MOON
	  * @Method Brief : 민원 상세 조회
	  * @param mozComplaintsReg
	  * @return
	  */
	public MozComplaintsReg getComplaintsDetail(MozComplaintsReg mozComplaintsReg);
	/**
	  * @Method Name : registComplain
	  * @Date : 2024. 3. 29.
	  * @Author : IK.MOON
	  * @Method Brief : 민원 등록
	  * @param mozComplaintsReg
	  */
	public void registComplaints(MozComplaintsReg mozComplaintsReg, MultipartFile[] files);
	
	/**
	  * @Method Name : updateComplaint
	  * @Date : 2024. 3. 29.
	  * @Author : IK.MOON
	  * @Method Brief : 민원 업데이트
	  * @param mozComplaintsReg
	  */
	public void updateComplaints(MozComplaintsReg mozComplaintsReg);
	
	/**
	  * @Method Name : checkPw
	  * @Date : 2024. 4. 4.
	  * @Author : IK.MOON
	  * @Method Brief : 민원 비밀번호 확인
	  * @param mozComplaintsReg
	  * @return
	  */
	public boolean checkPw(MozComplaintsReg mozComplaintsReg);
}
