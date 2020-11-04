package com.ali.retail.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileSplitUtil {

    /**
     * 切割指定源文件，并输出到指定目录，按默认大小切割
     * @param srcFile 指定要切割的源文件
     * @param outputDir 指定输出目录
     * @param size 文件大小，单位：MB
     * @throws IOException 有异常时抛出，由调用者处理
     */
    public static void split(String srcFile, String outputDir, int size) throws IOException {
        File f = new File(srcFile);
        String format = srcFile.substring(srcFile.lastIndexOf("."));
        FileInputStream in = new FileInputStream(f);
        FileOutputStream out = null;
        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = null;
        // 将MB单位转为为字节B
        long m = size * 1024 * 1024;
        // 计算最终会分成几个文件
        int count = (int) (f.length() / m);
        // System.out.println(f.length() + " " + m + " " + count);
        for (int i = 0; i <= count; i++) {
            // 生成文件的路径
            String t = outputDir + "split_" + i + format;
            try {
                out = new FileOutputStream(new File(t));
                outChannel = out.getChannel();
                // 从inChannel的m*i处，读取固定长度的数据，写入outChannel
                if (i != count)
                    inChannel.transferTo(m * i, m, outChannel);
                else// 最后一个文件，大小不固定，所以需要重新计算长度
                    inChannel.transferTo(m * i, f.length() - m * count, outChannel);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                out.close();
                outChannel.close();
            }
        }
        in.close();
        inChannel.close();
    }

    public static void splitFile(String absInputFilePath, int splitNum) throws IOException{
        File inputFile = new File(absInputFilePath);
        String inputFileName = inputFile.getName();
        String dirPath = absInputFilePath.substring(0, absInputFilePath.lastIndexOf(inputFileName));
        splitFile(dirPath, dirPath, inputFileName, "tmp_", splitNum);
    }

    public static void splitFile(String inputDirPath, String outputDirPath, String inputFileName, String tmpFileNamePrefix, int splitNum) throws IOException {
        inputDirPath = inputDirPath.endsWith(File.separator) ? inputDirPath : inputDirPath + File.separator;
        outputDirPath = outputDirPath.endsWith(File.separator) ? outputDirPath : outputDirPath + File.separator;
        FileInputStream fis = new FileInputStream(inputDirPath + inputFileName);
        FileChannel inputChannel = fis.getChannel();
        final long fileSize = inputChannel.size();
        long average = fileSize / splitNum;//平均文件大小
        long bufferSize = 4096; //缓存块大小，自行调整
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.parseInt(bufferSize + "")); // 申请一个缓存区
        long startPosition = 0; //子文件开始位置
        long endPosition = average < bufferSize ? 0 : average - bufferSize;//子文件结束位置
        for (int i = 0; i < splitNum; i++) {
            if (i + 1 != splitNum) {
                int read = inputChannel.read(byteBuffer, endPosition);// 读取数据
                readW:
                while (read != -1) {
                    byteBuffer.flip();//切换读模式
                    byte[] array = byteBuffer.array();
                    for (int j = 0; j < array.length; j++) {
                        byte b = array[j];
                        if (b == 10 || b == 13) { //判断\n\r
                            endPosition += j;
                            break readW;
                        }
                    }
                    endPosition += bufferSize;
                    byteBuffer.clear(); //重置缓存块指针
                    read = inputChannel.read(byteBuffer, endPosition);
                }
            }else{
                endPosition = fileSize; //最后一个文件直接指向文件末尾
            }

            int i1 = inputFileName.lastIndexOf(".");
            String fileFormat = inputFileName.substring(i1);
            String tmpName = outputDirPath + tmpFileNamePrefix + (i + 1) + fileFormat;
            System.out.println("begin to write file: " + tmpName);
            FileOutputStream fos = new FileOutputStream(tmpName);

            FileChannel outputChannel = fos.getChannel();
            inputChannel.transferTo(startPosition, endPosition - startPosition, outputChannel);//通道传输文件数据
            outputChannel.close();
            fos.close();
            startPosition = endPosition + 1;
            endPosition += average;
        }
        inputChannel.close();
        fis.close();
    }

    public static void merge(String from, String to) throws IOException {
        File t = new File(to);
        FileInputStream in = null;
        FileChannel inChannel = null;

        FileOutputStream out = new FileOutputStream(t, true);
        FileChannel outChannel = out.getChannel();

        File f = new File(from);
        // 获取目录下的每一个文件名，再将每个文件一次写入目标文件
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            // 记录新文件最后一个数据的位置
            long start = 0;
            for (File file : files) {

                in = new FileInputStream(file);
                inChannel = in.getChannel();

                // 从inChannel中读取file.length()长度的数据，写入outChannel的start处
                outChannel.transferFrom(inChannel, start, file.length());
                start += file.length();
                in.close();
                inChannel.close();
            }
        }
        out.close();
        outChannel.close();
    }

    public static void main(String[] args) throws IOException {
        splitFile("D:\\_com_project_info\\KYS\\log\\api.log", 10);
    }

}
