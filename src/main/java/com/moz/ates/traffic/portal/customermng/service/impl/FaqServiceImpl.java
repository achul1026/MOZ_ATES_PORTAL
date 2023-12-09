package com.moz.ates.traffic.portal.customermng.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moz.ates.traffic.common.entity.board.MozFaq;
import com.moz.ates.traffic.common.repository.board.MozFaqRepository;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import com.moz.ates.traffic.portal.customermng.service.FaqService;

/**
 * @packageName : com.moz.ates.traffic.portal.customermng.service.impl 
 * @fileName    : FaqServiceImpl.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
@Service
public class FaqServiceImpl implements FaqService {

    @Autowired
    MozFaqRepository faqRepository;

	/**
	 * @Method 		: getFaqList
	 * @Author 		: NK.KIM
	 * @Description : FAQ 목록 조회
	 * <PRE>
	 * 2022. 2. 25. kim : 최초 작성
	 * </PRE>
	 */
	@Override
	public List<MozFaq> getFaqList(Map<String,Object> paramMap) {
		MozFaq faq = new MozFaq();
		String cateTy = !MozatesCommonUtils.isNull(paramMap.get("cateTy")) ? String.valueOf(paramMap.get("cateTy")) : "";
		faq.setCateTy(cateTy);
		return faqRepository.selectFaqListForPortal(faq);
	}
}
