package com.vmware.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class GenerateHtml {

	public final String preHeight = "/resources/preHeight.txt";
	public final String preVmName = "/resources/preVmName.txt";
	public final String afterVmName = "/resources/afterVmName.txt";
	public final String afterOffset = "/resources/afterClone.txt";
	public final String afterClone = "/resources/afterOffset.txt";
	public final String afterLogic = "/resources/afterLogic.txt";
	public final String afterReconfigure = "/resources/afterReconfigure.txt";
	public final String afterPrePowerOn = "/resources/afterPrePowerOn.txt";
	public final String afterPowerOn = "/resources/afterPowerOn.txt";
	public final String afterPostPowerOn = "/resources/afterPostPowerOn.txt";
	
	public final String vmNameFlag = "vmName:";

	public String inputPath;
	public int nodes;
	public String outputPath;

	public GenerateHtml(String inputPath, int nodes, String outputPath) {
		this.inputPath = inputPath;
		this.nodes = nodes;
		this.outputPath = outputPath;
	}

	public void generateHtml() throws IOException {

		/* 读入TXT文件 */
		InputStreamReader reader = new InputStreamReader(new FileInputStream(
				new File(inputPath))); // 建立一个输入流对象reader
		BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(
				outputPath)));

		String line = "";
		BufferedReader tempbr = new BufferedReader(new InputStreamReader(
				this.getClass().getResourceAsStream(preHeight)));
		while ((line = br.readLine()) != null) {
			if (line.contains(vmNameFlag)) {
				
				String templine = "";
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				if(nodes<=5){
					out.write("400px");
				}else if(nodes==10){
					out.write("600px");
				}else if(nodes>=100){
					out.write(nodes*20+"px");
				}else{
					out.write("1000px");
				}
				
				line = br.readLine();// read vmNames
				tempbr = new BufferedReader(new InputStreamReader(
						this.getClass().getResourceAsStream(preVmName)));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				line = br.readLine();
				line = br.readLine();// read offset time
				tempbr = new BufferedReader(new InputStreamReader(
						this.getClass().getResourceAsStream(afterVmName)));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				line = br.readLine();
				line = br.readLine();// read clone time
				tempbr = new BufferedReader(new InputStreamReader(
						this.getClass().getResourceAsStream(afterOffset)));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				line = br.readLine();
				line = br.readLine();// read logic time
				tempbr = new BufferedReader(new InputStreamReader(
						this.getClass().getResourceAsStream(afterClone)));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				line = br.readLine();
				line = br.readLine();// read reconfigure time
				tempbr = new BufferedReader(new InputStreamReader(
						this.getClass().getResourceAsStream(afterLogic)));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				line = br.readLine();
				line = br.readLine();// read prePowerOn time
				tempbr = new BufferedReader(new InputStreamReader(
						this.getClass().getResourceAsStream(afterReconfigure)));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				line = br.readLine();
				line = br.readLine();// read powerOn time
				tempbr = new BufferedReader(new InputStreamReader(
						this.getClass().getResourceAsStream(afterPrePowerOn)));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				line = br.readLine();
				line = br.readLine();// read postPowerOn time
				tempbr = new BufferedReader(new InputStreamReader(
						this.getClass().getResourceAsStream(afterPowerOn)));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				tempbr = new BufferedReader(new InputStreamReader(
						this.getClass().getResourceAsStream(afterPostPowerOn)));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				
				out.flush(); // 把缓存区内容压入文件
				out.close(); // 最后记得关闭文件
				
				break;
				
				
			}
		}
		
		
	}

}
