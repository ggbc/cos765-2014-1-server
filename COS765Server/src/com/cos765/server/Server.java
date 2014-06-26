package com.cos765.server;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
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
		int sequenceNumber = 0;

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

				ByteArrayInputStream byteStream = new ByteArrayInputStream(
						FileUtils.readFileToByteArray(new File("C:\\test.txt")));
				
				while (bytesRead != -1) {
					byte zero = 0;
					Arrays.fill(sendData, zero);
					
					sequenceNumber++;
					
					ByteBuffer bb = ByteBuffer.allocate(Segment.HEADER_SIZE);
					bb.putInt(sequenceNumber);					
					
					ByteArrayInputStream sequenceNumberStream = new ByteArrayInputStream(bb.array());
					sequenceNumberStream.read(sendData, 0, Segment.HEADER_SIZE); // copiar para dentro do sendData o cabeçalho com o sequencenumber (4 bytes)
					
					bytesRead = byteStream.read(sendData, Segment.HEADER_SIZE, Segment.PAYLOAD_SIZE);
										
					// Enviar os dados
					DatagramPacket sendPacket = new DatagramPacket(sendData,
							sendData.length, iPAddress, port);
					serverSocket.send(sendPacket);
					
					// Delay na transmissão
					Thread.sleep(Common.SLEEP_TIME);					
				}				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			serverSocket.close();
		}
	}
}
	
