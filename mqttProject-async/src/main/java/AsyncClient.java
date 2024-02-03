import org.eclipse.paho.client.mqttv3.*;

public class AsyncClient
{
	private String clientId;
	private String topic;
	private final int QOS = 2;

	private final String broker = "tcp://broker.emqx.io:1883";
	private MqttAsyncClient client;

	public AsyncClient(String clientId, String topic)
	{
		this.clientId = clientId;
		this.topic = topic;
	}

	public void setup()
	{
		try
		{
			client = new MqttAsyncClient(broker, clientId);
			MqttConnectOptions options = new MqttConnectOptions();
			options.setCleanSession(false);

			System.out.println("Connecting to the broker");
			IMqttToken connectionToken = client.connect(options);
			connectionToken.waitForCompletion();
			System.out.println("Connected to the broker");

			client.setCallback(new MqttCallback() {
				@Override
				public void connectionLost(Throwable cause) {
					System.out.println("Connection lost!");
				}

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					System.out.println("Message received on topic " + topic + ": " + new String(message.getPayload()));
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					System.out.println("Message has been Successfully delivered");
				}
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void subscribe()
	{
		try
		{
			IMqttToken subToken = client.subscribe(topic, QOS);
			subToken.waitForCompletion();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void publish(String message)
	{
		try
		{
			MqttMessage mqttMessage = new MqttMessage(message.getBytes());
			IMqttToken pubToken = client.publish(topic, mqttMessage);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void disconnect()
	{
		try
		{
			IMqttToken disconnectToken = client.disconnect();
			disconnectToken.waitForCompletion();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
