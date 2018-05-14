import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;

//to read UPD packets in linux use:
// tcpdump -i wlan0 udp port 8125 -vv -X

public class RecUDP {
    private DatagramSocket socket;
    private InetAddress address;
    private BufferedWriter bw;
    
 
    private byte[] buf = new byte[100];
 
    public RecUDP() {
        try {
			socket = new DatagramSocket(9999);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
        try {
			address = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    }//end constructor
 
    public String receiveUDP() {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String received = new String(packet.getData(), 0, packet.getLength());
        String[] chopList = received.split(",");
        
        try {
        	bw = new BufferedWriter(new FileWriter("test.txt", true));
			for(String str: chopList) {
				bw.write(str);
			}
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			System.out.println(e);
		}
        
        return received;
    }
 
    public void close() {
        socket.close();
    }
    
	public static void main(String[] args) {
		RecUDP r = new RecUDP();
		while(true) {
			String s = r.receiveUDP();
			System.out.println(s);
		}
	}
}