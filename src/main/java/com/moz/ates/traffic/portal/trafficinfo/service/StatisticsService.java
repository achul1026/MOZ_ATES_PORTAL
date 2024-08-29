package com.moz.ates.traffic.portal.trafficinfo.service;

import com.moz.ates.traffic.common.entity.common.ChartDTO.AccidentChartGraph;
import com.moz.ates.traffic.common.entity.common.ChartDTO.AccidentTotalChart;
import com.moz.ates.traffic.common.entity.common.ChartDTO.AccidentTotalVictimsChart;
import com.moz.ates.traffic.common.entity.common.ChartDTO.AccidentTypeTotalChart;
import com.moz.ates.traffic.common.repository.accident.MozTfcAcdntMasterRepository;
import com.moz.ates.traffic.common.repository.monitoring.MonitoringMapRepository;
import com.moz.ates.traffic.portal.common.FeaturesLayerDTO;
import com.moz.ates.traffic.portal.common.GeoJsonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

	@Autowired
	MonitoringMapRepository monitoringMapRepository;

	@Autowired
	MozTfcAcdntMasterRepository mozTfcAcdntMasterRepository;

	private FeaturesLayerDTO getFeaturesLayerDTO(FeaturesLayerDTO featuresLayerDTO,
												 List<GeoJsonDTO.FeaturesDTO> featuresList, List<Map<String, Object>> facilityList) {
		facilityList.forEach(map -> {
			GeoJsonDTO.FeaturesDTO featuresDomain = new GeoJsonDTO.FeaturesDTO();
			GeoJsonDTO.GeometryDTO geometryDomain = new GeoJsonDTO.GeometryDTO();
			geometryDomain.setType("Point");
			geometryDomain.setLng(Double.parseDouble((String) map.get("LNG")));
			geometryDomain.setLat(Double.parseDouble((String) map.get("LAT")));

			featuresDomain.setGeometry(geometryDomain);
			featuresDomain.setProperties(map);
			featuresList.add(featuresDomain);
		});
		featuresLayerDTO.setFeatures(featuresList);
		return featuresLayerDTO;
	}

	/**
	 * @Method Name : getFacilityGeoJson
	 * @Date : 2024. 5. 20.
	 * @Author : IK.MOON
	 * @Method Brief : map 시설물 정보 조회
	 * @return
	 */
	public FeaturesLayerDTO getFacilityGeoJson(Map<String, String> parmaMap) {
		FeaturesLayerDTO featuresLayerDTO = new FeaturesLayerDTO();
		List<GeoJsonDTO.FeaturesDTO> featuresList = new ArrayList<>();
		List<Map<String, Object>> facilityList = monitoringMapRepository.findAllFacilityByFacilityTy(parmaMap);
		return getFeaturesLayerDTO(featuresLayerDTO, featuresList, facilityList);
	}
	
	/**
	  * @Method Name : getEquipmentSource
	  * @Date : 2024. 7. 10.
	  * @Author : IK.MOON
	  * @Method Brief : map 단속 카메라 정보 조회
	  * @param param
	  * @return
	  */
	public FeaturesLayerDTO getEquipmentSource(Map<String, String> param) {
		FeaturesLayerDTO featuresLayerDTO = new FeaturesLayerDTO();
		List<GeoJsonDTO.FeaturesDTO> featuresList = new ArrayList<>();
		List<Map<String, Object>> equipmentList = monitoringMapRepository.findAllEquipmentByEquipmentTy(param);
		return getFeaturesLayerDTO(featuresLayerDTO, featuresList, equipmentList);
	}

	/**
	 * @breif : 유관기관 Map 조회
	 * @date : 2024.07.17
	 * @param param
	 * @return
	 */
	public FeaturesLayerDTO getOrganizationGeoJson(Map<String, String> param) {
		FeaturesLayerDTO featuresLayerDTO = new FeaturesLayerDTO();
		List<GeoJsonDTO.FeaturesDTO> featuresList = new ArrayList<>();
		List<Map<String, Object>> facilityList = monitoringMapRepository.findAllFacilityByFacilityTy(param);
		return getFeaturesLayerDTO(featuresLayerDTO, featuresList, facilityList);
	}

	/**
	 * @Method Name : getAccidentList
	 * @Date : 2024. 5. 24.
	 * @Author : IK.MOON
	 * @Method Brief : map 사고 정보 total 조회
	 * @param param
	 * @return
	 */
	public FeaturesLayerDTO getAccidentList(Map<String, String> param) {
		FeaturesLayerDTO featuresLayerDTO = new FeaturesLayerDTO();
		List<GeoJsonDTO.FeaturesDTO> featuresList = new ArrayList<>();
		List<Map<String, Object>> accidentList = monitoringMapRepository.findAllAccidentByDate(param);
		return getFeaturesLayerDTO(featuresLayerDTO, featuresList, accidentList);
	}

	/**
	 * @Method Name : getAccidentListByStatType
	 * @Date : 2024. 5. 23.
	 * @Author : IK.MOON
	 * @Method Brief : map 사고 정보 type별 조회
	 * @param paramMap
	 * @return
	 */
	public FeaturesLayerDTO getAccidentListByStatType(Map<String, String> paramMap) {
		FeaturesLayerDTO featuresLayerDTO = new FeaturesLayerDTO();
		List<GeoJsonDTO.FeaturesDTO> featuresList = new ArrayList<>();
		List<Map<String, Object>> accidentList = monitoringMapRepository.findAllAccidentByDateAndStatType(paramMap);
		return getFeaturesLayerDTO(featuresLayerDTO, featuresList, accidentList);
	}

	/**
	 * @Method Name : getAccidentTotalChart
	 * @Date : 2024. 5. 24.
	 * @Author : IK.MOON
	 * @Method Brief :	chart 사고 전체 통계
	 * @param paramMap
	 * @return
	 */
	public AccidentTotalChart getAccidentTotalChart(Map<String, String> paramMap) {
		return mozTfcAcdntMasterRepository.findOneAccidentChartData(paramMap);
	}

	/**
	 * @Method Name : getAccidentTotalByTypeChart
	 * @Date : 2024. 5. 24.
	 * @Author : IK.MOON
	 * @Method Brief : chart 사고 전체 type별 통계
	 * @param paramMap
	 * @return
	 */
	public AccidentChartGraph getAccidentTotalByTypeChart(Map<String, String> paramMap) {
		return mozTfcAcdntMasterRepository.findOneAccidentByTypeChartDate(paramMap);
	}

	/**
	 * @Method Name : getAccidentTypeTotalChart
	 * @Date : 2024. 5. 27.
	 * @Author : IK.MOON
	 * @Method Brief : chart 사고 피해 타입별 통계
	 * @param parmaMap
	 * @return
	 */
	public AccidentTypeTotalChart getAccidentTypeTotalChart(Map<String, String> parmaMap) {
		return mozTfcAcdntMasterRepository.findOneAccidentTypeTotalChart(parmaMap);
	}

	/**
	 * @Method Name : getAccidentTotalVictimsChart
	 * @Date : 2024. 5. 27.
	 * @Author : IK.MOON
	 * @Method Brief : chart 사고 전체 피해자 수 통계
	 * @param parmaMap
	 * @return
	 */
	public AccidentTotalVictimsChart getAccidentTotalVictimsChart(Map<String, String> parmaMap) {
		return mozTfcAcdntMasterRepository.findOneAccidentTotalVictimsChart(parmaMap);
	}
}
