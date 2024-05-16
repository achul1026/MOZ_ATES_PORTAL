package com.moz.ates.traffic.portal.contactus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.moz.ates.traffic.common.entity.board.MozBrd;
import com.moz.ates.traffic.common.entity.board.MozFaq;
import com.moz.ates.traffic.common.entity.board.MozInqry;
import com.moz.ates.traffic.common.entity.common.CommonResponse;
import com.moz.ates.traffic.common.entity.common.MozCmCd;
import com.moz.ates.traffic.common.entity.payment.MozPlPymntInfo;
import com.moz.ates.traffic.common.repository.common.MozCmCdRepository;
import com.moz.ates.traffic.portal.contactus.service.FaqService;
import com.moz.ates.traffic.portal.contactus.service.NoticeService;
import com.moz.ates.traffic.portal.contactus.service.PenaltyPlaceService;
import com.moz.ates.traffic.portal.contactus.service.QnaService;

@Controller
@RequestMapping(value = "/info")
public class ContactUsController {

	@Autowired
	private FaqService faqService;
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private PenaltyPlaceService penaltyPlaceService;
	
	@Autowired
	private QnaService qnaService;
	
	@Autowired
	private MozCmCdRepository mozCmCdRepository;

	/**
	  * @Method Name : noticeList
	  * @Date : 2024. 2. 19.
	  * @Author : IK.MOON
	  * @Method Brief : 공지사항 리스트 화면
	  * @param model
	  * @return
	  */
	@GetMapping("/notice/list.do")
	public String noticeList(Model model, MozBrd mozBrd) {
		int page = mozBrd.getPage();
		int totalCnt = noticeService.getNoticeListCount(mozBrd);
		Pagination pagination = new Pagination(totalCnt, page);
		
		mozBrd.setStart((page - 1) * pagination.getPageSize());
		
		model.addAttribute("mozBrd", mozBrd);
		model.addAttribute("noticeList", noticeService.getNoticeList(mozBrd));
		model.addAttribute("pagination", pagination);
		
		return "views/contactus/noticeList";
	}

	/**
	 * @Method Name : noticeDetail
	 * @작성일 : 2024. 01. 17.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 공지사항 상세
	 * @param model
	 * @return
	 */
	@GetMapping("/notice/detail.do")
	public String noticeDetail(Model model, @RequestParam(name="boardIdx", required = true) String boardIdx) {
		
		model.addAttribute("noticeDetail", noticeService.getNoticeDetail(boardIdx));
		return "views/contactus/noticeDetail";
	}	

	/**
	  * @Method Name : faqList
	  * @Date : 2024. 2. 21.
	  * @Author : IK.MOON
	  * @Method Brief : FAQ 리스트 화면
	  * @param model
	  * @param paramMap
	  * @return
	  */
	@GetMapping("/faq/list.do")
	public String faqList(Model model, MozFaq mozFaq) {
		int page = mozFaq.getPage();
		int totalCnt = faqService.getFaqCount(mozFaq);
		Pagination pagination = new Pagination(totalCnt, page);
		
		mozFaq.setStart((page - 1) * pagination.getPageSize());
		
		model.addAttribute("mozFaq", mozFaq);
		model.addAttribute("pagination", pagination);
		model.addAttribute("faqList", faqService.getFaqList(mozFaq));

		return "views/contactus/faqList";
	}

	/**
	 * @Method Name : faqDetail
	 * @작성일 : 2024. 01. 17.
	 * @작성자 : TY.LEE
	 * @Method 설명 : FAQ 상세
	 * @param model
	 * @return
	 */
	@GetMapping("/faq/detail.do")
	public String faqDetail(Model model, MozFaq mozFaq) {

		model.addAttribute("faqDetail", faqService.getFaqDetail(mozFaq));
		
		return "views/contactus/faqDetail";
	}

	/**
	  * @Method Name : qnaList
	  * @Date : 2024. 3. 4.
	  * @Author : IK.MOON
	  * @Method Brief : QnA 리스트 페이지
	  * @param model
	  * @return
	  */
	@GetMapping("/qna/list.do")
	public String qnaList(Model model, MozInqry mozInqry) {
		List<MozCmCd> cateCdList = mozCmCdRepository.findAllSubCmcd("INQUIRY_TYPE_CD");
		cateCdList.removeIf(x -> x.getUseYn().equals("N"));
		
		int page = mozInqry.getPage();
		int totalCnt = qnaService.getQnaListCount(mozInqry);
		Pagination pagination = new Pagination(totalCnt, page);
		
		mozInqry.setStart((page - 1) * pagination.getPageSize());
		
		model.addAttribute("cateCdList", cateCdList);
		model.addAttribute("mozInqryInfo", mozInqry);
		model.addAttribute("pagination", pagination);
		model.addAttribute("mozInqryList", qnaService.getQnaList(mozInqry));
		return "views/contactus/qnaList";
	}
	
