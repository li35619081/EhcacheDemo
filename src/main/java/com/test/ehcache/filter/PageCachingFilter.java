package com.test.ehcache.filter;



import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.ehcache.constructs.blocking.LockTimeoutException;
import net.sf.ehcache.constructs.web.AlreadyCommittedException;
import net.sf.ehcache.constructs.web.AlreadyGzippedException;
import net.sf.ehcache.constructs.web.filter.FilterNonReentrantException;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;


public class PageCachingFilter  extends SimplePageCachingFilter{

	private static final Logger logger = Logger.getLogger(PageCachingFilter.class);
	
	/**
	 * 设置缓存页面的key
	 */
	@Override
	protected String calculateKey(HttpServletRequest request) {
		StringBuffer stringBuffer = new StringBuffer();
		//请求地址
		String url=request.getRequestURI();
		//请求参数
		String query=request.getQueryString();	
		
		stringBuffer.append(url);
		stringBuffer.append("?");
		stringBuffer.append(query);
		return stringBuffer.toString();
	}

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws AlreadyGzippedException, AlreadyCommittedException, FilterNonReentrantException,
			LockTimeoutException, Exception {
		String url=request.getRequestURI()+"?"+request.getQueryString();
		
		//过滤活动相关的分组等请求，满足则缓存页面
		if(url.contains("/home/main.do")){
			logger.info("ehcache缓存页面地址：" + url);
			super.doFilter(request, response, chain);
		}else{
			//不满足则不缓存
			chain.doFilter(request, response);
		}
	}
	
	/**
	 * 兼容ie6/7 gzip压缩
	 */
	@Override
	protected boolean acceptsGzipEncoding(HttpServletRequest request) {
		boolean ie6 = headerContains(request, "User-Agent", "MSIE 6.0");
		boolean ie7 = headerContains(request, "User-Agent", "MSIE 7.0");
		return acceptsEncoding(request, "gzip") || ie6 || ie7;
	}
	
	
	private boolean headerContains(final HttpServletRequest request, final String header, final String value) {
		logRequestHeaders(request);
		final Enumeration<?> accepted = request.getHeaders(header);
		while (accepted.hasMoreElements()) {
			final String headerValue = (String) accepted.nextElement();
			if (headerValue.indexOf(value) != -1) {
				return true;
			}
		}
		return false;
	}
}
