package ecust.gui.treepanel;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import ecust.gui.PicPanel;
import ecust.manage.CompMaintain;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BtnWindowPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public BtnWindowPanel(JFrame frame,JTree tree,DefaultMutableTreeNode node,PicPanel picPanel) {
		
		JButton btnChangeName = new JButton("ÐÞ¸Ä³÷´°Ãû³Æ");
		btnChangeName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowChangeNameDialog dialog = new WindowChangeNameDialog(frame,tree,node);
				dialog.setVisible(true);
			}
		});
		add(btnChangeName);
		
		JButton btnChangeId = new JButton("ÐÞ¸Ä³÷´°ID");
		btnChangeId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WindowChangeIdDialog dialog = new WindowChangeIdDialog(frame,tree,node);
				dialog.setVisible(true);
			}
		});
		add(btnChangeId);
		
		JButton btnDel = new JButton("É¾³ý³÷´°");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompMaintain.getCurrentChannel().remove(node);
				CompMaintain.getTreePanel().getBtnPanel().removeAll();
				CompMaintain.getTreePanel().getBtnPanel().updateUI();
				tree.updateUI();
				//ÖØ»æboard
				CompMaintain.getPicPanel().getBoard().repaint();
			}
		});
		add(btnDel);

	}

}
