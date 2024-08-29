package com.moz.ates.traffic.portal.inquiry;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moz.ates.traffic.common.entity.equipment.MozCameraEnfOrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moz.ates.traffic.common.component.FileUploadComponent;
import com.moz.ates.traffic.common.component.Pagination;
import com.moz.ates.traffic.common.component.validate.ValidateBuilder;
import com.moz.ates.traffic.common.component.validate.ValidateChecker;
import com.moz.ates.traffic.common.component.validate.ValidateResult;
import com.moz.ates.traffic.common.entity.common.CommonResponse;
import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;
import com.moz.ates.traffic.common.entity.equipment.MozTfcEnfFileInfo;
import com.moz.ates.traffic.common.entity.equipment.MozTfcEnfFineInfo;
import com.moz.ates.traffic.common.entity.finentc.MozFineNtcInfo;
import com.moz.ates.traffic.common.repository.equipment.MozTfcEnfFileInfoRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.portal.inquiry.service.InquiryService;

@Controller
@RequestMapping(value = "/inquiry")
public class InquiryController {

	@Autowired
	private InquiryService inquiryService;

	@Autowired
	private FileUploadComponent fileUploadComponent;
	
	@Autowired
	MozTfcEnfFileInfoRepository mozTfcEnfFileInfoRepository;
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
	 * @throws Exception 
	  */
	@RequestMapping("/enfstatus/detail.do")
	public String enfStatusDetail(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		if (!request.getMethod().equals("POST")) {
			throw new Exception();
		}
		
		MozTfcEnfMaster tfcEnfStatusDetail = inquiryService.getEnfStatusDetail(paramMap);
		List<MozTfcEnfFineInfo> fineList = inquiryService.getAllTfcEnfFineInfo(paramMap);
		
		model.addAttribute("paramMap", paramMap);
		model.addAttribute("tfcEnfStatusDetail", tfcEnfStatusDetail);
		model.addAttribute("fineList", fineList);
		return "views/inquiry/enfStatusDetail";
	}

	/**
	  * @Method Name : tfcEnfImgView
	  * @Date : 2024. 5. 20.
	  * @Author : IK.MOON
	  * @Method Brief : 단속 이미지 요청
	  * @param response
	  * @param fileId
	  * @throws IOException
	  */
	@GetMapping("/enf/image.do")
	public void tfcEnfImgView(HttpServletResponse response,
			@RequestParam(name = "fileId" ,required = true) String fileId
			) throws IOException {
		MozTfcEnfFileInfo tfcEnfFileInfo = mozTfcEnfFileInfoRepository.findOneByMozTfcEnfFileInfoByVioFileId(fileId);
		fileUploadComponent.imgView(response, tfcEnfFileInfo.getFilePath());
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
	 * @throws Exception 
	  */
	@RequestMapping("/pymntstatus/detail.do")
	public String pymntStatusDetail(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		if (!request.getMethod().equals("POST")) {
			throw new Exception();
		}
		MozTfcEnfMaster pymntStatusDetail = inquiryService.getPymntSatusDetail(paramMap);
		List<MozTfcEnfFineInfo> fineList = inquiryService.getAllTfcEnfFineInfo(paramMap);
		
		model.addAttribute("pymntStatusDetail", pymntStatusDetail);
		model.addAttribute("fineList", fineList);
		
		return "views/inquiry/pymntStatusDetail";
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
	 * @throws Exception 
	  */
	@RequestMapping("/licencestatus/detail.do")
	public String licenceStatusDetail(Model model, @RequestParam Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
		if (!request.getMethod().equals("POST")) {
			throw new Exception();
		}
		MozTfcEnfMaster tfcEnfStatusDetail = inquiryService.getEnfStatusDetail(paramMap);
		List<MozTfcEnfFineInfo> fineList = inquiryService.getAllTfcEnfFineInfo(paramMap);
		
//		model.addAttribute("paramMap", paramMap);
		model.addAttribute("tfcEnfStatusDetail", tfcEnfStatusDetail);
		model.addAttribute("fineList", fineList);
		return "views/inquiry/licneceDetail";
	}

	/**
	  * @Method Name : billPopup
	  * @Date : 2024. 5. 8.
	  * @Author : IK.MOON
	  * @Method Brief : 1차 고지서 조회
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

	/**
	 * 2024.07.31
	 * 단속 카메라 감지 내역 리스트 페이지
	 * @param model
	 * @param cameraEnfOrg
	 * @return
	 */
	@GetMapping("/detection/list.do")
	public String detectionList(Model model, MozCameraEnfOrg cameraEnfOrg) {
		int page = cameraEnfOrg.getPage();
		int totalCnt = inquiryService.getDetectionCount(cameraEnfOrg);

		Pagination pagination = new Pagination(totalCnt, page);
		cameraEnfOrg.setStart((page - 1) * pagination.getPageSize());

		model.addAttribute("cameraEnfOrg", cameraEnfOrg);
		model.addAttribute("pagination", pagination);
		model.addAttribute("detectionList", inquiryService.getDetectionList(cameraEnfOrg));

		return "views/inquiry/detectionList";
	}

	/**
	 * 2024.07.31
	 * 단속 카메라 감지 내역 상세 페이지
	 * @param model
	 * @param cameraEnfOrg
	 * @return
	 */
	@GetMapping("/detection/detail.do")
	public String detectionDetail(Model model, MozCameraEnfOrg cameraEnfOrg) {
		try {
			model.addAttribute("detectionDetail", inquiryService.getDetectionDetail(cameraEnfOrg));
			model.addAttribute("detectionImgList", inquiryService.getDetectionImgList(cameraEnfOrg));
		}catch (CommonException e) {
			return "views/common/ErrorPage";
		}
		return "views/inquiry/detectionDetail";
	}

	/**
	 * 2024.08.01
	 * 단속 카메라 감지 내역 상세 - 이미지 요청
	 * @param response
	 * @param idx
	 * @throws IOException
	 */
	@GetMapping("/detection/image.do")
	public void detectionImgView(HttpServletResponse response,
							  @RequestParam(name = "fileId" ,required = true) Long idx
	) throws IOException {
		String filePath = inquiryService.getDetectionImgPath(idx);
		fileUploadComponent.imgView(response, filePath);
	}
}
