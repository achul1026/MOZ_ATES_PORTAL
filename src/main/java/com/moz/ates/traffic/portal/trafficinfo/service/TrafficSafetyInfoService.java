package com.moz.ates.traffic.portal.trafficinfo.service;

import java.util.List;
import com.moz.ates.traffic.common.entity.board.MozTfcSftyEdctn;
import com.moz.ates.traffic.common.entity.board.MozTfcSftyInfrm;

public interface TrafficSafetyInfoService {

	/**
	  * @Method Name : getTfcSftyInfrmCount
	  * @Date : 2024. 3. 27.
	  * @Author : IK.MOON
	  * @Method Brief : 교통 안전 정보 카운트 조회
	  * @param mozTfcSftyInfrm
	  * @return
	  */
	public int getTfcSftyInfrmCount(MozTfcSftyInfrm mozTfcSftyInfrm);
	
	/**
	  * @Method Name : getTfcSftyInfrmList
	  * @Date : 2024. 3. 26.
	  * @Author : IK.MOON
	  * @Method Brief : 교통 안전 정보 리스트 조회
	  * @return
	  */
	public List<MozTfcSftyInfrm> getTfcSftyInfrmList(MozTfcSftyInfrm mozTfcSftyInfrm);
	
	/**
	  * @Method Name : getTfcSftyInfrmDetail
	  * @Date : 2024. 3. 26.
	  * @Author : IK.MOON
	  * @Method Brief : 교통 안전 정보 상세 조회
	  * @param mozTfcSftyInfrm
	  * @return
	  */
	public MozTfcSftyInfrm getTfcSftyInfrmDetail(MozTfcSftyInfrm mozTfcSftyInfrm);
	
	
	/**
	  * @Method Name : getTfcSftyEdctnCount
	  * @Date : 2024. 3. 27.
	  * @Author : IK.MOON
	  * @Method Brief : 교통 안전 교육 카운트 조회
	  * @param mozTfcSftyInfrm
	  * @return
	  */
	public int getTfcSftyEdctnCount(MozTfcSftyEdctn mozTfcSftyEdctn);
	
	/**
	  * @Method Name : getTfcSftyEdctnList
	  * @Date : 2024. 3. 26.
	  * @Author : IK.MOON
	  * @Method Brief : 교통 안전 교육 리스트 조회
	  * @return
	  */
	public List<MozTfcSftyEdctn> getTfcSftyEdctnList(MozTfcSftyEdctn mozTfcSftyEdctn);
	
	/**
	  * @Method Name : getTfcSftyEdctnDetail
	  * @Date : 2024. 3. 26.
	  * @Author : IK.MOON
	  * @Method Brief : 교통 안전 교육 상세 조회
	  * @param mozTfcSftyEdctn
	  * @return
	  */
	public MozTfcSftyEdctn getTfcSftyEdctnDetail(MozTfcSftyEdctn mozTfcSftyEdctn);	
}
