package ecust.manage;

import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;

import ecust.gui.MMenuBar;
import ecust.gui.MessagePanel;
import ecust.gui.PicPanel;
import ecust.gui.TreePanel;

/**
 * ���ά���ࣺ
 * ����ά����Ҫ�������������
 * @author CommissarMa
 */
public class CompMaintain {
	/**
	 * ������
	 */
	private static JFrame frame=null;
	/**
	 * �˵���
	 */
	private static MMenuBar mMenuBar=null;
	/**
	 * ͼ��ģ��
	 */
	private static PicPanel picPanel=null;
	/**
	 * ��ģ��
	 */
	private static TreePanel treePanel=null;
	/**
	 * ��ǰͨ��
	 */
	private static DefaultMutableTreeNode currentChannel=null;
	/**
	 * �ϴ�����״̬�Ľӿ�url
	 */
	private static String dataUrl=null;
	/**
	 * ��Ϣ���
	 */
	private static MessagePanel messagePanel=null;
	
	public static void setFrame(JFrame frame){
		CompMaintain.frame=frame;
	}
	
	public static JFrame getFrame(){
		if(frame==null){
			System.out.println("CompMaintain.frameΪnull");
			return null;
		}else{
			return frame;
		}
	}
	
	public static void setMMenuBar(MMenuBar mMenuBar){
		CompMaintain.mMenuBar=mMenuBar;
	}
	
	public static MMenuBar getMMenuBar(){
		if(mMenuBar==null){
			System.out.println("CompMaintain.mMenuBarΪnull");
			return null;
		}else{
			return mMenuBar;
		}
	}
	
	public static void setPicPanel(PicPanel picPanel){
		CompMaintain.picPanel=picPanel;
	}
	
	public static PicPanel getPicPanel(){
		if(picPanel==null){
			System.out.println("CompMaintain.picPanelΪnull");
			return null;
		}else{
			return picPanel;
		}
	}
	
	public static void setTreePanel(TreePanel treePanel){
		CompMaintain.treePanel=treePanel;
	}
	
	public static TreePanel getTreePanel(){
		if(treePanel==null){
			System.out.println("CompMaintain.treePanelΪnull");
			return null;
		}else{
			return treePanel;
		}
	}
	
	public static void setCurrentChannel(DefaultMutableTreeNode currentChannel){
		CompMaintain.currentChannel=currentChannel;
	}
	
	public static DefaultMutableTreeNode getCurrentChannel(){
		if(currentChannel==null){
//			System.out.println("CompMaintain.currentChannelΪnull");
			return null;
		}else{
			return currentChannel;
		}
	}
	
	public static void setDataUrl(String dataUrl){
		CompMaintain.dataUrl=dataUrl;
	}
	
	public static String getDataUrl(){
		return dataUrl;
	}
	
	public static void setMessagePanel(MessagePanel messagePanel){
		CompMaintain.messagePanel=messagePanel;
	}
	
	public static MessagePanel getMessagePanel(){
		if(messagePanel==null){
//			System.out.println("CompMaintain.messagePanelΪnull");
			return null;
		}else{
			return messagePanel;
		}
	}
}
