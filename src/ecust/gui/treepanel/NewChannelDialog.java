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
import javax.swing.tree.TreePath;

import ecust.entity.Channel;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewChannelDialog extends JDialog {
	private JPanel panelText;
	private JPanel panelBtn;
	private JPanel panelName;
	private JPanel panelConfig;
	private JLabel lblName;
	private JLabel lblConfig;
	private JTextField textFieldName;
	private JTextArea textAreaConfig;
	private JScrollPane scrollPane;
	private JButton btnSave;
	private JButton btnCancel;
	
	private JDialog thisDialog;

	/**
	 * Create the dialog.
	 */
	public NewChannelDialog(JFrame frame,JTree tree,DefaultMutableTreeNode node) {
		thisDialog=this;
		
		this.setModal(true);
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		setBounds((int)((frame.getWidth()-450)*1.0/2)+frame.getX(), (int)((frame.getHeight()-200)*1.0/2)+frame.getY(), 450, 200);
		setTitle("�½�ͨ��");
		setIconImage(Toolkit.getDefaultToolkit().getImage(NewChannelDialog.class.getResource("/ecust/resource/ecustlogo.png")));
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		panelText = new JPanel();
		panelText.setBackground(Color.WHITE);
		getContentPane().add(panelText, BorderLayout.NORTH);
		panelText.setLayout(new BorderLayout(0, 0));
		
		panelName = new JPanel();
		panelName.setBackground(Color.WHITE);
		panelText.add(panelName, BorderLayout.NORTH);
		
		lblName = new JLabel("ͨ�����ƣ�");
		panelName.add(lblName);
		
		textFieldName = new JTextField();
		panelName.add(textFieldName);
		textFieldName.setColumns(25);
		
		panelConfig = new JPanel();
		panelConfig.setBackground(Color.WHITE);
		panelText.add(panelConfig, BorderLayout.SOUTH);
		
		lblConfig = new JLabel("ͨ�����ã�");
		panelConfig.add(lblConfig);
		
		textAreaConfig = new JTextArea();
		textAreaConfig.setBackground(Color.WHITE);
		textAreaConfig.setFont(UIManager.getFont("TextField.font"));
		textAreaConfig.setLineWrap(true);
		textAreaConfig.setColumns(25);
		textAreaConfig.setRows(3);
		
		scrollPane = new JScrollPane(textAreaConfig);
		panelConfig.add(scrollPane);
		
		panelBtn = new JPanel();
		panelBtn.setBackground(Color.WHITE);
		getContentPane().add(panelBtn, BorderLayout.SOUTH);
		
		btnSave = new JButton("����");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textFieldName.getText().equals("")){
					JOptionPane.showMessageDialog(null, "ͨ�����Ʋ���Ϊ�գ�", "�п�Ժ�������ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(textAreaConfig.getText().equals("")){
					JOptionPane.showMessageDialog(null, "ͨ�����ò���Ϊ�գ�", "�п�Ժ�������ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
					return;
				}else{
					Channel channel=new Channel(textFieldName.getText(),textAreaConfig.getText());
					DefaultMutableTreeNode child=new DefaultMutableTreeNode(channel);
					node.add(child);
					tree.expandPath(new TreePath(node));//չ�����ڵ�
					tree.updateUI();
					thisDialog.dispose();//�رյ�ǰ����
				}
			}
		});
		panelBtn.add(btnSave);
		
		btnCancel = new JButton("ȡ��");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thisDialog.dispose();//�رյ�ǰ����
			}
		});
		panelBtn.add(btnCancel);
	}

}
