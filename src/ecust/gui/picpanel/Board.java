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
	
	private boolean canDraw=false;//true代表能够绘制，false代表不能够绘制
	
	public void setCanDraw(boolean canDraw){
		this.canDraw=canDraw;
	}
	
	public boolean getCanDraw(){
		return this.canDraw;
	}
	
	public int flag=0;//鼠标哪个键按下，0代表没有，1代表左键，2滚轮按下，3右键
	public int flagClear=0;//是否在绘制新区域，初始时、绘制前和绘制后为0，绘制中为1
	public int x=0,y=0;//当前鼠标位置
	public int startx=0,starty=0,endx=0,endy=0;//鼠标按下的位置和弹起的位置
	
	public void paint(Graphics g){
//		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.WHITE);//设置画笔颜色
		g2.setStroke(new BasicStroke(3f));//设置画笔的粗细
		if(flagClear==1){//绘制新区域中
			g2.drawRect(startx,starty,x-startx,y-starty);//再绘制当前区域
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
			//写上当前通道的名称
			Font font = new Font("Serif", Font.PLAIN, 20);
			g2.setFont(font);
			g2.drawString("当前通道："+((Channel)CompMaintain.getCurrentChannel().getUserObject()).toString(), 0, 20);
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
		if(e.getButton()==MouseEvent.BUTTON1){//左键
			System.out.println("左键pressed");
			flag=1;//当前按键为左键
			flagClear=1;//当前在绘制中
			startx=e.getX();//记录下初始位置
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
		if(flag==1){//左键拖动，注意：在拖动事件中，是得不到到底是哪个鼠标按键按下的信息的
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
			repaint();//重新绘制
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(canDraw==false)return;
		if(e.getButton()==MouseEvent.BUTTON1){//左键
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
			//弹出对话框（新建橱窗）
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
