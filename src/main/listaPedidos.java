package main;

import processing.core.PApplet;
import processing.core.PImage;

public class listaPedidos {
	
	private PImage pedido;
	private PApplet app;
	private int posX, posY;
	
	
	public listaPedidos(PImage pedido, PApplet app) {
		this.app = app;
		this.posX = posX;
		this.posY = posY;
		this.pedido = pedido;
	}


	public void draw(int posX, int posY) {
		// TODO Auto-generated method stub
		
		app.image(pedido, posX, posY);
		
		
	}


	
	public PImage getPedido() {
		return pedido;
	}


	public void setPedido(PImage pedido) {
		this.pedido = pedido;
	}


	public PApplet getApp() {
		return app;
	}


	public void setApp(PApplet app) {
		this.app = app;
	}


	public int getPosX() {
		return posX;
	}


	public void setPosX(int posX) {
		this.posX = posX;
	}


	public int getPosY() {
		return posY;
	}


	public void setPosY(int posY) {
		this.posY = posY;
	}
}
