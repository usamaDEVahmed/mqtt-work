import org.eclipse.paho.client.mqttv3.*;
import java.nio.charset.StandardCharsets;

public class Subscriber
{
	private String clientId;
	private String topic;
	private final int QOS = 2;

	public Subscriber(String clientId, String topic)
	{
		this.clientId = clientId;
		this.topic = topic;
	}

	public void subscribe()
	{
		try
		{
			System.out.println(this.clientId + " Subscription initialized");

			String broker = "tcp://broker.emqx.io:1883";
			MqttClient client = new MqttClient(broker, this.clientId);
			MqttConnectOptions options = new MqttConnectOptions();
			client.connect(options);

			client.setCallback(new MqttCallback()
			{
				@Override
				public void connectionLost(Throwable throwable)
				{
					System.out.println(clientId + "\nConnection Lost: " + throwable.getMessage());
				}

				@Override
				public void messageArrived(String s, MqttMessage mqttMessage) throws Exception
				{
					System.out.println(clientId + ": " +
							"\n" + "Topic: " + topic + "\n" +
							"QOS: " + mqttMessage.getQos() + "\n" +
							"Message: " + new String(mqttMessage.getPayload(), StandardCharsets.UTF_8));
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken)
				{
					System.out.println(clientId + "\nDelivery Completion Status: " + iMqttDeliveryToken.isComplete());
				}
			});

			try
			{
				System.out.println("Waiting for Subscription");
				client.subscribe(this.topic, QOS);
			}
			catch (Exception e)
			{
				client.disconnect();
				client.close();

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
