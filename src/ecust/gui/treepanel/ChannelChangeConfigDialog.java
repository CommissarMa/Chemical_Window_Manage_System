package ecust.gui.treepanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import ecust.entity.Channel;
import ecust.manage.CompMaintain;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChannelChangeConfigDialog extends JDialog {
	private JTextField textField;
	
	private JDialog thisDialog;
	private JPanel panelConfig;
	private JLabel lblConfig;
	private JPanel panelBtn;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JButton btnSave;
	private JButton btnCancel;

	/**
	 * Create the dialog.
	 */
	public ChannelChangeConfigDialog(JFrame frame,JTree tree,DefaultMutableTreeNode node) {
		thisDialog=this;
	
		this.setModal(true);//模态对话框
		this.setTitle("修改通道配置");
		setBounds((int)((frame.getWidth()-450)*1.0/2)+frame.getX(), (int)((frame.getHeight()-200)*1.0/2)+frame.getY(), 450, 200);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChannelChangeConfigDialog.class.getResource("/ecust/resource/ecustlogo.png")));
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		panelConfig = new JPanel();
		getContentPane().add(panelConfig, BorderLayout.NORTH);
		
		lblConfig = new JLabel("修改通道配置");
		panelConfig.add(lblConfig);
		
		Channel channel=(Channel)(node.getUserObject());
		
		textArea = new JTextArea(channel.getConfig());
		textArea.setLineWrap(true);//允许换行
		textArea.setRows(3);
		textArea.setBackground(Color.WHITE);
		textArea.setFont(UIManager.getFont("TextField.font"));
		textArea.setColumns(25);
		
		scrollPane = new JScrollPane(textArea);
		panelConfig.add(scrollPane);
		
		panelBtn = new JPanel();
		getContentPane().add(panelBtn, BorderLayout.SOUTH);
		
		//保存按钮
		btnSave = new JButton("保存");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				channel.setConfig(textArea.getText());
				tree.updateUI();
				//重绘board
				CompMaintain.getPicPanel().getBoard().repaint();
				thisDialog.dispose();
			}
		});
		panelBtn.add(btnSave);
		
		//取消按钮
		btnCancel = new JButton("取消");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisDialog.dispose();
			}
		});
		panelBtn.add(btnCancel);
	}

}
