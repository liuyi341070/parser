package com.vmware.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateData {

	
	public String inputPath;
	public String outputPath;
	public DecimalFormat df = new DecimalFormat("0.00");
	public float initOffset = 0.0f; 
	// public final String scheduleFlag =
	// "scheduling a clone from source VmCreateSpec";
	public final String cloneStartFlag = "VmCloner: clone from source";
	public final String cloneFinishFlag = "notify listener on vm clone done";
	public final String recfgStartFlag = "start reconfiguring vm";
	public final String prePowerOnFlag = "call prePowerOn for vm";
	public final String powerOnFlag = "powering on vm";
	public final String postPowerOnFlag = "call postPowerOn for vm";
	public final String postPowerOnDoneFlag = "postPowerOn is done";

	public Map<String, CloneLifeTime> map = new LinkedHashMap<String, CloneLifeTime>();

	public GenerateData(String inputPath, String outputPath){
		this.inputPath = inputPath;
		this.outputPath = outputPath;
	}

	public void generateData() {

		try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw

			/* 读入TXT文件 */
			InputStreamReader reader = new InputStreamReader(
					new FileInputStream(new File(inputPath))); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line = "";
			String vmName = "";
			CloneLifeTime c = new CloneLifeTime();
			float t = 0.0f;
			boolean initOffsetFlag = false;
			while ((line = br.readLine()) != null) {

				// if newly schedule, put it into map
				// if (line.contains(scheduleFlag)) {
				// vmName = findVmName(line);
				// map.put(vmName, new CloneLifeTime());
				// }
				if (line.contains(cloneStartFlag)) {
					vmName = findVmNameCloneStart(line);
					System.out.println(vmName);
					c = map.get(vmName);
					if (c == null) {
						map.put(vmName, new CloneLifeTime());
						c = map.get(vmName);
					}
					//first clone time is offset
					if(initOffsetFlag==false){
						initOffset = getTime(line);
						initOffsetFlag = true;
					}
					c.setCloneStartOri(getOrignalTime(line));
					t = getTime(line);
					c.setCloneStart(t);
					System.out.println("##########clone start time: "+t);
				}
				if (line.contains(cloneFinishFlag)) {
					vmName = findVmName(line);
					System.out.println(vmName);
					c = map.get(vmName);
					if (c == null) {
						map.put(vmName, new CloneLifeTime());
						c = map.get(vmName);
					}
					c.setCloneFinishOri(getOrignalTime(line));
					t = getTime(line);
					c.setCloneFinish(t);
					System.out.println("##########clone finish time: "+t);
				}
				if (line.contains(recfgStartFlag)) {
					vmName = findVmNameCfg(line);
					System.out.println(vmName);
					c = map.get(vmName);
					if (c == null) {
						map.put(vmName, new CloneLifeTime());
						c = map.get(vmName);
					}
					c.setReconfigureStartOri(getOrignalTime(line));
					t = getTime(line);
					c.setReconfigureStart(t);
					System.out.println("##########reconfigure start time: "+t);
				}
				if (line.contains(prePowerOnFlag)) {
					vmName = findVmNamePower(line);
					System.out.println(vmName);
					c = map.get(vmName);
					if (c == null) {
						map.put(vmName, new CloneLifeTime());
						c = map.get(vmName);
					}
					c.setPrePowerOnStartOri(getOrignalTime(line));
					t = getTime(line);
					c.setPrePowerOnStart(t);
					System.out.println("##########prePowerOn start time: "+t);
				}
				if (line.contains(powerOnFlag)) {
					vmName = findVmNamePower(line);
					System.out.println(vmName);
					c = map.get(vmName);
					if (c == null) {
						map.put(vmName, new CloneLifeTime());
						c = map.get(vmName);
					}
					c.setPowerOnStartOri(getOrignalTime(line));
					t = getTime(line);
					c.setPowerOnStart(t);
					System.out.println("##########powerOn start time: "+t);
				}
				if (line.contains(postPowerOnFlag)) {
					vmName = findVmNamePower(line);
					System.out.println(vmName);
					c = map.get(vmName);
					if (c == null) {
						map.put(vmName, new CloneLifeTime());
						c = map.get(vmName);
					}
					c.setPostPowerOnStartOri(getOrignalTime(line));
					t = getTime(line);
					c.setPostPowerOnStart(t);
					System.out.println("##########postPowerOn start time: "+t);
				}
				if (line.contains(postPowerOnDoneFlag)) {
					vmName = findVmNamePower(line);
					System.out.println(vmName);
					c = map.get(vmName);
					if (c == null) {
						map.put(vmName, new CloneLifeTime());
						c = map.get(vmName);
					}
					c.setPostPowerOnDoneOri(getOrignalTime(line));
					t = getTime(line);
					c.setPostPowerOnDone(t);
					System.out.println("##########postPowerOn done time: "+t);
				}

				// System.out.println(line);

			}
			/* 写入Txt文件 */
			File writename = new File(outputPath);
			writename.createNewFile(); // 创建新文件
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));

			//output original time greped from logs
			out.write("Print origin time greped from logs, format is as below:\r\n");
			out.write("vmName, cloneStart, cloneFinish, reconfigureStart, prePowerOnStart, "
					+ "powerOnStart, postPowerOnStart, postPowerOnDone\r\n");
			Iterator<Map.Entry<String, CloneLifeTime>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, CloneLifeTime> entry = it.next();
				c = entry.getValue();
				out.write(entry.getKey() + "," + c.getCloneStartOri() + ","
						+ c.getCloneFinishOri() + "," + c.getReconfigureStartOri()
						+ "," + c.getPrePowerOnStartOri() + ","
						+ c.getPowerOnStartOri() + "," + c.getPostPowerOnStartOri()
						+ "," + c.getPostPowerOnDoneOri() + "\r\n"); // \r\n即为换行

			}
			
			out.write("\r\nPrint calculated time costed in each period:\r\n");
			//output vmName
			it = map.entrySet().iterator();
			Map.Entry<String, CloneLifeTime> entry = it.next();
			out.write("vmName:\r\n");
			out.write("'"+entry.getKey()+"'");
			while (it.hasNext()) {
				entry = it.next();
				out.write(",'"+entry.getKey()+"'"); // \r\n即为换行
			}
			out.write("\r\n");
			//output offset time
			it = map.entrySet().iterator();
			entry = it.next();
			c = entry.getValue();
			c.setOffsetTime(c.getCloneStart()-initOffset);
			float i = Float.valueOf(df.format(c.getOffsetTime()));
			out.write("offset time:\r\n");
			out.write("'"+(i==0?0.1:i)+"'");
			while (it.hasNext()) {
				entry = it.next();
				c = entry.getValue();
				c.setOffsetTime(c.getCloneStart()-initOffset);
				i = Float.valueOf(df.format(c.getOffsetTime()));
				out.write(",'"+(i==0f?0.1f:i)+"'"); // \r\n即为换行
			}
			out.write("\r\n");
			
			//output clone time 
			it = map.entrySet().iterator();
			entry = it.next();
			c = entry.getValue();
			c.setCloneTime(c.getCloneFinish()-c.getCloneStart());
			i = Float.valueOf(df.format(c.getCloneTime()));
			out.write("clone time:\r\n");
			out.write("'"+(i==0f?0.1f:i)+"'");
			while (it.hasNext()) {
				entry = it.next();
				c = entry.getValue();
				c.setCloneTime(c.getCloneFinish()-c.getCloneStart());
				i = Float.valueOf(df.format(c.getCloneTime()));
				out.write(",'"+(i==0f?0.1f:i)+"'"); // \r\n即为换行
			}
			out.write("\r\n");
			
			//output clone-reconfigure logic time 
			it = map.entrySet().iterator();
			entry = it.next();
			c = entry.getValue();
			c.setCl_rcfgLogicTime(c.getReconfigureStart()-c.getCloneFinish());
			i = Float.valueOf(df.format(c.getCl_rcfgLogicTime()));
			out.write("clone to reconfigure logic time:\r\n");
			out.write("'"+(i==0f?0.1f:i)+"'");
			while (it.hasNext()) {
				entry = it.next();
				c = entry.getValue();
				c.setCl_rcfgLogicTime(c.getReconfigureStart()-c.getCloneFinish());
				i = Float.valueOf(df.format(c.getCl_rcfgLogicTime()));
				out.write(",'"+(i==0f?0.1f:i)+"'"); // \r\n即为换行
			}
			out.write("\r\n");
			
			//output reconfigure time 
			it = map.entrySet().iterator();
			entry = it.next();
			c = entry.getValue();
			c.setReconfigureTime(c.getPrePowerOnStart()-c.getReconfigureStart());
			i = Float.valueOf(df.format(c.getReconfigureTime()));
			out.write("reconfigure time:\r\n");
			out.write("'"+(i==0f?0.1f:i)+"'");
			while (it.hasNext()) {
				entry = it.next();
				c = entry.getValue();
				c.setReconfigureTime(c.getPrePowerOnStart()-c.getReconfigureStart());
				i = Float.valueOf(df.format(c.getReconfigureTime()));
				out.write(",'"+(i==0f?0.1f:i)+"'"); // \r\n即为换行
			}
			out.write("\r\n");
			
			//output prePowerOn time 
			it = map.entrySet().iterator();
			entry = it.next();
			c = entry.getValue();
			c.setPrePowerOnTime(c.getPowerOnStart()-c.getPrePowerOnStart());
			i = Float.valueOf(df.format(c.getPrePowerOnTime()));
			out.write("prePowerOn time:\r\n");
			out.write("'"+(i==0f?0.1f:i)+"'");
			while (it.hasNext()) {
				entry = it.next();
				c = entry.getValue();
				c.setPrePowerOnTime(c.getPowerOnStart()-c.getPrePowerOnStart());
				i = Float.valueOf(df.format(c.getPrePowerOnTime()));				
				out.write(",'"+(i==0f?0.1f:i)+"'"); // \r\n即为换行
			}
			out.write("\r\n");
			
			//output PowerOn time 
			it = map.entrySet().iterator();
			entry = it.next();
			c = entry.getValue();
			c.setPowerOnTime(c.getPostPowerOnStart()-c.getPowerOnStart());
			i = Float.valueOf(df.format(c.getPowerOnTime()));
			out.write("powerOn time:\r\n");
			out.write("'"+(i==0f?0.1f:i)+"'");
			while (it.hasNext()) {
				entry = it.next();
				c = entry.getValue();
				c.setPowerOnTime(c.getPostPowerOnStart()-c.getPowerOnStart());
				i = Float.valueOf(df.format(c.getPowerOnTime()));
				out.write(",'"+(i==0f?0.1f:i)+"'"); // \r\n即为换行
			}
			out.write("\r\n");
			
			//output PostPowerOn time 
			it = map.entrySet().iterator();
			entry = it.next();
			c = entry.getValue();
			c.setPostPowerOnTime(c.getPostPowerOnDone()-c.getPostPowerOnStart());
			i = Float.valueOf(df.format(c.getPostPowerOnTime()));
			out.write("postPowerOn time:\r\n");
			out.write("'"+(i==0f?0.1f:i)+"'");
			while (it.hasNext()) {
				entry = it.next();
				c = entry.getValue();
				c.setPostPowerOnTime(c.getPostPowerOnDone()-c.getPostPowerOnStart());
				i = Float.valueOf(df.format(c.getPostPowerOnTime()));
				out.write(",'"+(i==0f?0.1f:i)+"'"); // \r\n即为换行
			}
			out.write("\r\n");
			
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public String getOrignalTime(String in) {
		//[2015-08-19T06:29:22.743+0000]
		return in.split(" INFO")[0];
	}
	
	public float getTime(String in) {
		//[2015-08-19T06:29:22.743+0000]
		String s = in.split(" INFO")[0];
		//to get 29:22
		String pattern = "\\[\\d+-\\d+-\\w+\\:(\\d+):(\\d+)\\.(\\d\\d\\d).*";
		// 创建 Pattern 对象
		Pattern r = Pattern.compile(pattern);
		// 现在创建 matcher 对象
		Matcher m = r.matcher(s);
		if (m.find()) {
//			System.out.println("Found value: " + m.group(0));
//			System.out.println("Found value: " + m.group(1));
//			System.out.println("Found value: " + m.group(2));
			
			float f = Integer.valueOf(m.group(1))*60f+Integer.valueOf(m.group(2))
				+(Integer.valueOf(m.group(3))*0.001f);
			return Float.valueOf(df.format(f));
		} else {
			System.out.println("NO MATCH");
			return 0;
		}
	}

	public String findVmName(String in) {
		// ...target VmCreateSpec [vmName=vm5-worker-1]
		String[] s = in.split("target VmCreateSpec \\[vmName=");
		return s[1].substring(0, s[1].length() - 1);
	}

	public String findVmNamePower(String in) {
		// ...for vm vm5-worker-1
		// ...on vm vm5-worker-1
		return in.split(" vm ")[1];
	}

	public String findVmNameCfg(String in) {
		// ...start reconfiguring vm vm5-worker-1 after cloning
		String[] s = in.split(" vm ");
		return s[1].split(" after")[0];
	}

	public String findVmNameCloneStart(String in) {
		// ...clone from source null:VirtualMachine:vm-1113to vm5-worker-1
		return in.split("to ")[1];
	}


}
