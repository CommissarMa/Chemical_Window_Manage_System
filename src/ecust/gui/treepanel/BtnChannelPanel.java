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
		
		//修改名称
		btnChangeName = new JButton("修改名称");
		btnChangeName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChannelChangeNameDialog dialog = new ChannelChangeNameDialog(frame,tree,node);
				dialog.setVisible(true);
			}
		});
		add(btnChangeName);
		
		//修改配置
		btnChangeConfig = new JButton("修改配置");
		btnChangeConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChannelChangeConfigDialog dialog = new ChannelChangeConfigDialog(frame,tree,node);
				dialog.setVisible(true);
			}
		});
		add(btnChangeConfig);
		
		//连接通道
		btnConnect = new JButton("连接通道");
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
						//设置board可绘制
						picPanel.getBoard().setCanDraw(true);
						//设置管理类的当前通道
						CompMaintain.setCurrentChannel(node);
					}
					capture.release();
				}else{
					JOptionPane.showMessageDialog(null, "通道连接失败，请检查通道配置或网络设置", "中科院橱窗检测系统提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		add(btnConnect);
		
		
		//删除通道
		btnDel = new JButton("删除通道");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode treenode=(DefaultMutableTreeNode)node.getParent();
				treenode.remove(node);
				tree.setSelectionPath(null);//设置当前选择的节点为空
				tree.updateUI();
			}
		});
		add(btnDel);
		
	}

}
