package ecust.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;

import org.opencv.core.Core;

import ecust.gui.MMenuBar;
import ecust.gui.MessagePanel;
import ecust.gui.PicPanel;
import ecust.gui.TreePanel;
import ecust.manage.CompMaintain;
import ecust.thread.DetectThread;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame {
	/**
	 * ������Ŀ��
	 */
	private static final int MAINFRAME_WIDTH=1200;
	/**
	 * ������ĸ߶�
	 */
	private static final int MAINFRAME_HEIGHT=800;

	/**
	 * ������
	 */
	private JFrame frame;
	/**
	 * �˵���
	 */
	private MMenuBar mMenuBar;
	/**
	 * ͼ��ģ��
	 */
	private PicPanel picPanel;
	/**
	 * ��ģ��
	 */
	private TreePanel treePanel;
	
	/**
	 * ��ʼ��ⰴť
	 */
	private JButton btnStart;
	
	/**
	 * ������ⰴť
	 */
	private JButton btnEnd;
	/**
	 * ��������߳�
	 */
	private DetectThread dt;
	/**
	 * ��Ϣ���
	 */
	private MessagePanel messagePanel;

	static{
		//����opencv��
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	/**
	 * Main��������Ӧ��
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					//���캯�����֮��Ż���ʾ���������������ܵõ����е��������
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * ����Ӧ�õĹ��캯��
	 */
	public MainFrame() {
		setWindowsUIStyle();
		initFrame();
		initMMenuBar();
		initPicPanel();
		initTreePanel();
		initBtn();
		initMessagePanel();
	}
	
	/**
	 * ����windows UI���
	 */
	private void setWindowsUIStyle(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "��ǰϵͳ��֧��Windows������", "�������ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * ��ʼ��frame�������壩
	 */
	private void initFrame() {
		frame = new JFrame();
		//��ȡ��Ļ��С
	    Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
	    if(screenSize.width<MAINFRAME_WIDTH || screenSize.height<MAINFRAME_HEIGHT){
	    	JOptionPane.showMessageDialog(null, "��ʾ���ֱ��ʹ�С���Ƽ�"+MAINFRAME_WIDTH+"*"+MAINFRAME_HEIGHT+"����", "�������ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
	    	frame.setBounds(0, 0, MAINFRAME_WIDTH, MAINFRAME_HEIGHT);
	    }
	    else{
	    	//������ʾ������
	    	frame.setBounds((int)((screenSize.width-MAINFRAME_WIDTH)*1.0/2), (int)((screenSize.height-MAINFRAME_HEIGHT)*1.0/2), MAINFRAME_WIDTH, MAINFRAME_HEIGHT);
	    }
	    frame.setTitle("��������ϵͳ");//����ı���
		frame.getContentPane().setLayout(null);//���þ��Բ���
		frame.setResizable(true);//���Ըı䴰���С
		frame.getContentPane().setBackground(Color.WHITE);//����ɫ����ɫ
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/ecust/resource/ecustlogo.png")));//���ñ���ͼ��
		frame.setMinimumSize(new Dimension(MAINFRAME_WIDTH, MAINFRAME_HEIGHT));//���ô������С�ߴ�
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				mMenuBar.setBounds(0, 0, frame.getWidth(), 30);
			}
		});
		
		//����frame��������ӵ�CompMaintain��
		CompMaintain.setFrame(frame);
	}

	/**
	 * ��ʼ��mMenuBar���˵�����
	 */
	private void initMMenuBar(){
		mMenuBar=new MMenuBar();
		mMenuBar.setBounds(0, 0, MAINFRAME_WIDTH, 30);
		frame.getContentPane().add(mMenuBar);
		
		//����mMenuBar��������ӵ�CompMaintain��
		CompMaintain.setMMenuBar(mMenuBar);
	}

	/**
	 * ��ʼ��picPanel��ͼ��ģ�飩
	 */
	private void initPicPanel(){
		picPanel=new PicPanel();
		picPanel.setBounds(5, 35, 800, 600);
		frame.getContentPane().add(picPanel);
		
		//����picPanel��������ӵ�CompMaintain��
		CompMaintain.setPicPanel(picPanel);
	}
	
	/**
	 * ��ʼ��treePanel����ģ�飩
	 */
	private void initTreePanel(){
		treePanel=new TreePanel();
		treePanel.setBounds(810, 35, 380, 600);
		frame.getContentPane().add(treePanel);
		
		//����treePanel��������ӵ�CompMaintain��
		CompMaintain.setTreePanel(treePanel);
	}
	
	/**
	 * ��ʼ����ʼ��ť�ͽ�����ť
	 */
	private void initBtn(){
		//��ʼ��ť
		btnStart = new JButton("");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//���ж��ǲ�����ͨ���г���Ҫ��⣬�����ϴ��ӿ��ǲ������ú���
				if(!hasWindow()){
					JOptionPane.showMessageDialog(null, "��ǰû�г�����Ҫ��⣡", "�������ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(CompMaintain.getDataUrl()==null || CompMaintain.getDataUrl().equals("")){
					JOptionPane.showMessageDialog(null, "�ӿ�Urlδ���ã�����ѡ��˵��������á�", "�������ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				//��ʼ��picPanel
				CompMaintain.getPicPanel().recoverPicAndDisableBoard();
				//��Ϣ���ɼ�
				messagePanel.setVisible(true);
				//�����ɲ���
				CompMaintain.getTreePanel().getTree().setEnabled(false);
				CompMaintain.getTreePanel().getBtnPanel().setVisible(false);
				//�߳���
				dt=new DetectThread();
				dt.start();
				//������ť�ɼ�����ʼ��ť���ɼ�
				btnEnd.setVisible(true);
				btnStart.setVisible(false);
			}
		});
		btnStart.setToolTipText("��ʼ�������");
		btnStart.setIcon(new ImageIcon(MainFrame.class.getResource("/ecust/resource/start.png")));
		btnStart.setBounds(5, 650, 100, 100);
		frame.getContentPane().add(btnStart);
		
		//������ť
		btnEnd = new JButton("");
		btnEnd.setVisible(false);//�������ʱ������ť���ɼ�
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//�����߳�
				dt.stop();
				//��Ϣ��岻�ɼ�
				messagePanel.setVisible(false);
				//���ɲ���
				CompMaintain.getTreePanel().getTree().setEnabled(true);
				CompMaintain.getTreePanel().getBtnPanel().setVisible(true);
				//��ʼ��ť�ɼ���������ť���ɼ�
				btnStart.setVisible(true);
				btnEnd.setVisible(false);
			}
		});
		btnEnd.setToolTipText("ֹͣ�������");
		btnEnd.setIcon(new ImageIcon(MainFrame.class.getResource("/ecust/resource/end.png")));
		btnEnd.setBounds(120, 650, 100, 100);
		frame.getContentPane().add(btnEnd);
	}
	
	/**
	 * ��ʼ����Ϣ���
	 */
	private void initMessagePanel(){
		messagePanel=new MessagePanel();
		messagePanel.setBounds(240, 650, 565, 100);
		frame.getContentPane().add(messagePanel);
		
		//�����ñ��浽CompMaintain��
		CompMaintain.setMessagePanel(messagePanel);
	}
	
	/**
	 * �жϵ�ǰ�����Ƿ��г����ڵ�
	 * @return true�����У�false����û��
	 */
	private boolean hasWindow(){
		//��ȡ��
		JTree tree=CompMaintain.getTreePanel().getTree();
		//��ȡ���ĸ��ڵ�
		DefaultMutableTreeNode rootNode=(DefaultMutableTreeNode)tree.getModel().getRoot();
		for(int i=0;i<rootNode.getChildCount();i++){
			DefaultMutableTreeNode channelNode=(DefaultMutableTreeNode)rootNode.getChildAt(i);
			for(int j=0;j<channelNode.getChildCount();j++){
				return true;
			}
		}
		return false;
	}
}
