package com.huake.saas.account.service.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.huake.saas.account.service.token.CaptchaAndUidToken;

public class CaptchaUidFormAuthenticationFilter extends
		FormAuthenticationFilter {

	public static final String DEFAULT_UID_PARAM = "uid";
	public static final String DEFAULT_CAPCTHA_PARAM = "captcha";
	private String uidParam = DEFAULT_UID_PARAM;
	private String captchaParam = DEFAULT_CAPCTHA_PARAM;

	public String getUidParam() {
		return uidParam;
	}

	public void setUid(String uidParam) {
		this.uidParam = uidParam;
	}

	protected String getUid(ServletRequest request) {
		return WebUtils.getCleanParam(request, getUidParam());
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	// 创建 Token
	@Override
	protected CaptchaAndUidToken createToken(ServletRequest request,
			ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String uid = getUid(request);
		String captcha = getCaptcha(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);

		return new CaptchaAndUidToken(username, password.toCharArray(),
				rememberMe, host, uid.trim(), captcha);
	}

	// 认证
	@Override
	protected boolean executeLogin(ServletRequest request,
			ServletResponse response) throws Exception {
		CaptchaAndUidToken token = createToken(request, response);
		
		try {
			doCaptchaValidate((HttpServletRequest)request,token);
			Subject subject = getSubject(request, response);
			subject.login(token);
			return onLoginSuccess(token, subject, request, response);
		} catch (AuthenticationException e) {
			return onLoginFailure(token, e, request, response);
		}
	}

	// 验证码校验
	protected void doCaptchaValidate(HttpServletRequest request,
			CaptchaAndUidToken token) throws Exception {
		String captcha = (String) request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		System.out.println(captcha);
		System.out.println(token.getCaptcha());
		if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
			throw new AuthenticationException("验证码错误！");
		}
	}

}
