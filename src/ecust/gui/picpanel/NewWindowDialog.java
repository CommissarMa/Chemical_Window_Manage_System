package ecust.gui.picpanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import ecust.entity.Window;
import ecust.manage.CompMaintain;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewWindowDialog extends JDialog {
	private JTextField textFieldName;
	private JTextField textFieldId;
	
	private NewWindowDialog thisDialog;

	/**
	 * Create the dialog.
	 */
	public NewWindowDialog(JFrame frame,DefaultMutableTreeNode currentChannel,int x,int y,int width,int height) {
		thisDialog=this;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(NewWindowDialog.class.getResource("/ecust/resource/ecustlogo.png")));
		setBounds((int)((frame.getWidth()-450)*1.0/2)+frame.getX(), (int)((frame.getHeight()-150)*1.0/2)+frame.getY(), 450, 150);
		this.setTitle("�½�����");
		this.setModal(true);//ģ̬��
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel panelText = new JPanel();
			getContentPane().add(panelText, BorderLayout.NORTH);
			panelText.setLayout(new BorderLayout(0, 0));
			{
				JPanel panelName = new JPanel();
				panelText.add(panelName, BorderLayout.NORTH);
				{
					JLabel lblName = new JLabel("�������ƣ�");
					panelName.add(lblName);
				}
				{
					textFieldName = new JTextField();
					panelName.add(textFieldName);
					textFieldName.setColumns(25);
				}
			}
			{
				JPanel panelId = new JPanel();
				panelText.add(panelId, BorderLayout.CENTER);
				{
					JLabel lblId = new JLabel("����  ID��");
					panelId.add(lblId);
				}
				{
					textFieldId = new JTextField();
					panelId.add(textFieldId);
					textFieldId.setColumns(25);
				}
			}
		}
		{
			JPanel panelBtn = new JPanel();
			getContentPane().add(panelBtn, BorderLayout.SOUTH);
			{
				//���水ť
				JButton btnSave = new JButton("����");
				btnSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(textFieldName.getText().equals("")){
							JOptionPane.showMessageDialog(null, "�������Ʋ���Ϊ�գ�", "�п�Ժ�������ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if(textFieldId.getText().equals("")){
							JOptionPane.showMessageDialog(null, "����ID����Ϊ�գ�", "�п�Ժ�������ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
							return;
						}else{
							Window window=new Window(textFieldName.getText(),textFieldId.getText(),x,y,width,height);
							DefaultMutableTreeNode nodeWindow=new DefaultMutableTreeNode(window);
							currentChannel.add(nodeWindow);//����µĳ����ڵ�
							//�Զ�չ�����ڵ�
							DefaultTreeModel model=(DefaultTreeModel)CompMaintain.getTreePanel().getTree().getModel();
							TreeNode[] nodes=model.getPathToRoot(nodeWindow);
							TreePath path=new TreePath(nodes);
							CompMaintain.getTreePanel().getTree().scrollPathToVisible(path);
							CompMaintain.getTreePanel().getTree().updateUI();//����tree��UI��ͼ
							//�رյ�ǰ�Ի���
							thisDialog.dispose();
						}
					}
				});
				panelBtn.add(btnSave);
			}
			{
				//ȡ����ť
				JButton btnCancel = new JButton("ȡ��");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						thisDialog.dispose();
					}
				});
				panelBtn.add(btnCancel);
			}
		}
	}

}
