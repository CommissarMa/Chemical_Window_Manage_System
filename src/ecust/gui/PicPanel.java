package ecust.gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ecust.gui.picpanel.Board;
import ecust.manage.CompMaintain;

/**
 * ͼ��ģ��
 * @author CommissarMa
 */
public class PicPanel extends JPanel {

	private JLabel player;//ͼ���
	private Board board;//�����
	
	/**
	 * ����PicPanel
	 */
	public PicPanel() {
		this.setLayout(null);//���þ��Բ���
		
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
			System.out.println("PicPanel.playerΪnull");
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
			System.out.println("PicPanel.boardΪnull");
			return null;
		}else{
			return this.board;
		}
	}
	
	/**
	 * �ָ�ͼƬ��ĳ�ʼͼ��;
	 * ����board���ɻ���;
	 * ά����ĵ�ǰͨ������Ϊnull
	 * �ػ�board
	 */
	public void recoverPicAndDisableBoard(){
		this.player.setIcon(new ImageIcon(PicPanel.class.getResource("/ecust/resource/bg.jpg")));
		this.board.setCanDraw(false);
		CompMaintain.setCurrentChannel(null);
		this.board.repaint();
	}
}

