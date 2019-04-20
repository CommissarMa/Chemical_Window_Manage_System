package ecust.gui;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import ecust.gui.treepanel.BtnChannelPanel;
import ecust.gui.treepanel.BtnRootPanel;
import ecust.gui.treepanel.BtnWindowPanel;
import ecust.gui.treepanel.MyTreeRenderer;
import ecust.manage.CompMaintain;

import java.awt.BorderLayout;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;

/**
 * 树模块：
 * 包括带滚动面板的树（tree）和按钮模块（btnPanel）
 * @author CommissarMa
 */
public class TreePanel extends JPanel {

	/**
	 * 树模块
	 */
	private JTree tree;
	/**
	 * 放置树的滚动面板
	 */
	private JScrollPane scrollPane;
	/**
	 * 按钮模块
	 */
	private JPanel btnPanel;
	
	/**
	 * 构造TreePanel
	 */
	public TreePanel() {
		this.setLayout(new BorderLayout(0, 0));
		
		//初始化树
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("橱窗检测系统");
		tree = new JTree(root);
		tree.setShowsRootHandles(true);//显示树节点前的+（加号）按钮
		tree.setCellRenderer(new MyTreeRenderer());//设置自定义的节点绘制器
		scrollPane = new JScrollPane(tree);
		this.add(scrollPane, BorderLayout.CENTER);
		
		//初始化按钮组
		btnPanel=new JPanel();
		this.add(btnPanel,BorderLayout.SOUTH);
		
		//添加树的监听器
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				//得到当前被选中的节点
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if(node==null){//如果没有节点被选中
					//删除按钮panel中的按钮模块
					btnPanel.removeAll();
					btnPanel.updateUI();
					//恢复图片框的初始图像，并设置board不可绘制
					CompMaintain.getPicPanel().recoverPicAndDisableBoard();
				}else if(node.getLevel()==0){//根节点
					BtnRootPanel btnRootPanel=new BtnRootPanel(CompMaintain.getFrame(),tree,node);
					btnPanel.removeAll();
					btnPanel.add(btnRootPanel);
					btnPanel.updateUI();
					//恢复图片框的初始图像，并设置board不可绘制
					CompMaintain.getPicPanel().recoverPicAndDisableBoard();
//					DefaultMutableTreeNode rootnode=(DefaultMutableTreeNode)tree.getModel().getRoot();//获取根节点
//					System.out.println(rootnode.getChildCount());
				}else if(node.getLevel()==1){//通道节点
					BtnChannelPanel btnChannelPanel=new BtnChannelPanel(CompMaintain.getFrame(),tree,node,CompMaintain.getPicPanel());
					btnPanel.removeAll();
					btnPanel.add(btnChannelPanel);
					btnPanel.updateUI();
					if(node!=CompMaintain.getCurrentChannel()){//如果选择的通道不是当前通道
						//恢复图片框的初始图像，并设置board不可绘制
						CompMaintain.getPicPanel().recoverPicAndDisableBoard();
					}
				}else if(node.getLevel()==2){//橱窗节点
					if(node.getParent()==CompMaintain.getCurrentChannel()){
						System.out.println("当前橱窗属于当前通道");
						//设置TreePanel中的btnPanel（按钮模块）
						BtnWindowPanel btnWindowPanel=new BtnWindowPanel(CompMaintain.getFrame(),tree,node,CompMaintain.getPicPanel());
						btnPanel.removeAll();
						btnPanel.add(btnWindowPanel);
						btnPanel.updateUI();
					}else{
						//恢复图片框的初始图像，并设置board不可绘制
						CompMaintain.getPicPanel().recoverPicAndDisableBoard();
						//警告框
						JOptionPane.showMessageDialog(null, "请先连接该橱窗所属的通道！", "中科院橱窗检测系统提示", JOptionPane.INFORMATION_MESSAGE);
						btnPanel.removeAll();
						btnPanel.updateUI();
					}
					
				}
			}
		});
	}
	
	public void setTree(JTree tree){
		this.tree=tree;
	}
	
	public JTree getTree(){
		if(this.tree==null){
			System.out.println("TreePanel.tree为null");
			return null;
		}else{
			return this.tree;
		}
	}
	
	public void setBtnPanel(JPanel btnPanel){
		this.btnPanel=btnPanel;
	}
	
	public JPanel getBtnPanel(){
		if(this.btnPanel==null){
			System.out.println("TreePanel.btnPanel为null");
			return null;
		}else{
			return btnPanel;
		}
	}
}
