package com.cnvp.paladin.service;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.cnvp.paladin.kit.ConfigKit;
import com.jfinal.kit.PathKit;

public class DbService {
	
	private static String sp = File.separator;
    /**
     * @param path URL绝对路径
     * @throws Exception
     */
    public static void backup(String path) throws Exception{
    	//转换为物理路径
    	path = PathKit.getWebRootPath() + path;
        System.out.println(path);        
        //判断目录是否存在，不存在则新建
        File pathObj =new File(path);
        if  (!pathObj .exists()  && !pathObj .isDirectory()) 
        	pathObj .mkdir();
        //生成sql文件名
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String sqlFileName = formatter.format(new  Date(System.currentTimeMillis()))+".sql";
        //生成sql文件完整物理路径
        String sqlFile = path+sp+sqlFileName;
        sqlFile = sqlFile.replace("/", sp).replace("\\", sp);
        System.out.println(sqlFile);
        // 这里是读取的属性文件，也可以直接使用
        String username = ConfigKit.get("jdbc.username");
        String password = ConfigKit.get("jdbc.password");
        String dbName = ConfigKit.get("jdbc.dbName");
        String cmdTxt_no = "mysqldump -u "+username+" -p"+password+" --default-character-set=utf8 --lock-all-tables=true "+dbName+" > "+ sqlFile;
        StringBuilder cmdTxt = new StringBuilder();
        cmdTxt
        .append("mysqldump")
        .append(" --opt")
//        .append(" -h").append(hostIP)
        .append(" --user=").append(username) 
        .append(" --password=").append(username)
        .append(" --lock-all-tables=true")
        .append(" --result-file=").append(sqlFile)
        .append(" --default-character-set=utf8 ")
        .append(dbName);
        System.out.println(cmdTxt);
        System.out.println(cmdTxt_no);
        
    	Runtime rt = Runtime.getRuntime();
//        //-u后面是用户名，-p是密码-p后面最好不要有空格，-family是数据库的名字
    	Process process = rt.exec(cmdTxt_no);    	
        InputStream	inputStream = process.getInputStream();
    	Scanner scanner = new Scanner(inputStream, "UTF-8");
        String text = scanner.useDelimiter("\\A").next();
        System.out.println(text);
        scanner.close();
//        InputStream inputStream = process.getInputStream();//得到输入流，写成.sql文件
//        InputStreamReader reader = new InputStreamReader(inputStream);
//        BufferedReader br = new BufferedReader(reader);
//        String s = null;
//        StringBuffer sb = new StringBuffer();
//        while((s = br.readLine()) != null){
//            sb.append(s+"\r\n");
//        }
//        s = sb.toString();
//        
//        File file = new File(sqlFile);
//        file.getParentFile().mkdirs();
//        FileOutputStream fileOutputStream = new FileOutputStream(file);
//        fileOutputStream.write(s.getBytes());
//        fileOutputStream.close();
//        br.close();
//        reader.close();
//        inputStream.close();
    }
    public static void backup() throws Exception{
    	backup("/db_bak");
    }
     
    /**
     * 恢复备份
     * @throws Exception
     */
    public static void recover() throws Exception{
        String path="mysql -uroot -padmin wj < F:\\MySQl\\loowj.sql";
        //注意文件路径中存在空格,需要再空格地方加上" "引上空格
        java.lang.Runtime.getRuntime().exec("cmd.exe /c C:\\Program\" \"Files\\MySQL5\\bin\\"+path);
    }
     
    public static void main(String[] args) {
        try {
            backup("loowj.sql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
