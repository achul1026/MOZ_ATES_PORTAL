package com.moz.ates.traffic.portal.main.service;

import com.moz.ates.traffic.common.entity.board.MozBrd;
import com.moz.ates.traffic.common.entity.board.MozFaq;
import com.moz.ates.traffic.common.enums.NoticeCateCd;
import com.moz.ates.traffic.common.repository.board.MozBrdRepository;
import com.moz.ates.traffic.common.repository.board.MozFaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

	@Autowired
	MozBrdRepository brdRepository;

	@Autowired
	MozFaqRepository faqRepository;

	/**
	 * @Method Name : getNoticeList
	 * @Date : 2024. 4. 8.
	 * @Author : IK.MOON
	 * @Method Brief : 공지사항 리스트 조회
	 * @param brd
	 * @return
	 */
	public List<MozBrd> getNoticeList(MozBrd brd) {
		brd.setLength(4);
		brd.setUseYn("Y");
		brd.setCateCd(NoticeCateCd.PORTAL.getCode());
		return brdRepository.findAllByUseYn(brd);
	}

	/**
	 * @Method Name : getFaqList
	 * @Date : 2024. 4. 8.
	 * @Author : IK.MOON
	 * @Method Brief : FAQ 리스트 조회
	 * @param faq
	 * @return
	 */
	public List<MozFaq> getFaqList(MozFaq faq) {
		faq.setLength(4);
		return faqRepository.findAllByDelYn(faq);
	}
}
