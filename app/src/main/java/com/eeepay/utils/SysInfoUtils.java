package com.eeepay.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Ching on 2018/4/10.
 * 系统信息工具类
 */

public class SysInfoUtils {

    /**
     * 内存信息文件:/proc/meminfo    ***    内存信息文件（CPU信息文件：/proc/cpuinfo）
     **/
    public final static String MEMINFO_DIR = "/proc/meminfo";

    public static String getTotalRam() {

        try {
            FileReader fr = new FileReader(MEMINFO_DIR);
            //创建读取字符流缓存区
            BufferedReader br = new BufferedReader(fr, 2048);
            //读取第一行字符
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            memoryLine = br.readLine();
            String availableMemoryLine = memoryLine.substring(memoryLine.indexOf("MemFree:"));
            br.close();
//            //获取总的内存,这里需要注意的是replaceAll支持正则表达式"\\D"代表所有的字母字符，只保留数字部分
//            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
//            //获取当前可用内存
//            long availableSize = Integer.parseInt(availableMemoryLine.replaceAll("\\D+", ""));
            StringBuffer sb = new StringBuffer("总内存为：");
            sb.append(subMemoryLine.replaceAll("\\D+", "")).append("\n可用内存为：").append(availableMemoryLine.replaceAll("\\D+", ""));

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
