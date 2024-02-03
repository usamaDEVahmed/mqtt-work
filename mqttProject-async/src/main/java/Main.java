public class Main
{
	public static void main(String... args) throws InterruptedException {
		AsyncClient client = new AsyncClient("AsyncClient1", "BOSS_TOPIC");
		client.setup();

		client.publish("Message from client 1");
	}
}
