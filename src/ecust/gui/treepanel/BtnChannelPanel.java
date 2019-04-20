package ecust.gui.treepanel;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import ecust.entity.Channel;
import ecust.gui.PicPanel;
import ecust.manage.CompMaintain;
import ecust.tool.Tool;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;

public class BtnChannelPanel extends JPanel {
	private JButton btnChangeName;
	private JButton btnChangeConfig;
	private JButton btnConnect;
	private JButton btnDel;

	/**
	 * Create the panel.
	 */
	public BtnChannelPanel(JFrame frame,JTree tree,DefaultMutableTreeNode node,PicPanel picPanel) {
		Channel channel=(Channel)(node.getUserObject());
		
		//�޸�����
		btnChangeName = new JButton("�޸�����");
		btnChangeName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChannelChangeNameDialog dialog = new ChannelChangeNameDialog(frame,tree,node);
				dialog.setVisible(true);
			}
		});
		add(btnChangeName);
		
		//�޸�����
		btnChangeConfig = new JButton("�޸�����");
		btnChangeConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChannelChangeConfigDialog dialog = new ChannelChangeConfigDialog(frame,tree,node);
				dialog.setVisible(true);
			}
		});
		add(btnChangeConfig);
		
		//����ͨ��
		btnConnect = new JButton("����ͨ��");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VideoCapture capture=new VideoCapture();
				capture.open(channel.getConfig());
				if(capture.isOpened()){
					Mat mat=new Mat();
					capture.read(mat);
					if(!mat.empty()){
						Imgproc.resize(mat, mat, new Size(800,600));
						BufferedImage bfImage=Tool.matToBufferedImage(mat);
						picPanel.getPlayer().setIcon(new ImageIcon(bfImage));
						//����board�ɻ���
						picPanel.getBoard().setCanDraw(true);
						//���ù�����ĵ�ǰͨ��
						CompMaintain.setCurrentChannel(node);
					}
					capture.release();
				}else{
					JOptionPane.showMessageDialog(null, "ͨ������ʧ�ܣ�����ͨ�����û���������", "�п�Ժ�������ϵͳ��ʾ", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		add(btnConnect);
		
		
		//ɾ��ͨ��
		btnDel = new JButton("ɾ��ͨ��");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode treenode=(DefaultMutableTreeNode)node.getParent();
				treenode.remove(node);
				tree.setSelectionPath(null);//���õ�ǰѡ��Ľڵ�Ϊ��
				tree.updateUI();
			}
		});
		add(btnDel);
		
	}

}
