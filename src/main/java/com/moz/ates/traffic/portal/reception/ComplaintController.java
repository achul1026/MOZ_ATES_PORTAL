package com.moz.ates.traffic.portal.reception;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.moz.ates.traffic.common.component.validate.ValidateBuilder;
import com.moz.ates.traffic.common.component.validate.ValidateChecker;
import com.moz.ates.traffic.common.component.validate.ValidateResult;
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
import com.moz.ates.traffic.common.entity.board.MozAtchFile;
import com.moz.ates.traffic.common.entity.board.MozComplaintsReg;
import com.moz.ates.traffic.common.entity.common.CommonResponse;
import com.moz.ates.traffic.portal.common.service.CommonService;
import com.moz.ates.traffic.portal.reception.service.ComplaintService;

/**
 * @packageName : com.moz.ates.traffic.portal.complainmng.controller
 * @fileName : ComplainMngController.java
 * @author : NK.KIM
 * @date : 2022.02.25
 * @description : 최초 생성
 */
@Controller
@RequestMapping("/complain")
public class ComplaintController {

	@Autowired
	private ComplaintService complaintService;

	@Autowired
	private CommonService commonService;

	/**
	  * @Method Name : complaintInfo
	  * @Date : 2024. 3. 29.
	  * @Author : IK.MOON
	  * @Method Brief : 민원 리스트 페이지
	  * @param model
	  * @param mozComplaintsReg
	  * @return
	  */
	@GetMapping("/list.do")
	public String complaintInfo(Model model, MozComplaintsReg mozComplaintsReg) {
		
		int page = mozComplaintsReg.getPage();
		int totalCnt = complaintService.countComplaints(mozComplaintsReg);
		Pagination pagination = new Pagination(totalCnt, page);
		
		mozComplaintsReg.setStart((page - 1) * pagination.getPageSize());
		
		model.addAttribute("complaintsList", complaintService.getComplaintsList(mozComplaintsReg));
		model.addAttribute("complaintsReg", mozComplaintsReg);
		model.addAttribute("pagination", pagination);
		
		// 카테고리 코드 조회
		model.addAttribute("complaintsCate", commonService.getCdList("COMPLAINT_CATE_CD"));

		return "views/reception/complaintList";
	}

	/**
	 * @Method Name : complaintRegister
	 * @작성일 : 2024. 01. 16.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 컴플레인 등록
	 * @param model
	 * @return
	 */
	@GetMapping("/save.do")
	public String complaintRegister(Model model) {
		
		// 카테고리 코드 조회
		model.addAttribute("complaintsCate", commonService.getCdList("COMPLAINT_CATE_CD"));
		return "views/reception/complaintRegister";
	}

