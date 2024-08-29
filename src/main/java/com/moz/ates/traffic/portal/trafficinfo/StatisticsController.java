package com.moz.ates.traffic.portal.trafficinfo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moz.ates.traffic.common.entity.common.ChartDTO;
import com.moz.ates.traffic.common.entity.common.ChartDTO.AccidentChartGraph;
import com.moz.ates.traffic.common.entity.common.ChartDTO.AccidentTotalChart;
import com.moz.ates.traffic.common.entity.common.ChartDTO.AccidentTotalVictimsChart;
import com.moz.ates.traffic.common.entity.common.ChartDTO.AccidentTypeTotalChart;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.portal.common.FeaturesLayerDTO;
import com.moz.ates.traffic.portal.trafficinfo.service.StatisticsService;

@Controller
@RequestMapping("/stat")
public class StatisticsController {

	@Autowired
	private StatisticsService statisticsService;

	/**
	 * @Method Name : statistics
	 * @Date : 2024. 5. 8.
	 * @Author : IK.MOON
	 * @Method Brief : 통계 페이지
	 * @return
	 */
	@GetMapping("")
	public String statistics(Model model) {
		return "views/trafficinfo/statisticsAnalyze";
	}

	/**
	 * @Method Name : getFacilityGeoJson
	 * @Date : 2024. 5. 20.
	 * @Author : IK.MOON
	 * @Method Brief : map 시설물 정보 조회
	 * @param param
	 * @return
	 */
	@GetMapping("/mng/facMngGeojson.ajax")
	public @ResponseBody FeaturesLayerDTO getFacilityGeoJson(Map<String, String> param) {
		return statisticsService.getFacilityGeoJson(param);
	}

	/**
	 * 유관기관 map 조회
	 * @param param
	 * @return
	 */
	@GetMapping(value = "/mng/orgnzMngGeojson.ajax")
	public @ResponseBody
	FeaturesLayerDTO getOrganizationGeoJson(Map<String,String> param) {
		return statisticsService.getOrganizationGeoJson(param);
	}

    /**
      * @Method Name : getEquipmentSource
      * @Date : 2024. 7. 10.
      * @Author : IK.MOON
      * @Method Brief : map 단속 카메라 정보 조회
      * @param param
      * @return
      */
    @GetMapping("/mng/eqpMngGeojson.ajax")
    public @ResponseBody FeaturesLayerDTO getEquipmentSource(@RequestParam Map<String,String> param){
		param.put("eqpTy", "EQT001");
		param.put("useYn", "Y");
    	return statisticsService.getEquipmentSource(param);
    }
    
	/**
	 * @brief 통계 차트 조회
	 * @author IK.MOON
	 * @date 2024. 5. 23.
	 * @method getAccidentChartData
	 */
	@PostMapping("/{type}/chart.ajax")
	@ResponseBody
	public ResponseEntity<?> getAccidentChartData(
			@PathVariable("type") String type,
			@RequestParam(name = "startDate", required = true) String startDate,
			@RequestParam(name = "endDate", required = true) String endDate) {
		
		if (MozatesCommonUtils.isNull(startDate) || MozatesCommonUtils.isNull(endDate)) {
			// 시작 날짜와 종료 날짜가 필요합니다.
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Preciso de uma data de início e uma data de término.");
		}
		
		LocalDate startDateLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate endDateLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		ChartDTO chartDTO = new ChartDTO();
		if (!startDateLocalDate.isBefore(endDateLocalDate)) {
			// 시작 날짜는 종료 날짜보다 크거나 같을 수 없습니다
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início não pode ser maior ou igual à data de término.");
		} 
		
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		
		switch(type) {
			case "acdnt":
				AccidentTotalChart accidentTotalChart = statisticsService.getAccidentTotalChart(paramMap);
				chartDTO.setAccidentTotalChart(accidentTotalChart);
				break;
			case "type":
				AccidentChartGraph accidentChartGraph = statisticsService.getAccidentTotalByTypeChart(paramMap);
				chartDTO.setAccidentChartGraph(accidentChartGraph);
				break;
			case "level":
				AccidentTypeTotalChart accidentTypeTotalChart = statisticsService.getAccidentTypeTotalChart(paramMap);
				chartDTO.setAccidentTypeTotalChart(accidentTypeTotalChart);
				break;
			case "passenger":
				AccidentTotalVictimsChart accidentTotalVictimsChart = statisticsService.getAccidentTotalVictimsChart(paramMap);
				chartDTO.setAccidentTotalVictimsChart(accidentTotalVictimsChart);
				break;
			default :
				// 통계 타입 선택이 잘못 되었습니다.
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A seleção do tipo de estatística está incorreta.");
		}
		return ResponseEntity.ok(chartDTO);
	}

	/**
	  * @Method Name : getAccidentSource
	  * @Date : 2024. 5. 21.
	  * @Author : IK.MOON
	  * @Method Brief : map 사고 위치 조회
	  * @param param
	  * @return
	  */
	@GetMapping("/accident")
	@ResponseBody
	public FeaturesLayerDTO getAccidentSource(@RequestParam Map<String, String> paramMap) {
		return statisticsService.getAccidentListByStatType(paramMap);
	}
}
