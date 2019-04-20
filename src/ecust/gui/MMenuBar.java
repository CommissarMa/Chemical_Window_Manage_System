package ecust.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import ecust.gui.mmenubar.ChangeTitleDialog;
import ecust.manage.CompMaintain;
import ecust.tool.JsonConvert;

/**
 * �˵�����
 * �����˵���menu���Լ��˵��menuitem��
 * @author CommissarMa
 */
public class MMenuBar extends JMenuBar{
	
	/**
	 * �˵�1
	 */
	private JMenu menu1;
	/**
	 * �˵���1_1
	 */
	private JMenuItem item1_1;
	private JMenuItem item1_2;
	private JMenuItem item1_3;
	
	public MMenuBar(){
		menu1 = new JMenu("ѡ��");
		this.add(menu1);
		
		item1_1 = new JMenuItem("�޸����ݽӿ�Url");
		item1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(CompMaintain.getFrame()==null){
					System.out.println("MMenuBar�е���CompMaintain.frameΪnull");
				}else{
					ChangeTitleDialog dialog = new ChangeTitleDialog(CompMaintain.getFrame());
					dialog.setVisible(true);
				}
			}
		});
		menu1.add(item1_1);
		
		item1_2 = new JMenuItem("��������");
		item1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str=JsonConvert.tree2Json(CompMaintain.getTreePanel().getTree());
				System.out.println(str);
				//�����Ի���
				JsonConvert.showSaveConfig(str);
			}
		});
		menu1.add(item1_2);
		
		item1_3 = new JMenuItem("��������");
		item1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				JsonConvert.json2Tree(CompMaintain.json);
				JsonConvert.showLoadConfig();
			}
		});
		menu1.add(item1_3);
	}
	
}
