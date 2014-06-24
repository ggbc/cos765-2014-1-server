package com.cos765.server;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import com.cos765.common.Common;
import com.cos765.common.Segment;

public class Server {
	
	public static void main(String[] args) throws Exception {

		DatagramSocket serverSocket = new DatagramSocket(Common.SERVER_PORT);
		int bytesRead = 0;
		byte[] receiveData = new byte[Segment.PAYLOAD_SIZE
				+ Segment.HEADER_SIZE]; // o nome do
		// arquivo
		// desejado pelo cliente
		byte[] sendData = new byte[Segment.PAYLOAD_SIZE + Segment.HEADER_SIZE]; // o
																				// conteúdo
																				// do
		// arquivo a ser
		// enviado para o cliente
		String receivedFileName;
		int sendOrder = 0;

		try {
			while (true) {
				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				serverSocket.receive(receivePacket); // recebe nome da fonte de
														// dados (arquivo)
				receivedFileName = new String(receivePacket.getData());
				receivedFileName = "D://Setor//" + receivedFileName;
				// TODO: Não estou conseguindo mandar o nome do arquivo com duas
				// barras /!

				InetAddress iPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();

				ByteArrayInputStream bis = new ByteArrayInputStream(
//						FileUtils.readFileToByteArray(new File("C:\\test.txt")));
				FileUtils.readFileToByteArray(new File("D:\\setor\\test.txt")));

				while (bytesRead != -1) {
					byte zero = 0;
					Arrays.fill(sendData, zero);

					sendData[0] = (byte)++sendOrder;
					bytesRead = bis.read(sendData, 1, Segment.PAYLOAD_SIZE);
				
					// Delay na transmissão
					Thread.sleep(Common.TRANSMISSION_TIME);

					// Enviar os dados
					DatagramPacket sendPacket = new DatagramPacket(sendData,
							sendData.length, iPAddress, port);
					serverSocket.send(sendPacket);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			serverSocket.close();
		}
	}
}
	
