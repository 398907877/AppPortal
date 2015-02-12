package com.huake.saas.util;

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.MethodInvoker;

import com.huake.base.ServiceException;
import com.huake.saas.weixin.aop.WebContext;

public class SpringUtils implements ApplicationContextAware {

	static Logger logger = LoggerFactory.getLogger(SpringUtils.class);
	
	private static ApplicationContext applicationContext;

	private SpringUtils() {
	}

	public void setApplicationContext(ApplicationContext _applicationContext)
			throws BeansException {
		applicationContext = _applicationContext;
	}

	/**
	 * 获取Bean对象
	 * @param name bean的配置名称
	 * @return bean对象
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 * 获取Bean对象
	 * @param name bean的配置名称
	 * @param requiredType bean的类型
	 * @return bean对象
	 */
	public static <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}

	/**
	 * 获取Bean对象
	 * 
	 * @param requiredType bean的类型
	 * @return bean对象
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 运行Bean中指定名称的方法并返回其返回值
	 * 
	 * @param object object对象
	 * @param methodName 方法名
	 * @param arguments 参数值
	 * @return 运行bean中指定名称的方法的返回值
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 */
	public static Object invokeBeanMethod(String beanName, String methodName,
			Object[] arguments) {
		try {
			// 初始化
			MethodInvoker methodInvoker = new MethodInvoker();
			methodInvoker.setTargetObject(SpringUtils.getBean(beanName));
			methodInvoker.setTargetMethod(methodName);

			// 设置参数
			if (arguments != null && arguments.length > 0) {
				methodInvoker.setArguments(arguments);
			}

			// 准备方法
			methodInvoker.prepare();

			// 执行方法
			return methodInvoker.invoke();
		} catch (ClassNotFoundException e) {
			throw new ServiceException(e);
		} catch (NoSuchMethodException e) {
			throw new ServiceException(e);
		} catch (InvocationTargetException e) {
			throw new ServiceException(e);
		} catch (IllegalAccessException e) {
			throw new ServiceException(e);
		}
	}
	/**
	 * 根据服务包生成内容访问地址
	 * @param context
	 * @param scope
	 * @return
	 */
	public static StringBuilder generatorScopeShowURL(WebContext context, String scope, String host){
		StringBuilder detailAccessUrl = new StringBuilder();
		detailAccessUrl.append("http://").append(context.getDomain()).append(".").append(host).append("/");
		detailAccessUrl.append(context.getUid()).append("/").append(scope).append("/");
		return detailAccessUrl;
	}

}
