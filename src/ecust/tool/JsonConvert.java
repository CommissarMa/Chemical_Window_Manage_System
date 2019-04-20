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
 * Json与JTree相互转换类
 * @author Commissarma
 */
public class JsonConvert {
	/**
	 * 将json字符串转换成通道节点添加到树视图中
	 * @param jsonString Json字符串
	 */
	public static void json2Tree(String jsonString){
		//获取树
		JTree tree=CompMaintain.getTreePanel().getTree();
		//获取根节点
		DefaultMutableTreeNode rootNode=(DefaultMutableTreeNode)tree.getModel().getRoot();
		//清除所有通道
		rootNode.removeAllChildren();
		//恢复PicPanel
		CompMaintain.getPicPanel().recoverPicAndDisableBoard();
		//清除btnPanel
		CompMaintain.getTreePanel().getBtnPanel().removeAll();
		CompMaintain.getTreePanel().getBtnPanel().updateUI();
		
		try{
		JSONObject json= new JSONObject(jsonString);
		JSONArray jChannelArray=json.getJSONArray("channelarray");
		//遍历所有通道
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
			JOptionPane.showMessageDialog(null, "Json配置文件解析失败，请删除该配置文件并人工进行配置！", "中科院橱窗检测系统提示", JOptionPane.INFORMATION_MESSAGE);
		}
		
		tree.updateUI();
	}
	
	/**
	 * 将树的所有元素保存成json格式
	 * @param tree
	 * @return json字符串
	 */
	public static String tree2Json(JTree tree){
		//得到根节点
		DefaultMutableTreeNode rootNode=(DefaultMutableTreeNode)tree.getModel().getRoot();
		JSONObject json=new JSONObject();//Json对象
		JSONArray jChannelArray = new JSONArray();//通道数组
		
		//遍历所有通道
		for(int i=0;i<rootNode.getChildCount();i++){
			//得到通道节点以及通道对象
			DefaultMutableTreeNode channelNode=(DefaultMutableTreeNode)rootNode.getChildAt(i);
			Channel channel=(Channel)channelNode.getUserObject();
//			System.out.println(channel.toString());
			JSONObject jChannel=new JSONObject();//通道对象
			jChannel.put("name", channel.getName());
			jChannel.put("config", channel.getConfig());
			JSONArray jWindowArray = new JSONArray();//橱窗数组
			
			//遍历该通道下的所有橱窗
			for(int j=0;j<channelNode.getChildCount();j++){
				DefaultMutableTreeNode windowNode=(DefaultMutableTreeNode)channelNode.getChildAt(j);
				Window window=(Window)windowNode.getUserObject();
//				System.out.println(window.toString());
				JSONObject jWindow=new JSONObject();//橱窗对象
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
	 * 弹出保存文件对话框，把从tree转来的Json字符串存入文件
	 * @param JsonStr
	 */
	public static void showSaveConfig(String JsonStr){
		FileNameExtensionFilter filter=new FileNameExtensionFilter("*.json","json");  
        JFileChooser fc=new JFileChooser();  
        fc.setFileFilter(filter);  
        fc.setMultiSelectionEnabled(false);//只能选一个文件
        fc.setCurrentDirectory(new File("配置文件存放目录"));//设置默认路径
        int result=fc.showSaveDialog(null); 
        if (result==JFileChooser.APPROVE_OPTION) {  
            File file=fc.getSelectedFile();  
            if (!file.getPath().endsWith(".json")) {  
                file=new File(file.getPath()+".json");  
            }
            System.out.println("file path="+file.getPath());  
            FileOutputStream fos=null;  
            try {  
                if (!file.exists()) {//文件不存在 则创建一个 
                    file.createNewFile();  
                }  
                fos=new FileOutputStream(file);  
                //写入
                fos.write((JsonStr+"\r\n"+CompMaintain.getDataUrl()).getBytes());
                fos.flush();  
            } catch (IOException e) {  
                System.err.println("文件创建失败：");  
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
	 * 显示加载配置的对话框，并调用JsonConvert.json2Tree(str)更改树视图
	 */
	public static void showLoadConfig(){
		JFileChooser jfc=new JFileChooser();
		FileNameExtensionFilter filter=new FileNameExtensionFilter("*.json","json");  
		jfc.setFileFilter(filter);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY); 
        jfc.setCurrentDirectory(new File("配置文件存放目录"));//设置默认路径
        jfc.showDialog(new JLabel(), "选择");  
        File file=jfc.getSelectedFile();
        if(file!=null){
        	try {
        		InputStreamReader reader = new InputStreamReader(  
        				new FileInputStream(file)); // 建立一个输入流对象reader  
        		BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
        		//读入文件中的第一行
        		String line1 = "";
        		line1 = br.readLine();
        		//加载到树中
        		JsonConvert.json2Tree(line1);
        		//读入文件第二行
        		String line2="";
        		line2=br.readLine();
        		CompMaintain.setDataUrl(line2);//把第二行设置为dataUrl（数据接口Url）
//            	while (line != null) {  
//                	line = br.readLine(); // 一次读入一行数据  
//            	}  
        	} catch (IOException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        }
	}
	
}
