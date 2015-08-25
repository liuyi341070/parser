package com.vmware.parser.service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vmware.parser.GenerateData;
import com.vmware.parser.GenerateHtml;

public class Service {
	
	private String logPath;
	private int nodes;
	
	public Service(String logPath, int nodes) {
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
		
		GenerateHtml html = new GenerateHtml(generateDataPath(logPath), nodes, generateHtmlPath(logPath));
		html.generateHtml();
	}
	
	public static void main(String[] args) throws IOException {
		
//		CopyOfService s = new CopyOfService("C:\\Users\\liuyi\\Desktop\\Intern Project\\logs\\824\\5_nodes.log", 
//				5);
		Service s = new Service(args[0], Integer.valueOf(args[1]));
		
		s.execute();
		
	}

}