	/**
	 * @Method Name : qnaRegister
	 * @작성일 : 2024. 01. 17.
	 * @작성자 : TY.LEE
	 * @Method 설명 : QnA 신청 페이지
	 * @param model
	 * @return
	 */
	@GetMapping("/qna/save.do")
	public String qnaRegister(Model model) {
		List<MozCmCd> cateCdList = mozCmCdRepository.findAllSubCmcd("INQUIRY_TYPE_CD");
		// TODO :: removeIf 모두 삭제 필요 useYn: N 조건으로 찾아와야 함
 		cateCdList.removeIf(x -> x.getUseYn().equals("N"));
		model.addAttribute("cateCdList", cateCdList);
		return "views/contactus/qnaRegister";
	}
	
	/**
	  * @Method Name : qnaRegister
	  * @작성일 : 2024. 3. 8.
	  * @작성자 : SM.KIM
	  * @Method 설명 : QnA 신청
	  * @param mozInqry
	  * @param uploadFiles
	  * @return
	  */
	@PostMapping("/qna/save.ajax")
	@ResponseBody
	public CommonResponse<?> qnaRegister(
			MozInqry mozInqry
			, @RequestParam(name="uploadFiles",required = false) MultipartFile[] uploadFiles) {
		try {
			qnaService.save(mozInqry, uploadFiles);
			// 성공적으로 등록되었습니다.
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.OK, "Você foi registrado com sucesso.");
			
		} catch (Exception e) {
			// 성공적으로 등록되지 않았습니다
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST, "Não foi registrado com sucesso.");
		}
	}
	
	/**
	 * @Method Name : qnaDetail
	 * @작성일 : 2024. 01. 17.
	 * @작성자 : TY.LEE
	 * @Method 설명 : QnA 상세 페이지
	 * @param model
	 * @return
	 */
	@GetMapping("/qna/detail.do")
	public String qnaDetail(Model model, MozInqry mozInqry) {
		// TODO:: 비밀번호 검증로직 필요
		model.addAttribute("qnaDetail", qnaService.getQnaDetail(mozInqry.getInqryId()));
		return "views/contactus/qnaDetail";
	}
	
	/**
	  * @Method Name : qnaDetail
	  * @작성일 : 2024. 3. 8.
	  * @작성자 : SM.KIM
	  * @Method 설명 : QnA 상세보기 위한 비밀번호 검증
	  * @param mozInqry
	  * @return
	  */
	@PostMapping("/qna/detail.ajax")
	public @ResponseBody CommonResponse<?> qnaDetail(MozInqry mozInqry) {
				
		Boolean isVaild = qnaService.isValidatePostPw(mozInqry);
		if (isVaild) {
			
			return CommonResponse.ResponseCodeAndMessage(HttpStatus.OK,"");
		}
		// 비밀번호를 확인해 주세요
		return CommonResponse.ResponseCodeAndMessage(HttpStatus.BAD_REQUEST, "Por favor verifique sua senha");
	}
	
	/**
	  * @Method Name : qnaDelete
	  * @작성일 : 2024. 3. 11.
	  * @작성자 : SM.KIM
	  * @Method 설명 : 문의하기 삭제
	  * @param model
	  * @param mozInqry
	  * @return
	  */
	@PostMapping("/qna/delete.ajax")
	public @ResponseBody CommonResponse<?> qnaDelete(Model model, MozInqry mozInqry) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			qnaService.delete(mozInqry);
			
			result.put("code", 200);
			result.put("message", "QnA deletion completed");
			return CommonResponse.successToData(result,"");
			
		} catch (Exception e) {
			return CommonResponse.ResponseCodeAndMessage(401,"QnA deletion failed");
		}
	}

	/**
	  * @Method Name : penaltyPlaceList
	  * @Date : 2024. 2. 26.
	  * @Author : IK.MOON
	  * @Method Brief : 범칙금 납부처 리스트 화면
	  * @param model
	  * @param plPymntInfo
	  * @return
	  */
	@GetMapping("/penaltyplace/list.do")
	public String penaltyPlaceList(Model model, MozPlPymntInfo plPymntInfo) {
		plPymntInfo.setSearchType("all");
		
		int page = plPymntInfo.getPage();
		int totalCnt = penaltyPlaceService.getPenaltyPlaceListCount(plPymntInfo);
		Pagination pagination = new Pagination(totalCnt, page);
		
		plPymntInfo.setStart((page - 1) * pagination.getPageSize());
		
		model.addAttribute("plPymntInfo", plPymntInfo);
		model.addAttribute("pagination", pagination);
		model.addAttribute("plPymntList", penaltyPlaceService.getPenaltyPlaceList(plPymntInfo));
		
		
		return "views/contactus/penaltyPlaceList";
	}

	/**
	  * @Method Name : penaltyplaceDetail
	  * @Date : 2024. 2. 26.
	  * @Author : IK.MOON
	  * @Method Brief : 범칙금 납부처 상세 화면
	  * @param model
	  * @param plPymntInfo
	  * @return
	  */
	@GetMapping("/penaltyplace/detail.do")
	public String penaltyplaceDetail(Model model, MozPlPymntInfo plPymntInfo) {

		model.addAttribute("plPymntDetail", penaltyPlaceService.getPenaltyPlaceDetail(plPymntInfo));
		
		return "views/contactus/penaltyplaceDetail";
	}
	
}
