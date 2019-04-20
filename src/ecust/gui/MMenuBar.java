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
 * 菜单栏：
 * 包含菜单（menu）以及菜单项（menuitem）
 * @author CommissarMa
 */
public class MMenuBar extends JMenuBar{
	
	/**
	 * 菜单1
	 */
	private JMenu menu1;
	/**
	 * 菜单项1_1
	 */
	private JMenuItem item1_1;
	private JMenuItem item1_2;
	private JMenuItem item1_3;
	
	public MMenuBar(){
		menu1 = new JMenu("选项");
		this.add(menu1);
		
		item1_1 = new JMenuItem("修改数据接口Url");
		item1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(CompMaintain.getFrame()==null){
					System.out.println("MMenuBar中调用CompMaintain.frame为null");
				}else{
					ChangeTitleDialog dialog = new ChangeTitleDialog(CompMaintain.getFrame());
					dialog.setVisible(true);
				}
			}
		});
		menu1.add(item1_1);
		
		item1_2 = new JMenuItem("保存配置");
		item1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str=JsonConvert.tree2Json(CompMaintain.getTreePanel().getTree());
				System.out.println(str);
				//弹出对话框
				JsonConvert.showSaveConfig(str);
			}
		});
		menu1.add(item1_2);
		
		item1_3 = new JMenuItem("加载配置");
		item1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				JsonConvert.json2Tree(CompMaintain.json);
				JsonConvert.showLoadConfig();
			}
		});
		menu1.add(item1_3);
	}
	
}
