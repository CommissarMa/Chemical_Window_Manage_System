package ecust.thread;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import ecust.entity.Channel;
import ecust.entity.Window;
import ecust.manage.CompMaintain;
import ecust.tool.SendMessage;
import ecust.tool.Tool;

/**
 * 橱窗检测线程
 * @author CommissarMa
 */
public class DetectThread extends Thread{
	/**
	 * 线程任务
	 */
	public void run(){
		while(true){
			//获取树
			JTree tree=CompMaintain.getTreePanel().getTree();
			//获取树的根节点
			DefaultMutableTreeNode rootNode=(DefaultMutableTreeNode)tree.getModel().getRoot();
			//更新信息栏
			CompMaintain.getMessagePanel().getTextArea().setText("===============信息栏==============="+"\r\n");
			
			//遍历根节点下的所有通道
			for(int i=0;i<rootNode.getChildCount();i++){
				//获取当前通道节点
				DefaultMutableTreeNode channelNode=(DefaultMutableTreeNode)rootNode.getChildAt(i);
				//获取通道对象
				Channel channel=(Channel)channelNode.getUserObject();
				//更新信息栏
				CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"连接通道："+channel.getName()+"……\r\n");
				//连接通道获取当前监控图像
				VideoCapture capture=new VideoCapture();
				capture.open(channel.getConfig());
				if(capture.isOpened()){
					Mat mat=new Mat();
					capture.read(mat);
					capture.release();
					if(!mat.empty()){
						CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"通道连接成功！\r\n");
						Imgproc.resize(mat, mat, new Size(800,600));
						Imgproc.GaussianBlur(mat, mat, new Size(3,3),0,0); 
						Mat hsv=new Mat();
				        Imgproc.cvtColor(mat, hsv, Imgproc.COLOR_BGR2HSV);  
				        Mat thresholded=new Mat();
				        Core.inRange(hsv, new Scalar(0,90,90), new Scalar(20,255,255), thresholded);
						//遍历当前通道下的所有橱窗
						for(int j=0;j<channelNode.getChildCount();j++){
							//获取当前橱窗节点
							DefaultMutableTreeNode windowNode=(DefaultMutableTreeNode)channelNode.getChildAt(j);
							//获取橱窗对象
							Window window=(Window)windowNode.getUserObject();
							//检测当前橱窗状态
							int redPixelCount=0;//红色像素点数量
							String windowState="0";//0打开，1关闭
							String windowStateText="打开";
							for(int r=window.getY();r<window.getY()+window.getHeight();r++){
								for(int c=window.getX();c<window.getX()+window.getWidth();c++){
									if(thresholded.get(r, c)[0]>0){
		    	        				redPixelCount++;
		    	        			}
								}
							}
							System.out.println(window.getName()+redPixelCount);
							if(redPixelCount>=100){
								windowState="1";//有红条代表关闭
								windowStateText="关闭";
							}
							Imgcodecs.imwrite("temp/temp.jpg", mat);//保存图像
							//更新信息栏
							CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"橱窗（id："+window.getId()+"）的检测结果："+windowStateText+"  消息发送中……\r\n");
							//发送消息到数据接口
							String datetime=SendMessage.GetDateTime();//当前日期时间
							String postStr=SendMessage.getJson(window.getId(), windowState, SendMessage.GetImageStr("temp/temp.jpg"), datetime);
							try {
								String response=SendMessage.post(CompMaintain.getDataUrl(),postStr);
								System.out.println(response+"  "+windowState);
								//更新信息栏
								if(response.equals("{\"error\":0,\"message\":\"上传成功\"}")){
									CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"消息发送成功！\r\n");
								}else{
									CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"消息发送失败！请检查接口Url配置或网络设置。\r\n");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"消息发送失败！请检查接口Url配置或网络设置。\r\n");
							}
							//写入日志文件
							String logStr=datetime+"  "+windowState+"  "+window.getId()+"\r\n";
							try{
								File file=new File("日志.txt");
								if(!file.exists()){
									file.createNewFile();
								}
								//append方式
								FileWriter fileWritter = new FileWriter(file.getName(),true);
					            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					            bufferWritter.write(logStr);
					            bufferWritter.close();
							}catch(IOException e){
								e.printStackTrace();
							}
						}
					}else{
						//更新信息栏,mat为空也是通道连接失败
						CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"通道连接失败！请检查通道配置或网络设置。\r\n");
					
					}
				}else{
//					JOptionPane.showMessageDialog(null, "通道连接失败", "中科院橱窗检测系统提示", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("通道："+channel.getName()+" 连接失败！");
					//更新信息栏
					CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"通道连接失败！请检查通道配置或网络设置。\r\n");
				}
			}
			
			//延时5分钟
			try {
				int min=5;//5分钟
				for(int i=min;i>0;i--){
					//更新信息栏
					CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"距离下次检测还有"+i+"分钟……\r\n");
					this.sleep(1000*60);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
