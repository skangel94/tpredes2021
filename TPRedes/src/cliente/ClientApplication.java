import java.io.IOException;

public class ClientApplication {

	public static void main(String[] args) {
		try {
			Client.process();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
