package ecust.entity;

import java.io.Serializable;

/**
 * ͨ����
 * @author Administrator
 * �ؼ���toString()���� ������Tree�нڵ���ʾ������
 */
public class Channel{
	private String name;//ͨ������
	private String config;//ͨ������
	
	public Channel(){
		this.name="";
		this.config="";
	}
	
	public Channel(String name,String config){
		this.name=name;
		this.config=config;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setConfig(String config){
		this.config=config;
	}
	
	public String getConfig(){
		return this.config;
	}
	
	public String toString(){
		return this.name;
	}
}
