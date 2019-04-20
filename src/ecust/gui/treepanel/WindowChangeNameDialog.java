package ecust.gui.treepanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import ecust.entity.Channel;
import ecust.entity.Window;
import ecust.manage.CompMaintain;

public class WindowChangeNameDialog extends JDialog {
	private JTextField textField;

	private JDialog thisDialog;

	/**
	 * Create the dialog.
	 */
	public WindowChangeNameDialog(JFrame frame,JTree tree,DefaultMutableTreeNode node) {
		thisDialog=this;
		
		this.setModal(true);//模态对话框
		this.setTitle("修改橱窗名称");
		setBounds((int)((frame.getWidth()-450)*1.0/2)+frame.getX(), (int)((frame.getHeight()-85)*1.0/2)+frame.getY(), 450, 85);
		setIconImage(Toolkit.getDefaultToolkit().getImage(WindowChangeNameDialog.class.getResource("/ecust/resource/ecustlogo.png")));
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Window window=(Window)(node.getUserObject());
		textField = new JTextField(window.getName());
		getContentPane().add(textField);
		textField.setColumns(25);
		
		//保存按钮
		JButton btnSave = new JButton("保存");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window window=(Window)node.getUserObject();
				window.setName(textField.getText());
				tree.updateUI();
				//重绘board
				CompMaintain.getPicPanel().getBoard().repaint();
				thisDialog.dispose();
			}
		});
		getContentPane().add(btnSave);
		
		//取消按钮
		JButton btnCancel = new JButton("取消");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisDialog.dispose();
			}
		});
		getContentPane().add(btnCancel);
	}

}
