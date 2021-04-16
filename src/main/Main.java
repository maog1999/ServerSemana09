package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet {
	
	PImage cereal, sandwich, perros, jugos, fondo;
	private LinkedList<listaPedidos> listaWonka;
	private int posX, posY,nPedido;
	DatagramSocket socket;
	String mensaje;
	
	//soundFile music;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("main.Main");

	}

	public void settings() {
		size(1200, 700);
	}

	public void setup() {
		
		cereal = loadImage("imagenes/cereal.jpg");
		sandwich = loadImage("imagenes/sandwich.jpg");
		perros = loadImage("imagenes/perro.jpg");
		jugos = loadImage("imagenes/jugos.jpg");
		fondo = loadImage("imagenes/fondo.jpg");

		
		listaWonka = new LinkedList<listaPedidos>();


		new Thread(() -> {
			try {
				socket = new DatagramSocket(5000);

				//Donde se recibe el msj
				while (true) {
					byte[] buff = new byte[256];

					DatagramPacket packet = new DatagramPacket(buff, buff.length);
					System.out.println("Esperando...");
					socket.receive(packet);

					// Para pasar un arreglo de Bytes a un string
					String recibido = new String(packet.getData()).trim();
					System.out.println(packet.getSocketAddress());

					System.out.println(recibido);
					
					
					
					switch(recibido) {
					case "cereal":
						listaWonka.add(new listaPedidos(cereal,this));
						break;
					case "jugos":
						listaWonka.add(new listaPedidos(jugos,this));
						break;
					case "sandwich":
						listaWonka.add(new listaPedidos(sandwich,this));
						break;
					case "perros":
						listaWonka.add(new listaPedidos(perros,this));
						break;
					
					}
				}

			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		).start();


	}

	public void draw() {
		
		image(fondo,0,0);
		
		for(int i=0; i<listaWonka.size();i++) {
			posX = 10*(i*30);
			posY = 200;
			fill(255);
			textSize(20);
			
			nPedido = i+1;
			Calendar c = Calendar.getInstance();
			Date fecha = c.getTime();
			
			SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
			String hrPedido = hora.format(fecha);
			
			listaWonka.get(i).draw(posX,posY);
			text("Pedido #"+nPedido,posX,posY-20);
			fill(0);
			textSize(12);
			text("Su orden fue tomada a las: " + hrPedido,posX,posY-5);
		}

	}
	
	public void mouseClicked() {
		
			
			new Thread(
                    () -> {
                        try {
                    		for(int i=0; i<listaWonka.size();i++) {
                    			
                    			int posPedidoX = listaWonka.get(i).getPosX() + 10*(i*30);
                    			int posPedidoY = listaWonka.get(i).getPosY();
                    			
                    			if(mouseX> posPedidoX && mouseX<posPedidoX+236 && mouseY>posPedidoY && mouseY<436) {
                    				listaWonka.remove(i);
                    				mensaje = "El pedido#"+(i+1)+ " esta listo";
                    			}
                            // DatagramSocket socket = new DatagramSocket(5000);
                    		}
                         
                            DatagramPacket packet = new DatagramPacket(
                                    mensaje.getBytes(),
                                    mensaje.getBytes().length,
                                    
                                    //Profe aca toca estarle cambiando el # del canal porque
                                    //cada vez que se abre el emulador cambia, entonces en la consola se imprime
                                    //no supe como almacenar el .getAddress en un string y hacerle .split":"; para obtener solo el canal...
                                    InetAddress.getByName("127.0.0.1"), 53451);

									socket.send(packet);

                        } catch (SocketException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (UnknownHostException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            ).start();
		

		
	}
}
