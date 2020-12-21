package UnityTestSuit;

import application.MusicAPI;
import domain.MainframeAPI;
import domain.Response3270;
import domain.enums.ErrorMessage;
import domain.enums.ScreenIndicator;
import domain.exceptions.AuthException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
	private static Response3270 defaultResponse;
	private static Response3270 userIdUnauthorized;
	private static Response3270 passwordIncorrect;
	private static Response3270 userIdInUse;

	@BeforeAll
	public static void setUpClass() {
		defaultResponse = new Response3270() {
			@Override
			public String getParsedData() {
				return "";
			}

			@Override
			public boolean contains(String indicator) {
				return indicator.equals("");
			}

			@Override
			public boolean success() {
				return true;
			}

			@Override
			public boolean isConnected() {
				return true;
			}
		};

		userIdUnauthorized = new Response3270() {
			@Override
			public String getParsedData() {
				return "";
			}

			@Override
			public boolean contains(String indicator) {
				return indicator.equals(ScreenIndicator.MUSIC_USERID_UNAUTHORIZED.toString());
			}

			@Override
			public boolean success() {
				return true;
			}

			@Override
			public boolean isConnected() {
				return true;
			}
		};

		passwordIncorrect = new Response3270() {
			@Override
			public String getParsedData() {
				return "";
			}

			@Override
			public boolean contains(String indicator) {
				return indicator.equals(ScreenIndicator.MUSIC_PWD_INCORRECT.toString());
			}

			@Override
			public boolean success() {
				return true;
			}

			@Override
			public boolean isConnected() {
				return true;
			}
		};

		userIdInUse = new Response3270() {
			@Override
			public String getParsedData() {
				return "";
			}

			@Override
			public boolean contains(String indicator) {
				return indicator.equals(ScreenIndicator.MUSIC_USERID_IN_USE.toString());
			}

			@Override
			public boolean success() {
				return true;
			}

			@Override
			public boolean isConnected() {
				return true;
			}
		};
	}

	@Test
	public void shouldLogin() throws AuthException {
		String login = "pepe";
		String pwd = "pepe";

		MainframeAPI mainframe = new MusicAPI(new Fake3270Emulator(defaultResponse));
		mainframe.login(login, pwd);
	}

	@Test()
	public void shouldThrowUserIdUnauthorized() {
		String login = "pepe";
		String pwd = "pepe";

		MainframeAPI mainframe = new MusicAPI(new Fake3270Emulator(userIdUnauthorized));

		Throwable ex = assertThrows(AuthException.class, () -> mainframe.login(login, pwd));
		assertEquals(ErrorMessage.USERID_UNAUTHORIZED.toString(), ex.getMessage());
	}
	
	@Test
	public void shouldThrowPasswordIncorrect() {
		String login = "pepe";
		String pwd = "pepe";

		MainframeAPI mainframe = new MusicAPI(new Fake3270Emulator(passwordIncorrect));

		Throwable ex = assertThrows(AuthException.class, () -> mainframe.login(login, pwd));
		assertEquals(ErrorMessage.PWD_ERROR.toString(), ex.getMessage());
	}

	@Test
	public void shouldThrowUserIdInUse() {
		String login = "pepe";
		String pwd = "pepe";

		MainframeAPI mainframe = new MusicAPI(new Fake3270Emulator(userIdInUse));

		Throwable ex = assertThrows(AuthException.class, () -> mainframe.login(login, pwd));
		assertEquals(ErrorMessage.USERID_IN_USE.toString(), ex.getMessage());
	}
}
