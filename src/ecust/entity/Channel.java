package ecust.entity;

import java.io.Serializable;

/**
 * 通道类
 * @author Administrator
 * 关键是toString()方法 ：将在Tree中节点显示的名称
 */
public class Channel{
	private String name;//通道名称
	private String config;//通道配置
	
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
