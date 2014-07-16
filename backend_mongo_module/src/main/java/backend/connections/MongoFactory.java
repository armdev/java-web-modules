package backend.connections;

/**
 *
 * @author armen
 */
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MongoFactory {

    private static final String SYSTEMPROPERTY_SINGLEINSTANCECONTEXT = "mongo.singleinstance";
    private boolean singleInstanceContext;
    private List<ServerAddress> mongoAddresses;
    private MongoOptions mongoOptions;
    private WriteConcern writeConcern;

    public Mongo createMongoInstance(int connectionsPerHost) {
        Mongo mongo = null;
//        if (isSingleInstanceContext()) {
        try {
            ServerAddress serverAddress = new ServerAddress("localhost", 27017);
            mongoOptions.setConnectionsPerHost(connectionsPerHost);
            mongo = new Mongo(serverAddress, mongoOptions);
            mongo.setWriteConcern(determineWriteConcern());
        } catch (UnknownHostException e) {
            throw new MongoInitializationException("Could not create the default Mongo instance", e);
        }
        // }  
        return mongo;
    }

    /**
     * Default constructor that configures the factory to create test context
     * Mongo instances.
     */
    public MongoFactory() {
        this(new ArrayList<ServerAddress>(), new MongoOptions(), WriteConcern.SAFE);
        this.singleInstanceContext = true;
    }

    /**
     * Constructor creating defaults for WriteConcern.REPLICA_SAFE and default
     * MongoOptions
     *
     * @param mongoAddresses List containing the address of the servers to use
     */
    public MongoFactory(List<ServerAddress> mongoAddresses) {
        this(mongoAddresses, new MongoOptions(), WriteConcern.REPLICAS_SAFE);
    }

    /**
     * Constructor that accepts addresses, options and the default write
     * concern. Used to create a production context
     *
     * @param mongoAddresses List of server addresses to configure the Mongo
     * instance with.
     * @param mongoOptions MongoOptions instance to configure the Mongo instance
     * with
     * @param writeConcern WriteConcern to configure for the connection to the
     * Mongo instance
     */
    public MongoFactory(List<ServerAddress> mongoAddresses, MongoOptions mongoOptions, WriteConcern writeConcern) {
        this.mongoAddresses = mongoAddresses;
        this.mongoOptions = mongoOptions;
        this.writeConcern = writeConcern;
        this.singleInstanceContext = false;
    }

    /**
     * Provide a list of ServerAddress objects to use for locating the Mongo
     * replica set.
     *
     * @param mongoAddresses List of ServerAddress instances
     */
    public void setMongoAddresses(List<ServerAddress> mongoAddresses) {
        this.mongoAddresses = mongoAddresses;
    }

    /**
     * Provide an instance of MongoOptions to be used for the connections.
     *
     * @param mongoOptions MongoOptions to overrule the default
     */
    public void setMongoOptions(MongoOptions mongoOptions) {
        this.mongoOptions = mongoOptions;
    }

    /**
     * Sets the singleInstanceContext, provide true if you want the test context
     * and false if you want the production context.
     *
     * @param testContext Boolean indicating the context, true for test and
     * false for production.
     */
    public void setSingleInstanceContext(boolean testContext) {
        this.singleInstanceContext = testContext;
    }

    /**
     * Provided a write concern to be used by the mongo instance.
     *
     * @param writeConcern WriteConcern to use for the connections
     */
    public void setWriteConcern(WriteConcern writeConcern) {
        this.writeConcern = writeConcern;
    }

//        } else {
//            if (mongoAddresses.isEmpty()) {
//                throw new IllegalStateException("Please configure at least 1 instance of Mongo for production.");
//            }
//            mongo = new Mongo(mongoAddresses, mongoOptions);
//        }
    /**
     * Returns whether the factory is used to connect to a single Mongo instance
     * Mongo or to a replica set.
     *
     * @return true if we connect to a single instance Mongo, false for a
     * replica set
     */
    boolean isSingleInstanceContext() {
        String systemTestContext = SYSTEMPROPERTY_SINGLEINSTANCECONTEXT;
        if (null != systemTestContext) {
            return Boolean.parseBoolean(systemTestContext);
        } else {
            return singleInstanceContext;
        }

    }

    private WriteConcern determineWriteConcern() {
        WriteConcern toUseWriteConcern;
        if (isSingleInstanceContext()) {
            if (this.writeConcern != null) {
                if (this.writeConcern.getW() > WriteConcern.SAFE.getW()) {
                    throw new IllegalArgumentException(
                            "Invalid WriteConcern for a single instance Mongo context, can be maximum SAFE");
                }
                toUseWriteConcern = this.writeConcern;
            } else {
                toUseWriteConcern = WriteConcern.SAFE;
            }
        } else {
            if (this.writeConcern != null) {
                toUseWriteConcern = this.writeConcern;
            } else {
                toUseWriteConcern = WriteConcern.REPLICAS_SAFE;
            }
        }

        return toUseWriteConcern;
    }
}
