package com.moz.ates.traffic.portal.trafficinfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/statisticsmng")
public class StatisticsMngController {

	@GetMapping(value = "/statisticsAnalyze")
	public String statisticsAnalyzePage(Model model) {

		return "views/statisticsmng/statisticsAnalyze";
	}
}
