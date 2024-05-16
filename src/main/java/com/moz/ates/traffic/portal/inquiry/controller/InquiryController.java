package com.moz.ates.traffic.portal.inquiry.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.moz.ates.traffic.common.component.Pagination;
import com.moz.ates.traffic.common.component.validate.ValidateBuilder;
import com.moz.ates.traffic.common.component.validate.ValidateChecker;
import com.moz.ates.traffic.common.component.validate.ValidateResult;
import com.moz.ates.traffic.common.entity.common.CommonResponse;
import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;
import com.moz.ates.traffic.common.entity.finentc.MozFineNtcInfo;
import com.moz.ates.traffic.common.enums.NtcTypeCd;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.portal.inquiry.service.InquiryService;

@Controller
@RequestMapping(value = "/inquiry")
public class InquiryController {

	@Autowired
	private InquiryService inquiryService;

	/**
	  * @Method Name : enfStatusSearch
	  * @Date : 2024. 4. 18.
	  * @Author : IK.MOON
	  * @Method Brief : 단속현황 검색 화면
	  * @param model
	  * @return
	  */
	@GetMapping("/enfstatus/search.do")
	public String enfStatusSearch() {
		return "views/inquiry/enfStatusSearch";
	}

	/**
	  * @Method Name : enfStatusSearchAjax
	  * @Date : 2024. 4. 18.
	  * @Author : IK.MOON
	  * @Method Brief : 단속현황 검색 ajax
	  * @return
	  */
	@PostMapping("/enfstatus/search.ajax")
	@ResponseBody
	public CommonResponse<?> enfStatusSearchAjax(@RequestParam Map<String, Object> paramMap) {
		MozTfcEnfMaster tfcEnfStatus = null;
		
		try {
			tfcEnfStatus = inquiryService.getEnfStatus(paramMap);
		} catch (Exception e) {
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST, "Fail");
		}
		
