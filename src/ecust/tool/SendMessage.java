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
 * ��Ϣ������
 * @author CommissarMa
 */
public class SendMessage {
	
	/**
	 * post����
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
	 * ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��
	 * @param imgFilePath
	 * @return
	 */
	public static String GetImageStr(String imgFilePath) {
	    byte[] data = null;
	     
	    // ��ȡͼƬ�ֽ�����
	    try {
	      InputStream in = new FileInputStream(imgFilePath);
	      data = new byte[in.available()];
	      in.read(data);
	      in.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	     
	    // ���ֽ�����Base64����
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);// ����Base64��������ֽ������ַ���
	}
	
	public static String GetDateTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		return df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
	}
	
	public static String getJson(String ID,String State,String Picture,String LoadTime){
		String str="{\"ID\":\""+ID+"\","+"\"State\":\""+State+"\","+"\"Picture\":\""+Picture+"\","+"\"LoadTime\":\""+LoadTime+"\"}";
		return str;
	}
}
