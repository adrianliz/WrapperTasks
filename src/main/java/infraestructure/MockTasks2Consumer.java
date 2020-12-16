package infraestructure;

import application.ProxyWS3270;
import domain.Proxy3270Emulator;

public class MockTasks2Consumer {
	public static void main(String[] args) {
		try {
			Proxy3270Emulator proxy = new ProxyWS3270("ws3270");
			if (proxy.connect("155.210.71.101", "123")) System.out.println("Connected!");
			if (proxy.disconnect()) System.out.println("Disconnected!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
