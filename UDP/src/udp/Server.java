/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samar
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(4000);
            Scanner scanner = new Scanner(System.in);

            System.out.println("Server is booted up");

            while (true) {
                byte[] requestBufferList = new byte[5000];
                DatagramPacket requestPacketList = new DatagramPacket(requestBufferList, requestBufferList.length);
                serverSocket.receive(requestPacketList);

                String requestList = new String(requestPacketList.getData());
                System.out.println("Client: " + requestList.trim());
                
                String tempReq = requestList.trim();
                
                String[] num2 = tempReq.split(" ");
                int count = num2.length;
                int[] num = new int[count];
                
                for (int j = 0; j < count; j++) {
                    
                    num[j] = Integer.parseInt(num2[j]);
                }

                Arrays.sort(num);

                byte[] requestBuffer = new byte[5000];
                DatagramPacket requestPacket = new DatagramPacket(requestBuffer, requestBuffer.length);
                serverSocket.receive(requestPacket);

                String request = new String(requestPacket.getData());
                System.out.println("Client: " + request.trim());
                
                if (request.trim().equals("1")) {
                    
                    String reply = Arrays.toString(num);
                    byte[] replyBytes = reply.getBytes();
                    int replyLength = replyBytes.length;
                    InetAddress clientIP = requestPacket.getAddress();
                    int clientPort = requestPacket.getPort();

                    DatagramPacket replyPacket = new DatagramPacket(replyBytes, replyLength, clientIP, clientPort);
                    serverSocket.send(replyPacket);

                } else if (request.trim().equals("2")) {
                    
                    int[] temp = new int[count];
                    
                    int i = 0;
                    for (int j = count - 1; j > -1; j--) {
                        temp[i] = num[j];
                        i++;
                    }
                    String reply = Arrays.toString(temp);
                    byte[] replyBytes = reply.getBytes();
                    int replyLength = replyBytes.length;
                    InetAddress clientIP = requestPacket.getAddress();
                    int clientPort = requestPacket.getPort();

                    DatagramPacket replyPacket = new DatagramPacket(replyBytes, replyLength, clientIP, clientPort);
                    serverSocket.send(replyPacket);
                }

            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
