package com.ifp.wechat.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.ifp.wechat.util.WeixinUtil;

/**
 * 文件上传下载
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class FileService {

	public static Logger log = Logger.getLogger(FileService.class);

	/**
	 * 上传文件URL
	 */
	private static String uploadFileUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	/**
	 * 下载文件URL
	 */
	private static String dwonloadFileURL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	/**
	 * 上传多媒体文件
	 * 
	 * @param fileType
	 *            文件类型,分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param filename
	 *            文件名称
	 * @param filePath
	 *            文件路径
	 * @return json
	 */
	public static JSONObject uploadFile(String fileType, String filename, String filePath) {

		String requestUrl = uploadFileUrl.replace("ACCESS_TOKEN",
				WeixinUtil.getToken()).replace("TYPE", fileType);
		File file = new File(filePath);
		String result = "";
		String end = "\r\n";
		String twoHyphens = "--"; // 用于拼接
		String boundary = "*****"; // 用于拼接 可自定义
		URL submit = null;
		try {
			submit = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) submit
					.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			// 获取输出流对象，准备上传文件
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			dos.writeBytes("Content-Disposition: form-data; name=\"" + file
					+ "\";filename=\"" + filename + ";filelength=\"" + filePath
					+ ";" + end);
			dos.writeBytes(end);
			// 对文件进行传输
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);
			}
			fis.close(); // 关闭文件流

			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			result = br.readLine();
			dos.close();
			is.close();
		} catch (MalformedURLException e) {
			log.error("File upload fail..." + e);
		} catch (IOException e) {
			log.error("File upload fail..." + e);
		}
		return JSONObject.fromObject(result);
	}

	/**
	 * 下载多媒体文件
	 * 
	 * @param mediaId
	 * @return 媒体url地址
	 */
	public static String downloadFile(String mediaId) {
		return dwonloadFileURL.replace("ACCESS_TOKEN", WeixinUtil.getToken())
				.replace("MEDIA_ID", mediaId);
	}

}
