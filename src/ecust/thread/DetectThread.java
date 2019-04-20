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
 * ��������߳�
 * @author CommissarMa
 */
public class DetectThread extends Thread{
	/**
	 * �߳�����
	 */
	public void run(){
		while(true){
			//��ȡ��
			JTree tree=CompMaintain.getTreePanel().getTree();
			//��ȡ���ĸ��ڵ�
			DefaultMutableTreeNode rootNode=(DefaultMutableTreeNode)tree.getModel().getRoot();
			//������Ϣ��
			CompMaintain.getMessagePanel().getTextArea().setText("===============��Ϣ��==============="+"\r\n");
			
			//�������ڵ��µ�����ͨ��
			for(int i=0;i<rootNode.getChildCount();i++){
				//��ȡ��ǰͨ���ڵ�
				DefaultMutableTreeNode channelNode=(DefaultMutableTreeNode)rootNode.getChildAt(i);
				//��ȡͨ������
				Channel channel=(Channel)channelNode.getUserObject();
				//������Ϣ��
				CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"����ͨ����"+channel.getName()+"����\r\n");
				//����ͨ����ȡ��ǰ���ͼ��
				VideoCapture capture=new VideoCapture();
				capture.open(channel.getConfig());
				if(capture.isOpened()){
					Mat mat=new Mat();
					capture.read(mat);
					capture.release();
					if(!mat.empty()){
						CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"ͨ�����ӳɹ���\r\n");
						Imgproc.resize(mat, mat, new Size(800,600));
						Imgproc.GaussianBlur(mat, mat, new Size(3,3),0,0); 
						Mat hsv=new Mat();
				        Imgproc.cvtColor(mat, hsv, Imgproc.COLOR_BGR2HSV);  
				        Mat thresholded=new Mat();
				        Core.inRange(hsv, new Scalar(0,90,90), new Scalar(20,255,255), thresholded);
						//������ǰͨ���µ����г���
						for(int j=0;j<channelNode.getChildCount();j++){
							//��ȡ��ǰ�����ڵ�
							DefaultMutableTreeNode windowNode=(DefaultMutableTreeNode)channelNode.getChildAt(j);
							//��ȡ��������
							Window window=(Window)windowNode.getUserObject();
							//��⵱ǰ����״̬
							int redPixelCount=0;//��ɫ���ص�����
							String windowState="0";//0�򿪣�1�ر�
							String windowStateText="��";
							for(int r=window.getY();r<window.getY()+window.getHeight();r++){
								for(int c=window.getX();c<window.getX()+window.getWidth();c++){
									if(thresholded.get(r, c)[0]>0){
		    	        				redPixelCount++;
		    	        			}
								}
							}
							System.out.println(window.getName()+redPixelCount);
							if(redPixelCount>=100){
								windowState="1";//�к�������ر�
								windowStateText="�ر�";
							}
							Imgcodecs.imwrite("temp/temp.jpg", mat);//����ͼ��
							//������Ϣ��
							CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"������id��"+window.getId()+"���ļ������"+windowStateText+"  ��Ϣ�����С���\r\n");
							//������Ϣ�����ݽӿ�
							String datetime=SendMessage.GetDateTime();//��ǰ����ʱ��
							String postStr=SendMessage.getJson(window.getId(), windowState, SendMessage.GetImageStr("temp/temp.jpg"), datetime);
							try {
								String response=SendMessage.post(CompMaintain.getDataUrl(),postStr);
								System.out.println(response+"  "+windowState);
								//������Ϣ��
								if(response.equals("{\"error\":0,\"message\":\"�ϴ��ɹ�\"}")){
									CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"��Ϣ���ͳɹ���\r\n");
								}else{
									CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"��Ϣ����ʧ�ܣ�����ӿ�Url���û��������á�\r\n");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"��Ϣ����ʧ�ܣ�����ӿ�Url���û��������á�\r\n");
							}
							//д����־�ļ�
							String logStr=datetime+"  "+windowState+"  "+window.getId()+"\r\n";
							try{
								File file=new File("��־.txt");
								if(!file.exists()){
									file.createNewFile();
								}
								//append��ʽ
								FileWriter fileWritter = new FileWriter(file.getName(),true);
					            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					            bufferWritter.write(logStr);
					            bufferWritter.close();
							}catch(IOException e){
								e.printStackTrace();
							}
						}
					}else{
						//������Ϣ��,matΪ��Ҳ��ͨ������ʧ��
						CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"ͨ������ʧ�ܣ�����ͨ�����û��������á�\r\n");
					
					}
				}else{
//					JOptionPane.showMessageDialog(null, "ͨ������ʧ��", "�п�Ժ�������ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("ͨ����"+channel.getName()+" ����ʧ�ܣ�");
					//������Ϣ��
					CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"ͨ������ʧ�ܣ�����ͨ�����û��������á�\r\n");
				}
			}
			
			//��ʱ5����
			try {
				int min=5;//5����
				for(int i=min;i>0;i--){
					//������Ϣ��
					CompMaintain.getMessagePanel().getTextArea().setText(CompMaintain.getMessagePanel().getTextArea().getText()+"�����´μ�⻹��"+i+"���ӡ���\r\n");
					this.sleep(1000*60);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
