package live.source.Stream.PulsarClient;

import live.source.Stream.IStreamingEngine;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public interface IPulsarClientBehavior extends IStreamingEngine<String> {

    PulsarClient getPulsarClient() throws PulsarClientException;
}
