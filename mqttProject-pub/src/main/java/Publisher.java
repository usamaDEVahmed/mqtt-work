import org.eclipse.paho.client.mqttv3.*;

public class Publisher
{
	public static void main(String... args)
	{
		try
		{
			String broker = "tcp://broker.emqx.io:1883";
			final String clientId = "PUB";
			String topic = "topic/test";
			final int QOS = 2;

			MqttClient client = new MqttClient(broker, clientId);
			MqttConnectOptions options = new MqttConnectOptions();
			client.connect(options);

			MqttMessage message = new MqttMessage("Sehar".getBytes());
			message.setQos(QOS);
			client.publish(topic, message);

			client.disconnect();
			client.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
