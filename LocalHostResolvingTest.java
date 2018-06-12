import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.*;

@RunWith(JUnit4.class)
public class LocalHostResolvingTest {

    @Test
    public void shouldBindToInetAddressGetLocalHost() throws IOException {
        Socket s = new Socket();
        InetAddress host = InetAddress.getLocalHost();
        InetSocketAddress address = new InetSocketAddress(host, 0);
        System.out.println("InetAddress.getLocalHost - Address - " + address);
        s.bind(address);
        s.close();
        System.out.println("success");
    }

    @Test
    public void shouldBindTolocalhost() throws IOException {
        Socket s = new Socket();
        String host = "localhost";
        InetSocketAddress address = new InetSocketAddress(host, 0);
        System.out.println("'localhost' - Address - " + address);
        s.bind(address);
        s.close();
        System.out.println("success");
    }

    @Test(expected = BindException.class)
    public void shouldNotBindToNonLoopbackAddress() throws IOException {
        InetAddress[] allByName = InetAddress.getAllByName(InetAddress.getLocalHost().getCanonicalHostName());
        String externalAddress = "";
        for (InetAddress address : allByName) {
            if (!address.isLoopbackAddress())
                externalAddress = address.getHostAddress();
        }

        Socket s = new Socket();
        InetSocketAddress address = new InetSocketAddress(externalAddress, 0);
        System.out.println("'externalAddress' - Address - " + address);
        s.bind(address);
        s.close();
        System.out.println("InetAddress");
    }

    @Test
    public void shouldBindToAnyLocalHost() throws IOException {
        Socket s = new Socket();
        String host = "0.0.0.0";
        InetSocketAddress address = new InetSocketAddress(host, 0);
        System.out.println("'0.0.0.0' - Address - " + address);
        s.bind(address);
        s.close();
        System.out.println("InetAddress");
    }

}
