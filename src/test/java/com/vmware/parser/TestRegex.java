package com.vmware.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {

	public final static String s = "[2015-08-19T06:29:22.743+0000]";
	
	 public static void main( String args[] ){

	      // 按指定模式在字符串查找
	      String line = "[2015-08-19T06:29:22.743+0000]";
	      String pattern = "\\[\\d+-\\d+-\\w+\\:(\\d+:\\d+).*";

	      // 创建 Pattern 对象
	      Pattern r = Pattern.compile(pattern);

	      // 现在创建 matcher 对象
	      Matcher m = r.matcher(line);
	      if (m.find( )) {
	         System.out.println("Found value: " + m.group(0) );
	         System.out.println("Found value: " + m.group(1) );
//	         System.out.println("Found value: " + m.group(2) );
	      } else {
	         System.out.println("NO MATCH");
	      }
	      
	      
	      System.out.println(s);
	   }
}
