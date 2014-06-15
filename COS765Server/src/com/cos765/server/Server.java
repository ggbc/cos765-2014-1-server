package com.cos765.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {

	public static int TRANSMISSION_TIME = 20; // 20ms entre pacotes
	private static int PORT = 15000;
	private static int PACKET_SIZE = 160; // 160 bytes por pacote

	public static void main(String[] args) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(PORT);
		// byte[] receiveData = new byte[PACKET_SIZE];
		byte[] sendData = new byte[PACKET_SIZE];

		BufferedReader br = null;
		try {
			String line;
			br = new BufferedReader(new FileReader("C:\\test.txt"));
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		// while (true) {
		// DatagramPacket receivePacket = new DatagramPacket(receiveData,
		// receiveData.length);
		// serverSocket.receive(receivePacket);
		// String sentence = new String(receivePacket.getData());
		// InetAddress iPAddress = receivePacket.getAddress();
		//
		// int port = receivePacket.getPort();
		// String capitalizedSentence = sentence.toUpperCase();
		// sendData = capitalizedSentence.getBytes();
		//
		// DatagramPacket sendPacket = new DatagramPacket(sendData,
		// sendData.length, iPAddress, port);
		// serverSocket.send(sendPacket);
		// }
		serverSocket.close();
	}

}
