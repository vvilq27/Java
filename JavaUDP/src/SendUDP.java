import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

//to read UPD packets in linux use:
// tcpdump -i wlan0 udp port 8125 -vv -X

public class SendUDP {
    private DatagramSocket socket;
    private InetAddress address;
 
    private byte[] buf;
 
    public SendUDP() {
        try {
			socket = new DatagramSocket();
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
        try {
			address = InetAddress.getByName("89.65.242.51");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    }
 
    public void sendEcho(String msg) {
        buf = msg.getBytes();
        DatagramPacket packet 
          = new DatagramPacket(buf, buf.length, address, 9999);
        try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        // receive part if needed
    /*
        packet = new DatagramPacket(buf, buf.length);
        try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String received = new String(
          packet.getData(), 0, packet.getLength());
        return received;
     */
    }
 
    public void close() {
        socket.close();
    }
    
	public static void main(String[] args) {
		SendUDP s = new SendUDP();
		s.sendEcho("hello my nigga!, hihixd");
		s.close();
	}
    
}
