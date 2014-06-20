package com.cos765.server;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

public class Server {

	public static int TRANSMISSION_TIME = 20; // 20ms entre pacotes
	private static int PORT = 15000;
	private static int PAYLOAD_SIZE = 160; // 160 bytes no payload por pacote
	private static int HEADER_SIZE = 1; // 1 byte no cabeçalho por pacote

	public static void main(String[] args) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(PORT);
		int bytesRead = 0;
		byte[] receiveData = new byte[PAYLOAD_SIZE + HEADER_SIZE]; // o nome do
																	// arquivo
		// desejado pelo cliente
		byte[] sendData = new byte[PAYLOAD_SIZE + HEADER_SIZE]; // o conteúdo do
																// arquivo a ser
		// enviado para o cliente
		String receivedFileName;

		try {
			while (true) {
				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				serverSocket.receive(receivePacket); // recebe nome da fonte de
														// dados (arquivo)
				receivedFileName = new String(receivePacket.getData());
				receivedFileName = "D://Setor//" + receivedFileName; 
				// VER ISSO DEPOIS. Não estou conseguindo mandar o nome do arquivo com duas barras /!

				InetAddress iPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();

				ByteArrayInputStream bis = new ByteArrayInputStream(
						FileUtils.readFileToByteArray(new File("C:\\test.txt")));

				while (bytesRead != -1) {
					byte zero = 0;
					Arrays.fill(sendData, zero);

					sendData[0]++;
					bytesRead = bis.read(sendData, 1, PAYLOAD_SIZE);

					// Delay na transmissão
					Thread.sleep(TRANSMISSION_TIME);

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
