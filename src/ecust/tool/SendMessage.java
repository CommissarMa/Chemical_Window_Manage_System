package ecust.tool;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sun.misc.BASE64Encoder;

/**
 * 消息发送类
 * @author CommissarMa
 */
public class SendMessage {
	
	/**
	 * post方法
	 * @param url
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, String json) throws IOException {
		final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
	
	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @param imgFilePath
	 * @return
	 */
	public static String GetImageStr(String imgFilePath) {
	    byte[] data = null;
	     
	    // 读取图片字节数组
	    try {
	      InputStream in = new FileInputStream(imgFilePath);
	      data = new byte[in.available()];
	      in.read(data);
	      in.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	     
	    // 对字节数组Base64编码
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
	
	public static String GetDateTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}
	
	public static String getJson(String ID,String State,String Picture,String LoadTime){
		String str="{\"ID\":\""+ID+"\","+"\"State\":\""+State+"\","+"\"Picture\":\""+Picture+"\","+"\"LoadTime\":\""+LoadTime+"\"}";
		return str;
	}
}
