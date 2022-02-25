package org.bot.persistance.configuration;

import com.aerospike.client.AerospikeClient;

public class AerospikeClientConnection implements AutoCloseable {
    private final AerospikeClient aerospikeClient;

    private AerospikeClientConnection(AerospikeClient aerospikeClient) {
        this.aerospikeClient = aerospikeClient;
    }

    public static AerospikeClientConnection getInstance() {
        return new AerospikeClientConnection(new AerospikeClient("127.0.0.1", 3000));
    }

    public AerospikeClient getClient() {
        return aerospikeClient;
    }

    @Override
    public void close() throws Exception {
        this.aerospikeClient.close();
    }
}
