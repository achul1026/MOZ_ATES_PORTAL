package com.moz.ates.traffic.portal.contactus.service;

import com.moz.ates.traffic.common.entity.board.MozBrd;
import com.moz.ates.traffic.common.enums.NoticeCateCd;
import com.moz.ates.traffic.common.repository.board.MozBrdRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

	@Autowired
	private MozBrdRepository mozBrdRepository;

	/**
	 * @Method Name : getNoticeList
	 * @Date : 2024. 2. 19.
	 * @Author : IK.MOON
	 * @Method Brief : 공지사항 리스트 조회
	 * @return
	 */
	public List<MozBrd> getNoticeList(MozBrd mozBrd) {
		mozBrd.setUseYn("Y");
		mozBrd.setCateCd(NoticeCateCd.PORTAL.getCode());
		return mozBrdRepository.findAllByUseYnOrderByImprtYn(mozBrd);
	}

	/**
	 * @Method Name : getNoticeListCount
	 * @Date : 2024. 2. 20.
	 * @Author : IK.MOON
	 * @Method Brief : 공지사항 count 조회
	 * @param mozBrd
	 * @return
	 */
	public int getNoticeListCount(MozBrd mozBrd) {
		mozBrd.setUseYn("Y");
		mozBrd.setCateCd(NoticeCateCd.PORTAL.getCode());
		return mozBrdRepository.countByUseYn(mozBrd);
	}

	/**
	 * @Method Name : getNoticeDetail
	 * @Date : 2024. 2. 20.
	 * @Author : IK.MOON
	 * @Method Brief : 공지사항 상세 조회
	 * @param boardIdx
	 * @return
	 */
	public MozBrd getNoticeDetail(String boardIdx) {

		if(MozatesCommonUtils.isNull(boardIdx)) {
			throw new CommonException(ErrorCode.REQUIRED_FIELDS);
		}

		MozBrd mozBrd = new MozBrd();
		mozBrd.setBoardIdx(boardIdx);
		mozBrd.setUseYn("Y");
		mozBrd.setCateCd(NoticeCateCd.PORTAL.getCode());

		mozBrdRepository.updateViewCnt(mozBrd);
		MozBrd noticeDetail = mozBrdRepository.findOneByBoardIdxAndUseYnAndCateCd(mozBrd);

		if(MozatesCommonUtils.isNull(noticeDetail)) {
			throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
		}

		return noticeDetail;
	}
}
