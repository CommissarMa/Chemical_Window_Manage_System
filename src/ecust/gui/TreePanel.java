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
 * ��ģ�飺
 * ������������������tree���Ͱ�ťģ�飨btnPanel��
 * @author CommissarMa
 */
public class TreePanel extends JPanel {

	/**
	 * ��ģ��
	 */
	private JTree tree;
	/**
	 * �������Ĺ������
	 */
	private JScrollPane scrollPane;
	/**
	 * ��ťģ��
	 */
	private JPanel btnPanel;
	
	/**
	 * ����TreePanel
	 */
	public TreePanel() {
		this.setLayout(new BorderLayout(0, 0));
		
		//��ʼ����
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("�������ϵͳ");
		tree = new JTree(root);
		tree.setShowsRootHandles(true);//��ʾ���ڵ�ǰ��+���Ӻţ���ť
		tree.setCellRenderer(new MyTreeRenderer());//�����Զ���Ľڵ������
		scrollPane = new JScrollPane(tree);
		this.add(scrollPane, BorderLayout.CENTER);
		
		//��ʼ����ť��
		btnPanel=new JPanel();
		this.add(btnPanel,BorderLayout.SOUTH);
		
		//������ļ�����
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				//�õ���ǰ��ѡ�еĽڵ�
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if(node==null){//���û�нڵ㱻ѡ��
					//ɾ����ťpanel�еİ�ťģ��
					btnPanel.removeAll();
					btnPanel.updateUI();
					//�ָ�ͼƬ��ĳ�ʼͼ�񣬲�����board���ɻ���
					CompMaintain.getPicPanel().recoverPicAndDisableBoard();
				}else if(node.getLevel()==0){//���ڵ�
					BtnRootPanel btnRootPanel=new BtnRootPanel(CompMaintain.getFrame(),tree,node);
					btnPanel.removeAll();
					btnPanel.add(btnRootPanel);
					btnPanel.updateUI();
					//�ָ�ͼƬ��ĳ�ʼͼ�񣬲�����board���ɻ���
					CompMaintain.getPicPanel().recoverPicAndDisableBoard();
//					DefaultMutableTreeNode rootnode=(DefaultMutableTreeNode)tree.getModel().getRoot();//��ȡ���ڵ�
//					System.out.println(rootnode.getChildCount());
				}else if(node.getLevel()==1){//ͨ���ڵ�
					BtnChannelPanel btnChannelPanel=new BtnChannelPanel(CompMaintain.getFrame(),tree,node,CompMaintain.getPicPanel());
					btnPanel.removeAll();
					btnPanel.add(btnChannelPanel);
					btnPanel.updateUI();
					if(node!=CompMaintain.getCurrentChannel()){//���ѡ���ͨ�����ǵ�ǰͨ��
						//�ָ�ͼƬ��ĳ�ʼͼ�񣬲�����board���ɻ���
						CompMaintain.getPicPanel().recoverPicAndDisableBoard();
					}
				}else if(node.getLevel()==2){//�����ڵ�
					if(node.getParent()==CompMaintain.getCurrentChannel()){
						System.out.println("��ǰ�������ڵ�ǰͨ��");
						//����TreePanel�е�btnPanel����ťģ�飩
						BtnWindowPanel btnWindowPanel=new BtnWindowPanel(CompMaintain.getFrame(),tree,node,CompMaintain.getPicPanel());
						btnPanel.removeAll();
						btnPanel.add(btnWindowPanel);
						btnPanel.updateUI();
					}else{
						//�ָ�ͼƬ��ĳ�ʼͼ�񣬲�����board���ɻ���
						CompMaintain.getPicPanel().recoverPicAndDisableBoard();
						//�����
						JOptionPane.showMessageDialog(null, "�������Ӹó���������ͨ����", "�п�Ժ�������ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
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
			System.out.println("TreePanel.treeΪnull");
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
			System.out.println("TreePanel.btnPanelΪnull");
			return null;
		}else{
			return btnPanel;
		}
	}
}
