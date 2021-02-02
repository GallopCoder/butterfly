package com.ali.retail.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);


	public static boolean write2File(String path, String line, boolean append) {
		//文件的续写
		FileWriter fw = null;
		try {
			fw = new FileWriter(path, append);
			//写入换行
//			fw.write("\n");//Windows平台下用\r\n，Linux/Unix平台下用\n
			//续写一个hello world!
			fw.write(line + "\n");
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void removeEndLine(String filepath) {
		try{
			RandomAccessFile file = new RandomAccessFile(filepath, "rw");
			long len = file.length();
			long start = file.getFilePointer();
			long nextend = start + len - 1;
			file.seek(nextend);
			byte[] buf = new byte[1];
			while (nextend > start) {
				file.read(buf, 0, 1);
				if (buf[0] == '\r') {
					file.setLength(nextend - start);
					break;
				}
				nextend--;
				file.seek(nextend);
			}
			file.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public static void rewriteEndLine(String filepath, String string) throws Exception {
		removeEndLine(filepath);
		writeEndLine(filepath, string);
	}

	public static void writeEndLine(String filepath, String string) throws Exception {
		RandomAccessFile file = new RandomAccessFile(filepath, "rw");
		long len = file.length();
		long start = file.getFilePointer();
		long nextEnd = start + len - 1;
		byte[] buf = new byte[1];
		file.seek(nextEnd);
		file.read(buf, 0, 1);
		if (buf[0] == '\n')
			file.writeBytes(string);
		else
			file.writeBytes("\r\n"+string);
		file.close();
	}

	public static List<String> readSource(String path) throws IOException{
		return readSource(path, true);
	}

	public static List<String> readSource(String path, boolean skipFirstLine) throws IOException{

		return readSource(path, skipFirstLine, "utf-8");
	}

	public static List<String> readSource(String path,boolean skipFirstLine,String encode) throws IOException{

		File file = new File(path);

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),encode));
		String line = null;

		List<String> records = new ArrayList<String>();
		if(skipFirstLine){
			br.readLine();
		}

		while(null!=(line=br.readLine())){
			records.add(line);
		}
		br.close();
		return records;
	}

	public static String readLine(String path, boolean skipFirstLine, String encode, int lineIndex) throws IOException {
		File file = new File(path);

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),encode));
		String line;
		String value = null;
		int index = 0;
		if(skipFirstLine){
			value = br.readLine();
			if (index == lineIndex) {
				return value;
			}
			index++;
		}

		while(null != (line=br.readLine())){
			value = line;
			if (index == lineIndex) {
				return value;
			}
			index++;
		}
		return value;
	}

	public static void splitFile(String path, String name, int lineSize, String encode) throws IOException {
		File file = new File(path + File.separator + name);
		String filePath = file.getPath();
		if (file.isFile()) {
			int last = name.lastIndexOf(".");
			String suffix = name.substring(last);
			String prefix = name.substring(0, last) + "_";
			String targetDirPath =  path + File.separator +  name.substring(0, last);
			if (file.length() > 0) {
				File dir = new File(targetDirPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),encode));
				String line;
				int lineNum = 0;
				int fileNum = 0;
				String fileName = prefix + fileNum + suffix;
				FileWriter fw = null;
				try {
					fw = new FileWriter(targetDirPath + File.separator + fileName, true);
					while ((line = br.readLine()) != null) {
						fw.write(line + "\n");
						lineNum++;
						if (lineNum >= lineSize) {
							logger.info("file path: " + targetDirPath + ", name: " + fileName + ", line num: " + lineNum);
							fileNum++;
							lineNum = 0;
							fileName = prefix + fileNum + suffix;
							fw.close();
							fw = new FileWriter(targetDirPath + File.separator + fileName, true);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (fw != null) {
						fw.close();
					}
				}
				logger.info("file path: " + targetDirPath + ", name: " + fileName + ", line num: " + lineNum);
			} else {
				logger.warn(path + " isn't exist or is null.");
			}
		} else {
			logger.warn(path + " isn't a file.");
		}
	}


	public static void main(String[] args) throws IOException {
		FileUtil.splitFile("D:\\_com_project_info\\SEAGULL\\问题单-证据\\ho-schedule\\", "ho-schedule-01-23.log", 250000, "utf-8");
	}

}
