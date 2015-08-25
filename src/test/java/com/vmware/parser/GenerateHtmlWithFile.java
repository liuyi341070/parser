package com.vmware.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class GenerateHtmlWithFile {

	public final String preHeight = ".//src//main//resources//preHeight.txt";
	public final String preVmName = ".//src//main//resources//preVmName.txt";
	public final String afterVmName = ".//src//main//resources//afterVmName.txt";
	public final String afterOffset = ".//src//main//resources//afterClone.txt";
	public final String afterClone = ".//src//main//resources//afterOffset.txt";
	public final String afterLogic = ".//src//main//resources//afterLogic.txt";
	public final String afterReconfigure = ".//src//main//resources//afterReconfigure.txt";
	public final String afterPrePowerOn = ".//src//main//resources//afterPrePowerOn.txt";
	public final String afterPowerOn = ".//src//main//resources//afterPowerOn.txt";
	public final String afterPostPowerOn = ".//src//main//resources//afterPostPowerOn.txt";

	public final String vmNameFlag = "vmName:";

	public String inputPath;
	public int nodes;
	public String outputPath;

	public GenerateHtmlWithFile(String inputPath, int nodes, String outputPath) {
		this.inputPath = inputPath;
		this.nodes = nodes;
		this.outputPath = outputPath;
	}

	public void generateHtml() throws IOException {

		InputStreamReader reader = new InputStreamReader(new FileInputStream(
				new File(inputPath))); 
		BufferedReader br = new BufferedReader(reader); 
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(
				outputPath)));

		String line = "";
		BufferedReader tempbr = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(preHeight))));
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
						new FileInputStream(new File(preVmName))));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				line = br.readLine();
				line = br.readLine();// read offset time
				tempbr = new BufferedReader(new InputStreamReader(
						new FileInputStream(new File(afterVmName))));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				line = br.readLine();
				line = br.readLine();// read clone time
				tempbr = new BufferedReader(new InputStreamReader(
						new FileInputStream(new File(afterOffset))));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				line = br.readLine();
				line = br.readLine();// read logic time
				tempbr = new BufferedReader(new InputStreamReader(
						new FileInputStream(new File(afterClone))));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				line = br.readLine();
				line = br.readLine();// read reconfigure time
				tempbr = new BufferedReader(new InputStreamReader(
						new FileInputStream(new File(afterLogic))));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				line = br.readLine();
				line = br.readLine();// read prePowerOn time
				tempbr = new BufferedReader(new InputStreamReader(
						new FileInputStream(new File(afterReconfigure))));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				line = br.readLine();
				line = br.readLine();// read powerOn time
				tempbr = new BufferedReader(new InputStreamReader(
						new FileInputStream(new File(afterPrePowerOn))));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				line = br.readLine();
				line = br.readLine();// read postPowerOn time
				tempbr = new BufferedReader(new InputStreamReader(
						new FileInputStream(new File(afterPowerOn))));
				while ((templine = tempbr.readLine()) != null) {
					out.write(templine+"\r\n");
				}
				out.write(line+"\r\n");

				tempbr = new BufferedReader(new InputStreamReader(
						new FileInputStream(new File(afterPostPowerOn))));
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
