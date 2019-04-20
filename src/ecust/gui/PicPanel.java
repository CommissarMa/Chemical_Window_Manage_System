package ecust.gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ecust.gui.picpanel.Board;
import ecust.manage.CompMaintain;

/**
 * 图像模块
 * @author CommissarMa
 */
public class PicPanel extends JPanel {

	private JLabel player;//图像层
	private Board board;//画板层
	
	/**
	 * 构造PicPanel
	 */
	public PicPanel() {
		this.setLayout(null);//设置绝对布局
		
		player = new JLabel("");
		player.setIcon(new ImageIcon(PicPanel.class.getResource("/ecust/resource/bg.jpg")));
		player.setBounds(0, 0, 800, 600);
		this.add(player);
		
		board = new Board();
		board.setBounds(0, 0, 800, 600);
		player.add(board);
		this.addMouseListener(board);
		this.addMouseMotionListener(board);
	}
	
	public void setPlayer(JLabel player){
		this.player=player;
	}

	public JLabel getPlayer(){
		if(this.player==null){
			System.out.println("PicPanel.player为null");
			return null;
		}else{
			return this.player;
		}
	}
	
	public void setBoard(Board board){
		this.board=board;
	}
	
	public Board getBoard(){
		if(this.board==null){
			System.out.println("PicPanel.board为null");
			return null;
		}else{
			return this.board;
		}
	}
	
	/**
	 * 恢复图片框的初始图像;
	 * 设置board不可绘制;
	 * 维护类的当前通道设置为null
	 * 重绘board
	 */
	public void recoverPicAndDisableBoard(){
		this.player.setIcon(new ImageIcon(PicPanel.class.getResource("/ecust/resource/bg.jpg")));
		this.board.setCanDraw(false);
		CompMaintain.setCurrentChannel(null);
		this.board.repaint();
	}
}

