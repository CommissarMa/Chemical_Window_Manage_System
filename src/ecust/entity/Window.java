package ecust.entity;

import java.io.Serializable;

/**
 * ������
 * @author Administrator
 *
 */
public class Window{
	private String name;//��������
	private String id;//����id
	private int x;//���Ͻ�x
	private int y;//���Ͻ�y
	private int width;//��
	private int height;//��
	
	/**
	 * ���캯��
	 * @param name
	 * @param id
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Window(String name,String id,int x,int y,int width,int height){
		this.name=name;
		this.id=id;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setId(String id){
		this.id=id;
	}
	
	public String getId(){
		return this.id;
	}
	
	public void setX(int x){
		this.x=x;
	}
	
	public int getX(){
		return this.x;
	}
	
	public void setY(int y){
		this.y=y;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setWidth(int width){
		this.width=width;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public void setHeight(int height){
		this.height=height;
	}
	
	public int getHeight(){
		return this.height;
	}
	
	/**
	 * toString���������ڷ������нڵ�����ʾ���ַ���
	 */
	public String toString(){
		return this.name;
	}
}
