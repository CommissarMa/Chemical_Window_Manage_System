package ecust.gui.treepanel;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * 自定义的树节点绘制器类
 * @author CommissarMa
 */
public class MyTreeRenderer extends DefaultTreeCellRenderer{
	ImageIcon rootIcon=new ImageIcon(MyTreeRenderer.class.getResource("/ecust/resource/root.png"));
	ImageIcon channelIcon=new ImageIcon(MyTreeRenderer.class.getResource("/ecust/resource/channel.png"));
	ImageIcon windowIcon=new ImageIcon(MyTreeRenderer.class.getResource("/ecust/resource/window.png"));
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree,Object value,boolean sel,boolean expanded,
			boolean leaf,int row,boolean hasFocus){
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode node=(DefaultMutableTreeNode)value;
		//根据节点深度来决定图标
		ImageIcon icon=null;
		switch(node.getLevel()){
			case 0:
				icon=rootIcon;
				break;
			case 1:
				icon=channelIcon;
				break;
			case 2:
				icon=windowIcon;
				break;
		}
		//改变图标
		this.setIcon(icon);
		
		return this;
	}
}
