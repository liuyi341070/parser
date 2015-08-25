package com.vmware.parser;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vmware.parser.GenerateData;

public class ServiceTest {
	
	private String logPath;
	private int nodes;
	
	public ServiceTest(String logPath, int nodes) {
		this.logPath = logPath;
		this.nodes = nodes;
	}
	
	public String generateDataPath(String logPath){
		
		String pattern = "(.*)\\.";
		// 创建 Pattern 对象
		Pattern r = Pattern.compile(pattern);
		// 现在创建 matcher 对象
		Matcher m = r.matcher(logPath);
		if(m.find()) return m.group(1)+"_data.txt";
		else return null;
		
	}
	
	public String generateHtmlPath(String logPath){
		
		String pattern = "(.*)\\.";
		// 创建 Pattern 对象
		Pattern r = Pattern.compile(pattern);
		// 现在创建 matcher 对象
		Matcher m = r.matcher(logPath);
		if(m.find()) return m.group(1)+".html";
		else return null;
	}
	
	public void execute() throws IOException{
		
		GenerateData data = new GenerateData(logPath, generateDataPath(logPath));
		data.generateData();
		
		GenerateHtmlWithFile html = new GenerateHtmlWithFile(generateDataPath(logPath), nodes, generateHtmlPath(logPath));
		html.generateHtml();
	}
	
	public static void main(String[] args) throws IOException {
		
		ServiceTest s = new ServiceTest("C:\\Users\\liuyi\\Desktop\\Intern Project\\logs\\824\\5_nodes.log", 
				5);
		
		s.execute();
		
	}

}
