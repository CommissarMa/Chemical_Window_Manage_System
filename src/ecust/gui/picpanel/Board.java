package ecust.gui.picpanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.opencv.core.Rect;

import ecust.entity.Channel;
import ecust.entity.Window;
import ecust.manage.CompMaintain;


public class Board extends JLabel implements MouseListener,MouseMotionListener{
	
	private boolean canDraw=false;//true�����ܹ����ƣ�false�����ܹ�����
	
	public void setCanDraw(boolean canDraw){
		this.canDraw=canDraw;
	}
	
	public boolean getCanDraw(){
		return this.canDraw;
	}
	
	public int flag=0;//����ĸ������£�0����û�У�1���������2���ְ��£�3�Ҽ�
	public int flagClear=0;//�Ƿ��ڻ��������򣬳�ʼʱ������ǰ�ͻ��ƺ�Ϊ0��������Ϊ1
	public int x=0,y=0;//��ǰ���λ��
	public int startx=0,starty=0,endx=0,endy=0;//��갴�µ�λ�ú͵����λ��
	
	public void paint(Graphics g){
//		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.WHITE);//���û�����ɫ
		g2.setStroke(new BasicStroke(3f));//���û��ʵĴ�ϸ
		if(flagClear==1){//������������
			g2.drawRect(startx,starty,x-startx,y-starty);//�ٻ��Ƶ�ǰ����
		}
		if(CompMaintain.getCurrentChannel()!=null){
			for(int i=0;i<CompMaintain.getCurrentChannel().getChildCount();i++){
				DefaultMutableTreeNode windowNode=(DefaultMutableTreeNode)CompMaintain.getCurrentChannel().getChildAt(i);
				Window window=(Window)windowNode.getUserObject();
				g2.drawRect(window.getX(), window.getY(), window.getWidth(), window.getHeight());
				Font font = new Font("Serif", Font.PLAIN, 20);
				g2.setFont(font);
				g2.drawString(""+window.getName(), window.getX(), window.getY()-3);
			}
			//д�ϵ�ǰͨ��������
			Font font = new Font("Serif", Font.PLAIN, 20);
			g2.setFont(font);
			g2.drawString("��ǰͨ����"+((Channel)CompMaintain.getCurrentChannel().getUserObject()).toString(), 0, 20);
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(canDraw==false)return;
		if(e.getButton()==MouseEvent.BUTTON1){//���
			System.out.println("���pressed");
			flag=1;//��ǰ����Ϊ���
			flagClear=1;//��ǰ�ڻ�����
			startx=e.getX();//��¼�³�ʼλ��
			starty=e.getY();
			x=e.getX();
			y=e.getY();
			repaint();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(canDraw==false)return;
		if(flag==1){//����϶���ע�⣺���϶��¼��У��ǵò����������ĸ���갴�����µ���Ϣ��
			if(e.getX()>799){
				x=799;
			}else{
				x=e.getX();
			}
			if(e.getY()>599){
				y=599;
			}else{
				y=e.getY();
			}
			repaint();//���»���
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(canDraw==false)return;
		if(e.getButton()==MouseEvent.BUTTON1){//���
			System.out.println("released");
			if(e.getX()>799){
				x=799;
				endx=799;
			}else{
				x=e.getX();
				endx=e.getX();
			}
			if(e.getY()>599){
				y=599;
				endy=599;
			}else{
				y=e.getY();
				endy=e.getY();
			}
			repaint();
			//�����Ի����½�������
			NewWindowDialog dialog = new NewWindowDialog(CompMaintain.getFrame(),CompMaintain.getCurrentChannel(),startx,starty,endx-startx, endy-starty);
			dialog.setVisible(true);
			repaint();
		}
		flag=0;
		flagClear=0;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
