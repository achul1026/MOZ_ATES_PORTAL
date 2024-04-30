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
import com.moz.ates.traffic.common.entity.board.MozObjReg;
import com.moz.ates.traffic.common.entity.common.CommonResponse;
import com.moz.ates.traffic.common.entity.finentc.MozFineNtcInfo;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.portal.common.service.CommonService;
import com.moz.ates.traffic.portal.reception.service.ObjectionMngService;

/**
 * @packageName : com.moz.ates.traffic.portal.reception.controller
 * @fileName : ObjectionMngController.java
 * @author : NK.KIM
 * @date : 2022.02.25
 * @description : 최초 생성
 */
@Controller
@RequestMapping("/objection")
public class ObjectionMngController {

	@Autowired
	private ObjectionMngService objectionMngService;

	@Autowired
	private CommonService commonService;

	/**
	 * @Method Name : objectionInfo
	 * @작성일 : 2024. 01. 16.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 이의제기
	 * @param model
	 * @return
	 */
	@GetMapping("/list.do")
	public String objectionInfo(Model model, MozObjReg objReg) {
		int page = objReg.getPage();
		int totalCnt = objectionMngService.countObjRegList(objReg);
		Pagination pagination = new Pagination(totalCnt, page);
		
		objReg.setStart((page - 1) * pagination.getPageSize());
		
		model.addAttribute("objReg", objReg);
		model.addAttribute("pagination", pagination);
		model.addAttribute("objRegList", objectionMngService.getObjRegList(objReg));
		
		model.addAttribute("objCate", commonService.getCdList("OBJECTION_CATE_CD"));
		
		return "views/reception/objectionInfo";
	}

	/**
	  * @Method Name : objectionSelect
	  * @Date : 2024. 2. 19.
	  * @Author : IK.MOON
	  * @Method Brief : 이의제기 신청 전 고지서 번호 확인 화면
	  * @param model
	  * @return
	  */
	@GetMapping("/fineNtc/search.do")
	public String objectionSelect() {
		return "views/reception/objectionSearchFineNtc";
	}

	/**
	  * @Method Name : searchFineNtc
	  * @Date : 2024. 4. 7.
	  * @Author : IK.MOON
	  * @Method Brief : 이의제기 신청 전 고지서 번호 확인 비동기 처리
	  * @param fineNtcInfo
	  * @return
	  */
	@PostMapping("/fineNtc/search.ajax")
	@ResponseBody
	public CommonResponse<?> searchFineNtc(MozFineNtcInfo fineNtcInfo) {
		
		MozFineNtcInfo fineNtcInfoSearched =  objectionMngService.getFineNtcInfo(fineNtcInfo);
		
		if (MozatesCommonUtils.isNull(fineNtcInfoSearched)) {
			// 일치하는 결과가 없숩니다.
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST, "Não houve resultados correspondentes.");
		}
		
		return CommonResponse.ResponseSuccess(HttpStatus.OK, "success", "url", fineNtcInfoSearched);
	}
	
	/**
	  * @Method Name : objectionRegister
	  * @Date : 2024. 2. 19.
	  * @Author : IK.MOON
	  * @Method Brief : 이의제기 신청 화면
	  * @param model
	  * @return
	  */
	@GetMapping("/save.do")
	public String objectionRegister(Model model, 
			MozFineNtcInfo fineNtcInfo){
		
		MozFineNtcInfo fineNtcInfoSearched = objectionMngService.getFineNtcInfo(fineNtcInfo);
		
		if (MozatesCommonUtils.isNull(fineNtcInfoSearched)) {
			throw new CommonException(ErrorCode.REQUIRED_FIELDS);
		}
		
		model.addAttribute("fineNtcInfo", fineNtcInfoSearched);
		model.addAttribute("objCate", commonService.getCdList("OBJECTION_CATE_CD"));
		return "views/reception/objectionRegister";
	}
	
	/**
	  * @Method Name : objectionRegisterAjax
	  * @Date : 2024. 4. 7.
	  * @Author : IK.MOON
	  * @Method Brief : 이의제기 저장 비동기 호출
	  * @param mozObjReg
	  * @return
	  */
	@PostMapping("/save.ajax")
	@ResponseBody
	public CommonResponse<?> objectionRegisterAjax(MozObjReg mozObjReg, @RequestParam(name = "files", required = false) MultipartFile[] files) {
		
		try {
			objectionMngService.saveObjReg(mozObjReg, files);
		} catch (Exception e) {
			// 오류가 발생 했습니다.
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST, "Um erro ocorreu.");
		}
		
		// 정상적으로 처리가 되었습니다.
		return CommonResponse.ResponseCodeAndMessage(HttpStatus.OK, "Foi processado normalmente.");
	}
	
	
	/**
	  * @Method Name : checkPostPw
	  * @Date : 2024. 4. 6.
	  * @Author : IK.MOON
	  * @Method Brief :
	  * @param objReg
	  * @return
	  */
	@PostMapping("/pwCheck.ajax")
	@ResponseBody
	public CommonResponse<?> checkPostPw(MozObjReg objReg) {
		
		if (!objectionMngService.checkPostPw(objReg)) {
			// 비밀번호를 확인해 주세요
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST, "Por favor verifique sua senha");
		}
		
		return CommonResponse.ResponseSuccess(HttpStatus.OK, "Good","/objection/detail.do",objReg.getObjIdx());
	}
	
	/**
	 * @Method Name : objectionResult
	 * @작성일 : 2024. 01. 16.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 이의제기 상세 화면
	 * @param model
	 * @return
	 */
	@PostMapping("/detail.do")
	public String objectionResult(Model model, MozObjReg objReg) {
		model.addAttribute("objRegDetail", objectionMngService.getObjRegDetail(objReg));
		return "views/reception/objectionDetail";
	}

	/**
	 * @Method Name : objectionModify
	 * @작성일 : 2024. 01. 16.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 이의제기 수정 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/update.do")
	public String objectionModify(Model model) {
		return "views/reception/objectionModify";
	}

}
