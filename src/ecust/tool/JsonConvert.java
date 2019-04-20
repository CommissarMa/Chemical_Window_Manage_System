package ecust.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ecust.entity.Channel;
import ecust.entity.Window;
import ecust.manage.CompMaintain;

/**
 * Json��JTree�໥ת����
 * @author Commissarma
 */
public class JsonConvert {
	/**
	 * ��json�ַ���ת����ͨ���ڵ���ӵ�����ͼ��
	 * @param jsonString Json�ַ���
	 */
	public static void json2Tree(String jsonString){
		//��ȡ��
		JTree tree=CompMaintain.getTreePanel().getTree();
		//��ȡ���ڵ�
		DefaultMutableTreeNode rootNode=(DefaultMutableTreeNode)tree.getModel().getRoot();
		//�������ͨ��
		rootNode.removeAllChildren();
		//�ָ�PicPanel
		CompMaintain.getPicPanel().recoverPicAndDisableBoard();
		//���btnPanel
		CompMaintain.getTreePanel().getBtnPanel().removeAll();
		CompMaintain.getTreePanel().getBtnPanel().updateUI();
		
		try{
		JSONObject json= new JSONObject(jsonString);
		JSONArray jChannelArray=json.getJSONArray("channelarray");
		//��������ͨ��
		for(int i=0;i<jChannelArray.length();i++){
			JSONObject jChannel=(JSONObject)jChannelArray.get(i);
			String name=(String)jChannel.get("name");
			String config=(String)jChannel.get("config");
			Channel channel=new Channel(name,config);
			DefaultMutableTreeNode channelNode=new DefaultMutableTreeNode(channel);
			rootNode.add(channelNode);
			JSONArray jWindowArray=jChannel.getJSONArray("windowarray");
			for(int j=0;j<jWindowArray.length();j++){
				JSONObject jWindow=(JSONObject)jWindowArray.get(j);
				String nameWindow=(String)jWindow.get("name");
				String idWindow=(String)jWindow.get("id");
				int x=(int)jWindow.get("x");
				int y=(int)jWindow.get("y");
				int width=(int)jWindow.get("width");
				int height=(int)jWindow.get("height");
				Window window=new Window(nameWindow,idWindow,x,y,width,height);
				DefaultMutableTreeNode windowNode=new DefaultMutableTreeNode(window);
				channelNode.add(windowNode);
			}
		}
		}catch(JSONException e){
//			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Json�����ļ�����ʧ�ܣ���ɾ���������ļ����˹��������ã�", "�п�Ժ�������ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
		}
		
		tree.updateUI();
	}
	
	/**
	 * ����������Ԫ�ر����json��ʽ
	 * @param tree
	 * @return json�ַ���
	 */
	public static String tree2Json(JTree tree){
		//�õ����ڵ�
		DefaultMutableTreeNode rootNode=(DefaultMutableTreeNode)tree.getModel().getRoot();
		JSONObject json=new JSONObject();//Json����
		JSONArray jChannelArray = new JSONArray();//ͨ������
		
		//��������ͨ��
		for(int i=0;i<rootNode.getChildCount();i++){
			//�õ�ͨ���ڵ��Լ�ͨ������
			DefaultMutableTreeNode channelNode=(DefaultMutableTreeNode)rootNode.getChildAt(i);
			Channel channel=(Channel)channelNode.getUserObject();
//			System.out.println(channel.toString());
			JSONObject jChannel=new JSONObject();//ͨ������
			jChannel.put("name", channel.getName());
			jChannel.put("config", channel.getConfig());
			JSONArray jWindowArray = new JSONArray();//��������
			
			//������ͨ���µ����г���
			for(int j=0;j<channelNode.getChildCount();j++){
				DefaultMutableTreeNode windowNode=(DefaultMutableTreeNode)channelNode.getChildAt(j);
				Window window=(Window)windowNode.getUserObject();
//				System.out.println(window.toString());
				JSONObject jWindow=new JSONObject();//��������
				jWindow.put("name", window.getName());
				jWindow.put("id", window.getId());
				jWindow.put("x", window.getX());
				jWindow.put("y", window.getY());
				jWindow.put("width", window.getWidth());
				jWindow.put("height", window.getHeight());
				jWindowArray.put(jWindow);
			}
			jChannel.put("windowarray", jWindowArray);
			jChannelArray.put(jChannel);
		}
		json.put("channelarray", jChannelArray);
		
		return json.toString();
	}
	
	/**
	 * ���������ļ��Ի��򣬰Ѵ�treeת����Json�ַ��������ļ�
	 * @param JsonStr
	 */
	public static void showSaveConfig(String JsonStr){
		FileNameExtensionFilter filter=new FileNameExtensionFilter("*.json","json");  
        JFileChooser fc=new JFileChooser();  
        fc.setFileFilter(filter);  
        fc.setMultiSelectionEnabled(false);//ֻ��ѡһ���ļ�
        fc.setCurrentDirectory(new File("�����ļ����Ŀ¼"));//����Ĭ��·��
        int result=fc.showSaveDialog(null); 
        if (result==JFileChooser.APPROVE_OPTION) {  
            File file=fc.getSelectedFile();  
            if (!file.getPath().endsWith(".json")) {  
                file=new File(file.getPath()+".json");  
            }
            System.out.println("file path="+file.getPath());  
            FileOutputStream fos=null;  
            try {  
                if (!file.exists()) {//�ļ������� �򴴽�һ�� 
                    file.createNewFile();  
                }  
                fos=new FileOutputStream(file);  
                //д��
                fos.write((JsonStr+"\r\n"+CompMaintain.getDataUrl()).getBytes());
                fos.flush();  
            } catch (IOException e) {  
                System.err.println("�ļ�����ʧ�ܣ�");  
                e.printStackTrace();  
            }finally{  
                if (fos!=null) {  
                    try {  
                        fos.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                } 
            }
        }  
	}
	
	/**
	 * ��ʾ�������õĶԻ��򣬲�����JsonConvert.json2Tree(str)��������ͼ
	 */
	public static void showLoadConfig(){
		JFileChooser jfc=new JFileChooser();
		FileNameExtensionFilter filter=new FileNameExtensionFilter("*.json","json");  
		jfc.setFileFilter(filter);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY); 
        jfc.setCurrentDirectory(new File("�����ļ����Ŀ¼"));//����Ĭ��·��
        jfc.showDialog(new JLabel(), "ѡ��");  
        File file=jfc.getSelectedFile();
        if(file!=null){
        	try {
        		InputStreamReader reader = new InputStreamReader(  
        				new FileInputStream(file)); // ����һ������������reader  
        		BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������  
        		//�����ļ��еĵ�һ��
        		String line1 = "";
        		line1 = br.readLine();
        		//���ص�����
        		JsonConvert.json2Tree(line1);
        		//�����ļ��ڶ���
        		String line2="";
        		line2=br.readLine();
        		CompMaintain.setDataUrl(line2);//�ѵڶ�������ΪdataUrl�����ݽӿ�Url��
//            	while (line != null) {  
//                	line = br.readLine(); // һ�ζ���һ������  
//            	}  
        	} catch (IOException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        }
	}
	
}
