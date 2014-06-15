package com.cos765.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.commons.io.FileUtils;

public class Server {

	public static int TRANSMISSION_TIME = 20; // 20ms entre pacotes
	private static int PORT = 15000;
	private static int PACKET_SIZE = 160; // 160 bytes por pacote
			
	public static void main(String[] args) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(PORT);
		int bytesRead = 0;
		byte[] receiveData = new byte[PACKET_SIZE]; // o nome do arquivo desejado pelo cliente
		byte[] sendData = new byte[PACKET_SIZE]; // o conteúdo do arquivo a ser
													// enviado para o cliente
		String fileName;	
				
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);						
			serverSocket.receive(receivePacket); // recebe nome da fonte de dados (arquivo)		
			fileName = new String(receivePacket.getData());

			InetAddress iPAddress = receivePacket.getAddress();				
			int port = receivePacket.getPort();
			
			try
			{
				ByteArrayInputStream bis = new ByteArrayInputStream(
						FileUtils.readFileToByteArray(new File("C:\\test.txt")));
				
				while (bytesRead != -1) {
					sendData = new byte[PACKET_SIZE];
					bytesRead = bis.read(sendData, 0, PACKET_SIZE);
					// Enviar os dados
					DatagramPacket sendPacket = new DatagramPacket(sendData,
							sendData.length, iPAddress, port);
					serverSocket.send(sendPacket);
				}					
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}		
		}		
		
		
//			 while (true) {
//			 // Ao iniciar, o programa de transferˆencia de ´audio deve receber
//			 // informa¸c˜oes sobre a fonte de dados
//			 // que gerar´a as amostras a serem transferidas.
//			 DatagramPacket receivePacket = new DatagramPacket(receiveData,
//			 receiveData.length);
//			 serverSocket.receive(receivePacket); // recebe nome da fonte de
//			 // dados (arquivo)
//			
//			 String sentence = new String(receivePacket.getData());
//			 InetAddress iPAddress = receivePacket.getAddress();
//			
//			 int port = receivePacket.getPort();
//			 // String capitalizedSentence = sentence.toUpperCase();
//			 // sendData = capitalizedSentence.getBytes();			
//			 DatagramPacket sendPacket = new DatagramPacket(sendData,
//			 sendData.length, iPAddress, port);
//			 serverSocket.send(sendPacket);
//			 }
//		 serverSocket.close(); // fechando o socket aberto para envio do
		// arquivo
	}
}
