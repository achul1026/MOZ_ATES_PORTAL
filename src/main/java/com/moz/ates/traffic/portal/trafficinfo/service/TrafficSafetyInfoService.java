package com.moz.ates.traffic.portal.trafficinfo.service;

import com.moz.ates.traffic.common.entity.board.MozTfcSftyEdctn;
import com.moz.ates.traffic.common.entity.board.MozTfcSftyInfrm;
import com.moz.ates.traffic.common.repository.board.MozTfcSftyEdctnRepository;
import com.moz.ates.traffic.common.repository.board.MozTfcSftyInfrmRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrafficSafetyInfoService {

	@Autowired
	private MozTfcSftyInfrmRepository tfcSftyInfrmRepository;

	@Autowired
	private MozTfcSftyEdctnRepository tfcSftyEdctnRepository;

	/**
	 * @Method Name : getTfcSftyInfrmCount
	 * @Date : 2024. 3. 27.
	 * @Author : IK.MOON
	 * @Method Brief : 교통 안전 정보 카운트 조회
	 * @param mozTfcSftyInfrm
	 * @return
	 */
	public int getTfcSftyInfrmCount(MozTfcSftyInfrm mozTfcSftyInfrm) {
		mozTfcSftyInfrm.setExpYn("Y");

		return tfcSftyInfrmRepository.countAllByExpYnAndPostStrDeAndPostEndDe(mozTfcSftyInfrm);
	}

	/**
	 * @Method Name : getTfcSftyInfrmList
	 * @Date : 2024. 3. 26.
	 * @Author : IK.MOON
	 * @Method Brief : 교통 안전 정보 리스트 조회
	 * @return
	 */
	public List<MozTfcSftyInfrm> getTfcSftyInfrmList(MozTfcSftyInfrm mozTfcSftyInfrm) {
		mozTfcSftyInfrm.setExpYn("Y");

		return tfcSftyInfrmRepository.findAllMozTfcSftyInfrmByExpYnAndPostStrDeAndPostEndDe(mozTfcSftyInfrm);
	}

	/**
	 * @Method Name : getTfcSftyInfrmDetail
	 * @Date : 2024. 3. 26.
	 * @Author : IK.MOON
	 * @Method Brief : 교통 안전 정보 상세 조회
	 * @param mozTfcSftyInfrm
	 * @return
	 */
	public MozTfcSftyInfrm getTfcSftyInfrmDetail(MozTfcSftyInfrm mozTfcSftyInfrm) {

		if (MozatesCommonUtils.isNull(mozTfcSftyInfrm.getTfcSftyInfrmId())) {
			throw new CommonException(ErrorCode.REQUIRED_FIELDS);
		}

		mozTfcSftyInfrm.setExpYn("Y");

		// 조회수 증가
		tfcSftyInfrmRepository.updateViewCntByTfcSftyInfrmIdAndExpYnAndPostStrDeAndPostEndDe(mozTfcSftyInfrm);

		MozTfcSftyInfrm tfcSftyInfrmDetail = tfcSftyInfrmRepository.findOneByTfcSftyInfrmIdAndExpYnAndPostStrDeAndPostEndDe(mozTfcSftyInfrm);

		if (MozatesCommonUtils.isNull(tfcSftyInfrmDetail)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}

		return tfcSftyInfrmDetail;
	}


	/**
	 * @Method Name : getTfcSftyEdctnCount
	 * @Date : 2024. 3. 27.
	 * @Author : IK.MOON
	 * @Method Brief : 교통 안전 교육 카운트 조회
	 * @param mozTfcSftyEdctn
	 * @return
	 */
	public int getTfcSftyEdctnCount(MozTfcSftyEdctn mozTfcSftyEdctn) {
		mozTfcSftyEdctn.setExpYn("Y");
		return tfcSftyEdctnRepository.countAllByExpYnAndPostStrDeAndPostEndDe(mozTfcSftyEdctn);
	}

	/**
	 * @Method Name : getTfcSftyEdctnList
	 * @Date : 2024. 3. 26.
	 * @Author : IK.MOON
	 * @Method Brief : 교통 안전 교육 리스트 조회
	 * @return
	 */
	public List<MozTfcSftyEdctn> getTfcSftyEdctnList(MozTfcSftyEdctn mozTfcSftyEdctn) {
		mozTfcSftyEdctn.setExpYn("Y");
		return tfcSftyEdctnRepository.findAllByExpYnAndPostStrDeAndPostEndDe(mozTfcSftyEdctn);
	}

	/**
	 * @Method Name : getTfcSftyEdctnDetail
	 * @Date : 2024. 3. 26.
	 * @Author : IK.MOON
	 * @Method Brief : 교통 안전 교육 상세 조회
	 * @param mozTfcSftyEdctn
	 * @return
	 */
	public MozTfcSftyEdctn getTfcSftyEdctnDetail(MozTfcSftyEdctn mozTfcSftyEdctn) {

		if (MozatesCommonUtils.isNull(mozTfcSftyEdctn.getTfcSftyEdctnId())) {
			throw new CommonException(ErrorCode.REQUIRED_FIELDS);
		}

		mozTfcSftyEdctn.setExpYn("Y");

		// 조회수 증가
		tfcSftyEdctnRepository.updateViewCntByExpYnAndPostStrDeAndPostEndDe(mozTfcSftyEdctn);

		MozTfcSftyEdctn tfcSftyEdctnDetail = tfcSftyEdctnRepository.findOneByExpYnAndPostStrDeAndPostEndDe(mozTfcSftyEdctn);

		if (MozatesCommonUtils.isNull(tfcSftyEdctnDetail)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}

		return tfcSftyEdctnDetail;
	}
}
