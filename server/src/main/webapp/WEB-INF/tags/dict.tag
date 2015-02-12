<%@tag pageEncoding="UTF-8"%>
<%@tag import="com.huake.dict.service.DictViewService"%>
<%@tag import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@tag import="org.springframework.context.ApplicationContext" %>
<%@ attribute name="className" type="java.lang.String" required="true"%>
<%@ attribute name="value" type="java.lang.String" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
ServletContext context = request.getSession().getServletContext();
String[] values = value.split(",");
ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
DictViewService dictViewService = (DictViewService)ac.getBean("dictViewService");
String outStr = "";
StringBuffer sb = new StringBuffer();
for(int i=0; i < values.length; i++){
	outStr = dictViewService.format(className, values[i]);
	sb.append(outStr);
	if( i< values.length-1) sb.append(",");
}
out.print(sb.toString());
%>