		return CommonResponse.ResponseSuccess(HttpStatus.OK, "Success", "url", tfcEnfStatus);
	}
 	
	/**
	  * @Method Name : enfStatusDetail
	  * @Date : 2024. 4. 18.
	  * @Author : IK.MOON
	  * @Method Brief : 단속 현황 상세 화면
	  * @param model
	  * @return
	  */
	@PostMapping("/enfstatus/detail.do")
	public String enfStatusDetail(Model model, @RequestParam Map<String, Object> paramMap) {
		MozTfcEnfMaster tfcEnfStatusDetail = inquiryService.getEnfStatusDetail(paramMap);
		
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("tfcEnfStatusDetail", tfcEnfStatusDetail);
		return "views/inquiry/enfStatusDetail";
	}

	/**
	  * @Method Name : pymntStatusSearch
	  * @Date : 2024. 4. 18.
	  * @Author : IK.MOON
	  * @Method Brief : 납부 현황 검색 화면
	  * @param model
	  * @return
	  */
	@GetMapping("/pymntstatus/search.do")
	public String pymntStatusSearch() {

		return "views/inquiry/pymntStatusSearch";
	}

	/**
	  * @Method Name : pymntStatusSearchAjax
	  * @Date : 2024. 4. 23.
	  * @Author : IK.MOON
	  * @Method Brief : 납부 상태 검색 ajax
	  * @param paramMap
	  * @return
	  */
	@PostMapping("/pymntstatus/search.ajax")
	@ResponseBody
	public CommonResponse<?> pymntStatusSearchAjax(@RequestParam Map<String, Object> paramMap) {
		MozFineNtcInfo fineNtcInfo = null;
		
		try {
			fineNtcInfo = inquiryService.getPymntStatus(paramMap);
		} catch (Exception e) {
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST, "Fail");
		}
		
		return CommonResponse.ResponseSuccess(HttpStatus.OK, "Success", "url", fineNtcInfo);
	}
	
	/**
	  * @Method Name : pymntStatusDetail
	  * @Date : 2024. 4. 18.
	  * @Author : IK.MOON
	  * @Method Brief : 납부 현황 상세 화면
	  * @param model
	  * @return
	  */
	@PostMapping("/pymntstatus/detail.do")
	public String pymntStatusDetail(Model model, @RequestParam Map<String, Object> paramMap) {
		MozTfcEnfMaster pymntStatusDetail = inquiryService.getPymntSatusDetail(paramMap);
		
		model.addAttribute("pymntStatusDetail", pymntStatusDetail);
		
		return "views/inquiry/pymntStatusDetail";
	}

	/**
	 * @Method Name : finesBankPaymentDetail
	 * @작성일 : 2024. 01. 15.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 은행결제용 범칙금 납부서
	 * @param model
	 * @return
	 */
	@GetMapping("/pymntstatus/bank/detail.do")
	public String finesBankPaymentDetail() {

		return "views/inquiry/pymntBankPaymentDetail";
	}

	/**
	  * @Method Name : licenceStatusSearch
	  * @Date : 2024. 4. 25.
	  * @Author : IK.MOON
	  * @Method Brief : 면허 상태 조회 검색 페이지
	  * @return
	  */
	@GetMapping("/licencestatus/search.do")
	public String licenceStatusSearch() {
		return "views/inquiry/licenceStatus";
	}
	
	/**
	  * @Method Name : licenceStatusEnfListAjax
	  * @Date : 2024. 4. 25.
	  * @Author : IK.MOON
	  * @Method Brief : 면허 상태 조회 - 단속 리스트 조회 ajax
	  * @param model
	  * @param paramMap
	  * @return
	  */
	@PostMapping("/licencestatus/enf/list.ajax")
	public String licenceStatusEnfListAjax(Model model, MozTfcEnfMaster tfcEnfMaster) {
//		MozTfcEnfMaster tfcEnfMaster = new MozTfcEnfMaster();
		
		int page = tfcEnfMaster.getPage();
		int totalCnt = inquiryService.getLcenEnfCount(tfcEnfMaster);
		Pagination pagination = new Pagination(totalCnt, page);
		
		tfcEnfMaster.setStart((page - 1) * pagination.getPageSize());
		
		model.addAttribute("tfcEnfMaster", tfcEnfMaster);
		model.addAttribute("enfList", inquiryService.getLcenEnfList(tfcEnfMaster));
		model.addAttribute("pagination", pagination);
		
		return "views/inquiry/licenceStatusEnfListAjax";
	}
	
	/**
	  * @Method Name : licenceStatusDetail
	  * @Date : 2024. 4. 25.
	  * @Author : IK.MOON
	  * @Method Brief : 면허 상태 조회 - 단속이력 디테일 페이지
	  * @param model
	  * @return
	  */
	@GetMapping("/licencestatus/detail.do")
	public String licenceStatusDetail(Model model) {

		return "views/inquiry/licneceDetail";
	}

	/**
	  * @Method Name : billPopup
	  * @Date : 2024. 5. 8.
	  * @Author : IK.MOON
	  * @Method Brief :
	  * @param model
	  * @param paramMap
	  * @param request
	  * @return
	  * @throws Exception
	  */
	@RequestMapping("/enfstatus/bill/detail.do")
	public String billPopup(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		
		if (!request.getMethod().equals("POST")) {
			throw new Exception();
		}
		
		ValidateBuilder dtoValidator = new ValidateBuilder(paramMap);
		
		ValidateResult dtoValidateResult = dtoValidator
				.addRule("tfcEnfId", new ValidateChecker().setRequired())
				.addRule("dvrLcenId", new ValidateChecker().setRequired())
				.addRule("vioBrth", new ValidateChecker().setRequired())
				.isValid()
				;
		
		if (!dtoValidateResult.isSuccess()) {
			throw new CommonException(ErrorCode.REQUIRED_FIELDS);
		}
		
		model.addAttribute("fineNtcInfo", inquiryService.getFineNtcInfo(paramMap));
		return "views/inquiry/1stBillDetail";
	}
	
}
