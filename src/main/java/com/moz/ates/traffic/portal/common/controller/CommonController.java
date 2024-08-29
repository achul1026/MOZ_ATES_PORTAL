package com.moz.ates.traffic.portal.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moz.ates.traffic.common.component.FileUploadComponent;
import com.moz.ates.traffic.common.entity.board.MozAtchFile;
import com.moz.ates.traffic.common.entity.common.ApiDriverInfoDTO;
import com.moz.ates.traffic.common.repository.board.MozAtchFileRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import groovyjarjarpicocli.CommandLine.Model;

/**
 * @packageName : com.moz.ates.traffic.portal.common.controller
 * @fileName : CommonController.java
 * @author : NK.KIM
 * @date : 2022.03.10
 * @description : 최초 생성
 */

@Controller
@RequestMapping(value = "/common")
public class CommonController {
	
	@Autowired
	private MozAtchFileRepository mozAtchFileRepository;
	
	@Autowired
	private FileUploadComponent fileUploadComponent;
	
	/**
	 * @Method Name : objectionDatail
	 * @작성일 : 2024. 01. 16.
	 * @작성자 : TY.LEE
	 * @Method 설명 : 이의제기 상세
	 * @param model
	 * @return
	 */
	@GetMapping("/modal/objection/detail")
	public String objectionDetail(Model model) {
		return "views/common/modalobjectionDetail";
	}

	// TODO :: API TEST
	/**
	 * @Method Name : getInformation
	 * @Date : 2024. 4. 24.
	 * @Author : IK.MOON
	 * @Method Brief : API TEST
	 * @param apiDriverInfoDTO
	 * @return
	 */
	@PostMapping("/api/searchDriver")
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> getInformation(
			@RequestBody ApiDriverInfoDTO apiDriverInfoDTO) {

		if (apiDriverInfoDTO == null) {
			throw new CommonException(ErrorCode.INVALID_PARAMETER);
		}

		String searchType = (String) apiDriverInfoDTO.getSearchType();
		String searchValue = (String) apiDriverInfoDTO.getSearchValue();

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		Map<String, Object> result1 = new HashMap<String, Object>();
		result1.put("driverLicenseId", "LCE01245678");
		result1.put("driverLicenseType", "1종 보통");
		result1.put("driverNm", "홍길동");
		result1.put("driverBrth", "1980-01-01");
		result1.put("driverAddr", "서울시 강남구");
		result1.put("driverPno", "010-1234-5678");
		result1.put("driverEmail", "hong@example.com");
		result1.put("expirationDate", "2024-08-07");
		result1.put("vehicleNo", "31바 1111");
		result1.put("vehicleType", "Sonata");

		Map<String, Object> result2 = new HashMap<String, Object>();
		result2.put("driverLicenseId", "123");
		result2.put("driverLicenseType", "C");
		result2.put("driverNm", "링퀴링");
		result2.put("driverBrth", "1994-09-15");
		result2.put("driverAddr", "서울시 마포구");
		result2.put("driverPno", "010-9787-5678");
		result2.put("driverEmail", "ring@example.com");
		result2.put("expirationDate", "2024-10-07");
		result2.put("vehicleNo", "51바 1234");
		result2.put("vehicleType", "porche 911-spider");

		Map<String, Object> result3 = new HashMap<String, Object>();
		result3.put("driverLicenseId", "LCE003827374");
		result3.put("driverLicenseType", "A");
		result3.put("driverNm", "리끼리");
		result3.put("driverBrth", "1999-07-27");
		result3.put("driverAddr", "모잠비크 마푸토");
		result3.put("driverPno", "010-1114-4444");
		result3.put("driverEmail", "lee@example.com");
		result3.put("expirationDate", "2024-05-07");
		result3.put("vehicleNo", "99바 9999");
		result3.put("vehicleType", "Tico");
		
		Map<String, Object> result4 = new HashMap<String, Object>();
		result4.put("driverLicenseId", "1524815");
		result4.put("driverBrth", "1988-08-18");
		result4.put("driverPno", "828152452");
		result4.put("driverAddr", "Avenida Ahmed Sekou Touré 923 R/c, Maputo 1101");
		result4.put("lcenStts", "Active");
		result4.put("validStartDate", "2020-11-02");
		result4.put("expirationDate", "2025-11-02");
		result4.put("driverLicenseType", "C - Veículo Pesado - acima de 16000Kg (PB)");
		result4.put("cartaCt", "-");
		
		switch (searchType) {
			case "birthDtAndLcenId":
				result4.put("driverNm", searchValue);
				resultList.add(result4);
				break;
			case "dvrLcenId":
				result1.put("driverLicenseId", searchValue);
				result2.put("driverLicenseId", searchValue);
				result3.put("driverLicenseId", searchValue);
				resultList.add(result3);
				break;
			case "vehicleNo":
				result1.put("vehicleNo", searchValue);
				result2.put("vehicleNo", searchValue);
				result3.put("vehicleNo", searchValue);
				resultList.add(result1);
				resultList.add(result2);
				resultList.add(result3);
				break;
			case "driverNm":
				result1.put("driverNm", searchValue);
				result2.put("driverNm", searchValue);
				result3.put("driverNm", searchValue);
				resultList.add(result1);
				resultList.add(result2);
				resultList.add(result3);
				break;
			default:
				throw new CommonException(ErrorCode.INVALID_PARAMETER);
		}


		return ResponseEntity.ok(resultList);
	}
	
	/**
	  * @Method Name : fileDownload
	  * @Date : 2024. 5. 13.
	  * @Author : IK.MOON
	  * @Method Brief : 첨부 파일 다운로드
	  * @param fileId
	  * @param response
	  * @throws IOException
	  */
	@GetMapping("/file/download.do")
	public void fileDownload(@RequestParam(value = "fileId", required = true) String fileId, 
			HttpServletResponse response) throws IOException {
		MozAtchFile atchFile = mozAtchFileRepository.findOneMozAtchFileByFileIdx(fileId);
		fileUploadComponent.fileDownload(response
				, atchFile.getFileSaveNm()
				, atchFile.getFileOrgNm()
				, atchFile.getFilePath());
	}
}
