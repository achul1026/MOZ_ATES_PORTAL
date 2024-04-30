package com.moz.ates.traffic.portal.inquiry.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moz.ates.traffic.common.entity.enforcement.MozTfcEnfMaster;
import com.moz.ates.traffic.portal.common.service.CommonService;
import com.moz.ates.traffic.portal.inquiry.service.PaymentMngService;

/**
 * @packageName : com.moz.ates.traffic.portal.paymentmng.controller
 * @fileName : PaymentMngController.java
 * @author : NK.KIM
 * @date : 2022.02.25
 * @description : 최초 생성
 */
@Controller
@RequestMapping(value = "/paymentmng")
public class PaymentMngController {

	@Autowired
	private PaymentMngService paymentMngService;

	@Autowired
	private CommonService commonService;

	/**
	 * @Method : paymentInfo
	 * @Author : NK.KIM
	 * @Return : String
	 * @Description : 결제 정보 확인 화면
	 * 
	 *              <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 *              </PRE>
	 */
	@RequestMapping(value = "/paymentCnfrm")
	public String paymentInfo(Model model) {

		return "views/inquiry/paymentCnfrm";
	}

	/**
	 * @Method : paymentInfoRegister
	 * @Author : NK.KIM
	 * @Return : String
	 * @Description : 결제 정보 입력 화면
	 * 
	 *              <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 *              </PRE>
	 */
	@RequestMapping(value = "/paymentInfoRegister", method = RequestMethod.POST)
	public String paymentInfoRegister(Model model, @RequestParam Map<String, Object> paramMap,
			RedirectAttributes redirectAttributes) {

		// return default page
		String returnPage = "views/inquiry/paymentInfoRegister";

		// pymntStts : N(미결제) Y(결제)
		paramMap.put("pymntStts", "N");

		// 결제 상세 정보
		MozTfcEnfMaster paymentInfo = paymentMngService.getPaymentInfo(paramMap);

		// 결제 방법 목록
		// paramMap.put("cdGroupId", "pay");
		// List<MozCmCd> payMthdList = commonService.getCommonCdList(paramMap);

		// 결제 정보 없을 시 결제 정보 확인 페이지로 이동
		if (paymentInfo == null) {
			redirectAttributes.addFlashAttribute("resultMsg",
					"payment information was not found. Please check your license ID and name.");
			returnPage = "redirect:/paymentmng/paymentCnfrm";
		}

		model.addAttribute("paymentInfo", paymentInfo);
		// model.addAttribute("payMthdList",payMthdList);

		return returnPage;
	}

	/**
	 * @Method : paymentInfoCmplt
	 * @Author : NK.KIM
	 * @Return : String
	 * @Description : 결제 정보 완료 화면
	 * 
	 *              <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 *              </PRE>
	 */
	@RequestMapping(value = "/paymentInfoCmplt")
	public String paymentInfoCmplt(Model model, @RequestParam Map<String, Object> paramMap,
			RedirectAttributes redirectAttributes) {

		// return default page
		String returnPage = "redirect:/inquiry/paymentCnfrm";

		try {

			// 결제 정보 수정 (paymntStts N -> Y / 결제방법 )
			paymentMngService.modifyPaymentInfo(paramMap);

			paramMap.put("pymntStts", "Y");
			// 결제 완료 된 결제정보 조회
			MozTfcEnfMaster paymentInfo = paymentMngService.getPaymentInfo(paramMap);

			model.addAttribute("paymentInfo", paymentInfo);
			model.addAttribute("resultMsg", "The payment information registration was successful.");
			returnPage = "views/paymentmng/paymentInfoCmplt";

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("resultMsg",
					"The payment information registration was failed.");
		}

		return returnPage;
	}
}
