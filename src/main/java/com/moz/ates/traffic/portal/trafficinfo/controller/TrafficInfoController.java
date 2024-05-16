package com.moz.ates.traffic.portal.trafficinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.moz.ates.traffic.common.component.Pagination;
import com.moz.ates.traffic.common.entity.board.MozTfcSftyEdctn;
import com.moz.ates.traffic.common.entity.board.MozTfcSftyInfrm;
import com.moz.ates.traffic.common.entity.law.MozTfcLwInfo;
import com.moz.ates.traffic.portal.trafficinfo.service.TrafficLawInfoService;
import com.moz.ates.traffic.portal.trafficinfo.service.TrafficSafetyInfoService;

@Controller
@RequestMapping("/traffic/info")
public class TrafficInfoController {

	@Autowired
	private TrafficSafetyInfoService trafficSafetyInfoService;
	
	@Autowired
	private TrafficLawInfoService trafficLawInfoService;

	/**
	  * @Method Name : trafficSafeInfo
	  * @Date : 2024. 3. 27.
	  * @Author : IK.MOON
	  * @Method Brief : 교통 안전 정보 리스트 페이지
	  * @param model
	  * @return
	  */
	@GetMapping("/safeinfo/list.do")
	public String trafficSafeInfo(Model model, MozTfcSftyInfrm mozTfcSftyInfrm) {
		int page = mozTfcSftyInfrm.getPage();
		int totalCnt = trafficSafetyInfoService.getTfcSftyInfrmCount(mozTfcSftyInfrm);
		Pagination pagination = new Pagination(totalCnt, page);
		
		mozTfcSftyInfrm.setStart((page - 1) * pagination.getPageSize());
		
		model.addAttribute("tfcSftyInfrm", mozTfcSftyInfrm);
		model.addAttribute("tfcSftyInfrmList", trafficSafetyInfoService.getTfcSftyInfrmList(mozTfcSftyInfrm));
		model.addAttribute("pagination", pagination);
		
		
		return "views/trafficinfo/trafficSafeInfoList";
	}

	/**
	  * @Method Name : trafficSafeDetail
	  * @Date : 2024. 3. 27.
	  * @Author : IK.MOON
	  * @Method Brief : 교통안전정보 상세
	  * @param model
	  * @param mozTfcSftyInfrm
	  * @return
	  */
	@GetMapping("/safeinfo/detail.do")
	public String trafficSafeDetail(Model model, MozTfcSftyInfrm mozTfcSftyInfrm) {
		
		model.addAttribute("tfcSftyInfrmDetail", trafficSafetyInfoService.getTfcSftyInfrmDetail(mozTfcSftyInfrm));
		
		return "views/trafficinfo/trafficSafeInfoDetail";
	}
	
	/**
	  * @Method Name : trafficSafeEduList
	  * @Date : 2024. 3. 27.
	  * @Author : IK.MOON
	  * @Method Brief : 교통안전교육 리스트
	  * @param model
	  * @param mozTfcSftyEdctn
	  * @return
	  */
	@GetMapping("/edu/list.do")
	public String trafficSafeEduList(Model model, MozTfcSftyEdctn mozTfcSftyEdctn) {
		int page = mozTfcSftyEdctn.getPage();
		int totalCnt = trafficSafetyInfoService.getTfcSftyEdctnCount(mozTfcSftyEdctn);
		Pagination pagination = new Pagination(totalCnt, page);
		
		mozTfcSftyEdctn.setStart((page - 1) * pagination.getPageSize());
		
		model.addAttribute("tfcSftyEdctn", mozTfcSftyEdctn);
		model.addAttribute("tfcSftyEdctnList", trafficSafetyInfoService.getTfcSftyEdctnList(mozTfcSftyEdctn));
		model.addAttribute("pagination", pagination);
		
		return "views/trafficinfo/trafficSafeEduList";
	}

	/**
	  * @Method Name : trafficSafeEduDetail
	  * @Date : 2024. 3. 27.
	  * @Author : IK.MOON
	  * @Method Brief : 교통안전교육 상세
	  * @param model
	  * @param mozTfcSftyEdctn
	  * @return
	  */
	@GetMapping("/edu/detail.do")
	public String trafficSafeEduDetail(Model model, MozTfcSftyEdctn mozTfcSftyEdctn) {
		
		model.addAttribute("tfcSftyEdctnDetail", trafficSafetyInfoService.getTfcSftyEdctnDetail(mozTfcSftyEdctn));
		
		return "views/trafficinfo/trafficSafeEduDetail";
	}
	
	
	
	/**
	 * @Method Name : trafficLawInfo
	 * @Date : 2024. 1. 17.
	 * @Author : IK.MOON
	 * @Method Brief : 교통법규정보 화면
	 * @param model
	 * @return
	 */
	@GetMapping("/lawinfo/list.do")
	public String trafficLawInfo(Model model, MozTfcLwInfo tfcLwInfo) {
		
		int page = tfcLwInfo.getPage();
		int totalCnt = trafficLawInfoService.getTrafficLawCount(tfcLwInfo);
		Pagination pagination = new Pagination(totalCnt, page);
		
		tfcLwInfo.setStart((page - 1) * pagination.getPageSize());
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("tfcLwInfo", tfcLwInfo);
		model.addAttribute("tfcLwInfoList", trafficLawInfoService.getTrafficLawList(tfcLwInfo));
		
		return "views/trafficinfo/trafficLawList";
	}

	/**
	 * @Method Name : trafficLawInfoDetail
	 * @Date : 2024. 1. 17.
	 * @Author : TY.LEE
	 * @Method Brief : 교통법규 상세
	 * @param model
	 * @return
	 */
	@GetMapping("/lawinfo/detail.do")
	public String trafficLawInfoDetail(Model model, MozTfcLwInfo tfcLwInfo) {
		
		model.addAttribute("tfcLwInfoDetail", trafficLawInfoService.getTrafficLawDetail(tfcLwInfo));
		
		return "views/trafficinfo/trafficLawDetail";
	}

	/**
	  * @Method Name : statistics
	  * @Date : 2024. 5. 8.
	  * @Author : IK.MOON
	  * @Method Brief : 통계 페이지
	  * @return
	  */
	@GetMapping("/stat")
	public String statistics(Model model) {
		return "views/trafficinfo/statisticsAnalyze";
	}
}
