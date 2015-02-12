package com.huake.saas.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
@Component
public class FreeMarkertUtil{
	
	private static Logger logger = LoggerFactory
			.getLogger(FreeMarkertUtil.class);

	@Value("#{envProps.htmlpath}")
	private String outPath;
	
	public  void analysisTemplate(
			String templateName,String htmlFile,Map<?,?> root){ 
		
		try {
			Configuration config=new Configuration();
			config.setClassForTemplateLoading(getClass(), "/templates");
			config.setObjectWrapper(new DefaultObjectWrapper());
			Template template=config.getTemplate(templateName,"UTF-8");
			FileOutputStream fos = new FileOutputStream(getOutPath()+htmlFile);
			Writer out = new OutputStreamWriter(fos,"UTF-8");
			try {
				template.process(root,out);
			} catch (TemplateException e) {
				logger.debug("**************发生异常1*************");
			}
			out.flush();
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.debug("**************发生异常2*************");
		}
	
	}
	
	private String getOutPath()
	{
		File file = new File(outPath);
		if(!file.exists())
		{
			file.mkdirs();
		}		
		return outPath;
	}
	
}
