import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.net.*;
import java.io.IOException;

@RunWith(JUnit4.class)
public class LocalHostResolvingTest {

	@Test
	public void shouldBindToLocalHost() throws InterruptedException, IOException {
		Socket s = new Socket();
    		InetAddress host = InetAddress.getLocalHost();
    		InetSocketAddress address = new InetSocketAddress(host, 0);
		System.out.println("Address - " + address);
    		s.bind(address);
    		s.close();
	}
}
