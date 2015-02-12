package com.huake.saas.base.annonation;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.Param;

import com.huake.saas.base.annonation.model.BizCodeInterface;
import com.huake.saas.base.service.ServiceException;
import com.huake.saas.util.SpringUtils;
import com.huake.saas.weixin.annonation.WeixinTargetName;
import com.huake.saas.weixin.annonation.WeixinTargetParameter;

/**
 * 对外接口注解工具服务
 * @author laidingqing
 *
 */
@Component
public class TargetAnnonationService {

	/**
	 * 获取对外映射接口定义
	 * @param beanName
	 * @return
	 * @throws ServiceException
	 */
	public  List<BizCodeInterface> getTargetInterfaceMethods(String beanName) throws ServiceException{
		List<BizCodeInterface> interfaces = new ArrayList<BizCodeInterface>();
		
		//
		Class[] targetBeanClass = SpringUtils.getBean(beanName).getClass().getInterfaces();
		System.out.println(targetBeanClass.length);
		for(Class vClass : targetBeanClass){
			System.out.println(vClass.getName()+"接口名称");
			
			//获取所有方法
			Method[] methods = vClass.getMethods();
			for(Method method: methods){
				System.out.println(method.getName()+"方法名字");
				//获取该方法上的注解，getAnnotations可以返回所有注解 class<T>的话返回某种类型
				WeixinTargetName targetAnno = method.getAnnotation(WeixinTargetName.class);
				String name = targetAnno.value();
				System.out.println(name+"注解的名字");	
//				
//				Parameter[]  para= method.getParameters();
//				for (Parameter parameter : para) {
//					//获取到该方法下面所有parameter
//					WeixinTargetParameter para1=  parameter.getAnnotation(WeixinTargetParameter.class);
//				
//					System.out.println("参数：：："+	para1.name());
//					
//				}			
				

				//JDK6使用注解来获取参数名字3
				//第一层 数组是第几个参数，  第二层是第几个注解
				Annotation[][] paraandannots= method.getParameterAnnotations();
				
				 int paranum= paraandannots.length;
				System.out.println("一共"+paranum+"个参数");
				 
				for (int j = 0; j < method.getParameterTypes().length; j++) {
					//第j个参数下的所有注解
					Annotation[]  annot= paraandannots[j];
					//第一个注解 对象（为什么是0  因为 这里只有一个注解对象 ，如果为多个的话则需要循环获取）
					WeixinTargetParameter  parannot= (WeixinTargetParameter) annot[0];
					int n=j+1;
					System.out.println("第"+n+"个参数的第一个注解的   英文："+parannot.zhname());
					System.out.println("                                      中文："+parannot.enname());
					
					
				}
				
				
			}
			
			
			
			
			

		}
		return null;
	}
}
