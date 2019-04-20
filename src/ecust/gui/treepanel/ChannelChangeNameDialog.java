package ecust.gui.treepanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import ecust.entity.Channel;
import ecust.manage.CompMaintain;

import javax.swing.JTextField;
import javax.swing.JTree;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChannelChangeNameDialog extends JDialog {
	private JTextField textField;
	
	private JDialog thisDialog;

	/**
	 * Create the dialog.
	 */
	public ChannelChangeNameDialog(JFrame frame,JTree tree,DefaultMutableTreeNode node) {
		thisDialog=this;
		
		this.setModal(true);//ģ̬�Ի���
		this.setTitle("�޸�ͨ������");
		setBounds((int)((frame.getWidth()-450)*1.0/2)+frame.getX(), (int)((frame.getHeight()-85)*1.0/2)+frame.getY(), 450, 85);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChannelChangeNameDialog.class.getResource("/ecust/resource/ecustlogo.png")));
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Channel channel=(Channel)(node.getUserObject());
		textField = new JTextField(channel.getName());
		getContentPane().add(textField);
		textField.setColumns(25);
		
		//���水ť
		JButton btnSave = new JButton("����");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Channel channel=(Channel)node.getUserObject();
				channel.setName(textField.getText());
				tree.updateUI();
				//�ػ�board
				CompMaintain.getPicPanel().getBoard().repaint();
				thisDialog.dispose();
			}
		});
		getContentPane().add(btnSave);
		
		//ȡ����ť
		JButton btnCancel = new JButton("ȡ��");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisDialog.dispose();
			}
		});
		getContentPane().add(btnCancel);
	}

}
