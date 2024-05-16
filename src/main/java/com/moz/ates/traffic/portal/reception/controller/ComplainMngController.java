package com.moz.ates.traffic.portal.reception.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.moz.ates.traffic.common.component.Pagination;
import com.moz.ates.traffic.common.entity.board.MozComplaintsReg;
import com.moz.ates.traffic.common.entity.common.CommonResponse;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.portal.common.service.CommonService;
import com.moz.ates.traffic.portal.reception.service.ComplaintsMngService;

/**
 * @packageName : com.moz.ates.traffic.portal.complainmng.controller
 * @fileName : ComplainMngController.java
 * @author : NK.KIM
 * @date : 2022.02.25
 * @description : 최초 생성
 */
@Controller
@RequestMapping("/complain")
public class ComplainMngController {

	@Autowired
	private ComplaintsMngService complaintsMngService;

	@Autowired
	private CommonService commonService;

	/**
	  * @Method Name : complainInfo
	  * @Date : 2024. 3. 29.
	  * @Author : IK.MOON
	  * @Method Brief : 민원 리스트 페이지
	  * @param model
	  * @param mozComplaintsReg
	  * @return
	  */
	@GetMapping("/list.do")
	public String complainInfo(Model model, MozComplaintsReg mozComplaintsReg) {
		
		int page = mozComplaintsReg.getPage();
		int totalCnt = complaintsMngService.countComplaints(mozComplaintsReg);
		Pagination pagination = new Pagination(totalCnt, page);
		
		mozComplaintsReg.setStart((page - 1) * pagination.getPageSize());
		
		model.addAttribute("complaintsList", complaintsMngService.getComplaintsList(mozComplaintsReg));
		model.addAttribute("complaintsReg", mozComplaintsReg);
		model.addAttribute("pagination", pagination);
		
		// 카테고리 코드 조회
		model.addAttribute("complaintsCate", commonService.getCdList("COMPLAINT_CATE_CD"));

		return "views/reception/complainList";
	}

	/**
	 * @Method Name : complainRegister
	 * @작성일 : 2024. 01. 16.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 컴플레인 등록
	 * @param model
	 * @return
	 */
	@GetMapping("/save.do")
	public String complainRegister(Model model) {
		
		// 카테고리 코드 조회
		model.addAttribute("complaintsCate", commonService.getCdList("COMPLAINT_CATE_CD"));
		return "views/reception/complainRegister";
	}

	/**
	  * @Method Name : complaintsResgisterAjax
	  * @Date : 2024. 3. 29.
	  * @Author : IK.MOON
	  * @Method Brief : 민원 등록 Ajax
	  * @param mozComplaintsReg
	  * @return
	  */
	@PostMapping("/save.ajax")
	@ResponseBody
	public CommonResponse<?> complaintsResgisterAjax(
			MozComplaintsReg mozComplaintsReg,
			@RequestParam(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {
		try {
			complaintsMngService.registComplaints(mozComplaintsReg, uploadFiles);
			// 성공적으로 등록되었습니다.
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.OK, "Você foi registrado com sucesso.");
		} catch (Exception e) {
			// 성공적으로 등록되지 않았습니다
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST,"Não foi registrado com sucesso.");
		}
	}

	/**
	  * @Method Name : checkPostPw
	  * @Date : 2024. 4. 4.
	  * @Author : IK.MOON
	  * @Method Brief :
	  * @param mozComplaintsReg
	  * @return
	  */
	@PostMapping("/pwCheck.ajax")
	@ResponseBody
	public CommonResponse<?> checkPostPw(MozComplaintsReg mozComplaintsReg) {
		
		if (!complaintsMngService.checkPw(mozComplaintsReg)) {
			// 비밀번호를 확인해 주세요
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST, "Por favor verifique sua senha");
		}
		
		return CommonResponse.ResponseSuccess(HttpStatus.OK, "Good","/complain/detail.do",mozComplaintsReg.getComplaintsIdx());
	}
	
	/**
	 * @Method Name : complainResult
	 * @작성일 : 2024. 01. 16.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 컴플레인 상세
	 * @param model
	 * @return
	 */
	@PostMapping("/detail.do")
	public String complainResult(Model model, MozComplaintsReg mozComplaintsReg) {
		model.addAttribute("complaintsDetail", complaintsMngService.getComplaintsDetail(mozComplaintsReg));
		
		return "views/reception/complainDetail";
	}

	/**
	 * @Method Name : complainModify
	 * @작성일 : 2024. 01. 16.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 컴플레인 수정
	 * @param model
	 * @return
	 */
	@GetMapping("/update.do")
	public String complainModify(Model model) {
		return "views/reception/complainModify";
	}
}
