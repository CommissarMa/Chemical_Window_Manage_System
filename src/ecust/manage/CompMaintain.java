package ecust.manage;

import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;

import ecust.gui.MMenuBar;
import ecust.gui.MessagePanel;
import ecust.gui.PicPanel;
import ecust.gui.TreePanel;

/**
 * 组件维护类：
 * 用于维护主要界面组件的引用
 * @author CommissarMa
 */
public class CompMaintain {
	/**
	 * 主窗体
	 */
	private static JFrame frame=null;
	/**
	 * 菜单栏
	 */
	private static MMenuBar mMenuBar=null;
	/**
	 * 图像模块
	 */
	private static PicPanel picPanel=null;
	/**
	 * 树模块
	 */
	private static TreePanel treePanel=null;
	/**
	 * 当前通道
	 */
	private static DefaultMutableTreeNode currentChannel=null;
	/**
	 * 上传橱窗状态的接口url
	 */
	private static String dataUrl=null;
	/**
	 * 信息面板
	 */
	private static MessagePanel messagePanel=null;
	
	public static void setFrame(JFrame frame){
		CompMaintain.frame=frame;
	}
	
	public static JFrame getFrame(){
		if(frame==null){
			System.out.println("CompMaintain.frame为null");
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
			System.out.println("CompMaintain.mMenuBar为null");
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
			System.out.println("CompMaintain.picPanel为null");
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
			System.out.println("CompMaintain.treePanel为null");
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
//			System.out.println("CompMaintain.currentChannel为null");
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
//			System.out.println("CompMaintain.messagePanel为null");
			return null;
		}else{
			return messagePanel;
		}
	}
}
