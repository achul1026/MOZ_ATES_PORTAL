package com.moz.ates.traffic.portal.contactus.service;

import com.moz.ates.traffic.common.entity.board.MozFaq;
import com.moz.ates.traffic.common.repository.board.MozFaqRepository;
import com.moz.ates.traffic.common.support.exception.CommonException;
import com.moz.ates.traffic.common.support.exception.ErrorCode;
import com.moz.ates.traffic.common.util.MozatesCommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @packageName : com.moz.ates.traffic.portal.customermng.service 
 * @fileName    : FaqService.java 
 * @author      : NK.KIM
 * @date        : 2022.02.25 
 * @description : 최초 생성
 */
@Service
public class FaqService {

    @Autowired
    MozFaqRepository faqRepository;

		/**
		  * @Method Name : getFaqList
		  * @Date : 2024. 2. 21.
		  * @Author : IK.MOON
		  * @Method Brief : FAQ 목록 조회
		  * @param mozFaq
		  * @return
		  */
		public List<MozFaq> getFaqList(MozFaq mozFaq) {
			return faqRepository.findAllByDelYn(mozFaq);
		}

		/**
		  * @Method Name : getFaqCount
		  * @Date : 2024. 2. 21.
		  * @Author : IK.MOON
		  * @Method Brief : FAQ count 조회
		  * @param mozFaq
		  * @return
		  */
		public int getFaqCount(MozFaq mozFaq) {
			return faqRepository.countAllBtDelYn(mozFaq);
		}

		/**
		  * @Method Name : getFaqDetail
		  * @Date : 2024. 2. 23.
		  * @Author : IK.MOON
		  * @Method Brief : FAQ 상세 조회
		  * @param mozFaq
		  * @return
		  */
		public MozFaq getFaqDetail(MozFaq mozFaq) {
			
			if(MozatesCommonUtils.isNull(mozFaq.getFaqIdx())) {
				throw new CommonException(ErrorCode.REQUIRED_FIELDS);
			}
			
			faqRepository.updateViewCnt(mozFaq);
			MozFaq dbMozFaq = faqRepository.findOneByDelYnAndFaqIdx(mozFaq);
			
			if(MozatesCommonUtils.isNull(dbMozFaq)) {
				throw new CommonException(ErrorCode.ENTITY_DATA_NULL);
			}
			
			return dbMozFaq;
		}
}
