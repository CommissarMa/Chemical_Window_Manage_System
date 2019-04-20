package ecust.gui.mmenubar;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ecust.main.MainFrame;
import ecust.manage.CompMaintain;

import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangeTitleDialog extends JDialog {
	private JTextField textField;
	private ChangeTitleDialog thisDialog;
	
	/**
	 * Create the dialog.
	 */
	public ChangeTitleDialog(JFrame frame) {
		thisDialog=this;//引用自身，用于关闭dialog
		
		this.setTitle("修改数据接口Url");
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);//默认关闭按钮操作
		setModal(true);//设置模态框
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChangeTitleDialog.class.getResource("/ecust/resource/ecustlogo.png")));//设置标题图标
		setBounds((int)((frame.getWidth()-450)*1.0/2)+frame.getX(), (int)((frame.getHeight()-85)*1.0/2)+frame.getY(), 450, 85);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textField = new JTextField();
		textField.setText(CompMaintain.getDataUrl());
		getContentPane().add(textField);
		textField.setColumns(25);
		
		//保存按钮
		JButton btnOK = new JButton("保存");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompMaintain.setDataUrl(textField.getText());
				thisDialog.dispose();
			}
		});
		getContentPane().add(btnOK);
		
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
