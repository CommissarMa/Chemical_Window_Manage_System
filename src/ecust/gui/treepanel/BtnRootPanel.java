package ecust.gui.treepanel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BtnRootPanel extends JPanel {

	private JButton btnNew;
	private JButton btnDel;
	
	/**
	 * Create the panel.
	 */
	public BtnRootPanel(JFrame frame,JTree tree,DefaultMutableTreeNode node) {
		
		//�½�ͨ��
		btnNew = new JButton("�½�ͨ��");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewChannelDialog dialog = new NewChannelDialog(frame,tree,node);
				dialog.setVisible(true);
			}
		});
		add(btnNew);

		//���ͨ��
		btnDel = new JButton("���ͨ��");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				node.removeAllChildren();
				tree.updateUI();
				tree.setSelectionPath(null);
			}
		});
		add(btnDel);

	}

}
