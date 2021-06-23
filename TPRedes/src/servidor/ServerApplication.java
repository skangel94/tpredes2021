
public class ServerApplication {

	public static void main(String[] args) {
		try {
			new Server().process();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
