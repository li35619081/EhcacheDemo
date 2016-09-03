package com.test.ehcache.web;



import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

	private static final Logger logger = Logger.getLogger(HomeController.class);

	@RequestMapping(value="/main")
	public String main(Model model){
		//参数
		String dateTime = new Date().toLocaleString();
		model.addAttribute("datas", dateTime);
		logger.info("测试，当前时间:" + dateTime);
		return "home/main";
	}
	
	
	
}