	/**
	  * @Method Name : complaintResgisterAjax
	  * @Date : 2024. 3. 29.
	  * @Author : IK.MOON
	  * @Method Brief : 민원 등록 Ajax
	  * @param mozComplaintsReg
	  * @return
	  */
	@PostMapping("/save.ajax")
	@ResponseBody
	public CommonResponse<?> complaintResgisterAjax(
			MozComplaintsReg mozComplaintsReg,
			@RequestParam(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {

		ValidateBuilder dtoValidator = new ValidateBuilder(mozComplaintsReg);
		ValidateResult validationResult = dtoValidator
				.addRule("wrtrNm", new ValidateChecker()
						.setRequired().setMinLength(3).setMaxLength(50))
				.addRule("wrtrPno", new ValidateChecker()
						.setRequired().setMinLength(5).setMaxLength(15))
				.addRule("postTtl", new ValidateChecker()
						.setRequired().setMinLength(5).setMaxLength(150))
				.addRule("postPw", new ValidateChecker()
						.setRequired().setMinLength(4).setMaxLength(10))
				.addRule("cateCd", new ValidateChecker().setRequired())
				.addRule("postCn", new ValidateChecker()
						.setRequired().setMinLength(5).setMaxLength(1000))
				.isValid()
				;
		if (!validationResult.isSuccess()) {
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST, validationResult.getMessage());
		}

		try {
			complaintService.registComplaints(mozComplaintsReg, uploadFiles);
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
		
		if (!complaintService.checkPw(mozComplaintsReg)) {
			// 비밀번호를 확인해 주세요
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST, "Por favor verifique sua senha");
		}
		
		return CommonResponse.ResponseSuccess(HttpStatus.OK, "Good","/complain/detail.do",mozComplaintsReg.getComplaintsIdx());
	}
	
	/**
	 * @Method Name : complaintResult
	 * @작성일 : 2024. 01. 16.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 컴플레인 상세
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/detail.do")
	public String complaintResult(Model model, MozComplaintsReg mozComplaintsReg, HttpServletRequest request) throws Exception {
		if (!request.getMethod().equals("POST")) {
			throw new Exception();
		}
		model.addAttribute("complaintsDetail", complaintService.getComplaintsDetail(mozComplaintsReg));
		
		return "views/reception/complaintDetail";
	}

	/**
	 * @Method Name : complaintModify
	 * @작성일 : 2024. 01. 16.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 컴플레인 수정 페이지
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/update.do")
	public String complaintModify(Model model, MozComplaintsReg mozComplaintsReg, HttpServletRequest request) throws Exception {
		if (!request.getMethod().equals("POST")) {
			throw new Exception();
		}
		
		MozComplaintsReg complaintsReg = complaintService.getComplaintsDetail(mozComplaintsReg);
		
		List<String> oldFileArr = complaintsReg.getQstAtchFileList().stream()
                .map(MozAtchFile::getFileOrgNm)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
		
		model.addAttribute("complaintsDetail", complaintsReg);
		model.addAttribute("oldFileArr", oldFileArr);
		// 카테고리 코드 조회
		model.addAttribute("complaintsCate", commonService.getCdList("COMPLAINT_CATE_CD"));
		return "views/reception/complaintModify";
	}
	
	/**
	  * @Method Name : updateComplaint
	  * @Date : 2024. 5. 23.
	  * @Author : IK.MOON
	  * @Method Brief : 컴플레인 수정 ajax
	  * @param mozComplaintsReg
	  * @param uploadFiles
	  * @return
	  */
	@PostMapping("/update.ajax")
	@ResponseBody
	public CommonResponse<?> updateComplaint(MozComplaintsReg mozComplaintsReg,
			@RequestParam(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {
		ValidateBuilder dtoValidator = new ValidateBuilder(mozComplaintsReg);
		ValidateResult validationResult = dtoValidator
				.addRule("complaintsIdx", new ValidateChecker().setRequired())
				.addRule("wrtrNm", new ValidateChecker()
						.setRequired().setMinLength(3).setMaxLength(50))
				.addRule("wrtrPno", new ValidateChecker()
						.setRequired().setMinLength(5).setMaxLength(15))
				.addRule("postTtl", new ValidateChecker()
						.setRequired().setMinLength(5).setMaxLength(150))
				.addRule("postPw", new ValidateChecker()
						.setRequired().setMinLength(4).setMaxLength(10))
				.addRule("cateCd", new ValidateChecker().setRequired())
				.addRule("postCn", new ValidateChecker()
						.setRequired().setMinLength(5).setMaxLength(1000))
				.isValid()
				;
		if (!validationResult.isSuccess()) {
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST, validationResult.getMessage());
		}
		// 문의에 대한 답변이 이미 등록되어 있습니다.
		if (complaintService.checkAnsStts(mozComplaintsReg).equals("Y")) {
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST, "Uma resposta à sua pergunta já foi registrada.");
		}
		
		try {
			complaintService.updateComplaints(mozComplaintsReg, uploadFiles);
			
			// 성공적으로 등록되었습니다.
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.OK, "Você foi registrado com sucesso.");
		} catch (Exception e) {
			// 성공적으로 등록되지 않았습니다
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST,"Não foi registrado com sucesso.");
		}
		
		
	}
	
	/**
	  * @Method Name : deleteComplaint
	  * @Date : 2024. 5. 23.
	  * @Author : IK.MOON
	  * @Method Brief : 민원 삭제
	  * @param mozComplaintsReg
	  * @return
	  */
	@PostMapping("/delete.ajax")
	@ResponseBody
	public CommonResponse<?> deleteComplaint(MozComplaintsReg mozComplaintsReg) {
		// 문의에 대한 답변이 이미 등록되어 있습니다.
		if (complaintService.checkAnsStts(mozComplaintsReg).equals("Y")) {
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST, "Uma resposta à sua pergunta já foi registrada.");
		}
		
		try {
			complaintService.deleteComplaints(mozComplaintsReg);
			
			// 성공적으로 삭제 되었습니다.
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.OK, "Apagado com sucesso.");
		} catch (Exception e) {
			// 성공적으로 삭제되지 않았습니다.
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST,"Não foi excluído com sucesso.");
		}
		
		
	}
}
