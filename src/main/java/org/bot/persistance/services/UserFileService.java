package org.bot.persistance.services;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.WritePolicy;
import org.bot.persistance.configuration.AerospikeClientConnection;

public class UserFileService implements AutoCloseable {
    private final AerospikeClientConnection aerospikeClientConnection;
    private final WritePolicy wPolicy;

    public UserFileService() {
        this.aerospikeClientConnection = AerospikeClientConnection.getInstance();
        this.wPolicy = new WritePolicy();
    }

    public void createUserFile(String username, String fileId) throws AerospikeException {
        if (username != null && username.length() > 0) {
            wPolicy.recordExistsAction = RecordExistsAction.UPDATE;
            Key key = new Key("test", "simpleFilters", username);
            Bin bin1 = new Bin("username", username);
            Bin bin2 = new Bin("fileId", fileId);
            aerospikeClientConnection.getClient().put(wPolicy, key, bin1, bin2);
        }
    }

    public String getUserFile(String username) {
        Key userKey = new Key("test", "simpleFilters", username);
        Record userRecord = aerospikeClientConnection.getClient().get(null, userKey);
        return userRecord.getValue("fileId").toString();
    }

    @Override
    public void close() throws Exception {
        aerospikeClientConnection.close();
    }
}
