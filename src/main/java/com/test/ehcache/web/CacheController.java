package com.test.ehcache.web;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.ehcache.util.EhcacheUtil;

@Controller
@RequestMapping("/cache")
public class CacheController{

	private static final Logger logger = Logger.getLogger(CacheController.class);
	
	@RequestMapping(value="/clean")
	@ResponseBody
	public void cleanPageCache(@RequestParam String params){
		logger.info("清除缓存"+params);
		EhcacheUtil.remove("webPageCache", params);
	}
	
	
	
}
