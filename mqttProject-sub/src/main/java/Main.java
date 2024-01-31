public class Main
{
	public static void main(String... args)
	{
		String topic = "topic/test";

		Subscriber sub1 = new Subscriber("SUB1", topic);
		Subscriber sub2 = new Subscriber("SUB2", topic);
		Subscriber sub3 = new Subscriber("SUB3", topic);

		sub1.subscribe();
		sub2.subscribe();
		sub3.subscribe();
	}
}
