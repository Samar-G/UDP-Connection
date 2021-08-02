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
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samar
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException {
        try{
            DatagramSocket clientSocket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);
            
            while(true){
                System.out.println("Please enter the list of numbers to be aranged or 'close' to close the connection");
                
                String requestListClose = scanner.nextLine();
                
                if(requestListClose.equalsIgnoreCase("close")){
                    clientSocket.close();
                    System.out.println("Client is terminated");
                    break;
                }
                else{
                    byte[] requestBytesList = requestListClose.getBytes();
                    int requestListLength = requestBytesList.length;
                    InetAddress serverIP = InetAddress.getByName("localhost");
                    int ServerPort = 4000;
                    DatagramPacket requestPacketList = new DatagramPacket(requestBytesList, requestListLength, serverIP, ServerPort);
                    clientSocket.send(requestPacketList);
                    
                    System.out.println("Please choose:\n 1. Ascending order \n 2. Descending order");
                    String request = scanner.nextLine();
                    byte[] requestBytes = request.getBytes();
                    int requestLength = requestBytes.length;
                    InetAddress serverIP2 = InetAddress.getByName("localhost");
                    int ServerPort2 = 4000;
                    DatagramPacket requestPacket = new DatagramPacket(requestBytes, requestLength, serverIP2, ServerPort2);
                    clientSocket.send(requestPacket);
                    
                    byte[] replyBuffer = new byte[5000];
                    DatagramPacket replyPacket = new DatagramPacket(replyBuffer, replyBuffer.length);
                    clientSocket.receive(replyPacket);
                    
                    String reply = new String(replyPacket.getData());
                    System.out.println("Server: "+ reply.trim());
                }
            }
        } catch(SocketException e){
            e.printStackTrace();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
