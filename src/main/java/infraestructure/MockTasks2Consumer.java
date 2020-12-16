package infraestructure;

import application.MusicAPI;
import application.ProxyWS3270;
import domain.MainframeAPI;
import domain.Proxy3270Emulator;

public class MockTasks2Consumer {
	public static void main(String[] args) {
		try {
			Proxy3270Emulator proxy = new ProxyWS3270("ws3270");
			MainframeAPI mainframe = new MusicAPI(proxy);

			if (proxy.connect("155.210.71.101", "123").success()) {
				System.out.println("Connected!");

				mainframe.login("prog", "prog123");
				System.out.println("Login!");
				mainframe.logout();
				System.out.println("Logout!");
			}

			if (proxy.disconnect().success()) System.out.println("Disconnect!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
