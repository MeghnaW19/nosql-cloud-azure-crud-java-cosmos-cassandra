package com.stackroute.hotelapp.util;


import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.datastax.oss.driver.shaded.guava.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Duration;

/**
 * This class utility class for creating connection to Azure CosmosDB for Cassandra.
 * It also has methods for creating the keyspace and table in Azure CosmosDB
 */
public class DatabaseUtility {

    private static final String SSL = "TLSv1.2";
    private static final String DB_HOST;
    private static final int DB_PORT;
    private static final String DB_USERNAME;
    private static final String DB_PASSWORD;
    private static final String DB_REGION;
    private static final String KEYSTORE_FILE_TYPE;
    private static final String KEYSTORE_FILE_PATH;
    private static final String KEYSTORE_FILE_PASSWORD;
    private static final String KEYSPACE;
    private static final String TABLE_NAME;


    private static Logger logger = LoggerFactory.getLogger(DatabaseUtility.class);

    static {
        DB_HOST = PropertyConfig.getProperty("db.host");
        DB_PORT = Integer.parseInt(PropertyConfig.getProperty("db.port"));
        DB_USERNAME = PropertyConfig.getProperty("db.username");
        DB_PASSWORD = PropertyConfig.getProperty("db.password");
        DB_REGION = PropertyConfig.getProperty("db.region");
        KEYSPACE = PropertyConfig.getProperty("keyspace.name");
        TABLE_NAME = PropertyConfig.getProperty("table.name");
        KEYSTORE_FILE_TYPE = "JKS";
        KEYSTORE_FILE_PATH = System.getenv("JAVA_HOME") + "/lib/security/cacerts";
        KEYSTORE_FILE_PASSWORD = "changeit";

    }

    /* **TODO**
     * The below method should create a CQLSession with Azure CosmosDB for Cassandra and return the session
     * Use CQLSession builder to configure the host, port, region, username, password and ssl context
     *  Method getSSLContext() is provided to get the SSLContext
     */
    public CqlSession getSession() {
        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress(DB_HOST, DB_PORT))
                .withSslContext(getSSLContext())
                .withLocalDatacenter(DB_REGION)
                .withAuthCredentials(DB_USERNAME, DB_PASSWORD)
                .build();
    }

    /* This method provides the SSLContext to connect securely to the database
     * using the configurations defined in application.properties file
     * **TODO**
     * Ensure that JAVA_HOME is configured in your machine and check the
     * path for `cacerts` is correct
     */
    private SSLContext getSSLContext() {
        SSLContext sslContext = null;
        try {
            final KeyStore keyStore = KeyStore.getInstance(KEYSTORE_FILE_TYPE);

            File sslKeyStoreFile = new File(KEYSTORE_FILE_PATH);
            try (final InputStream is = new FileInputStream(sslKeyStoreFile)) {
                keyStore.load(is, KEYSTORE_FILE_PASSWORD.toCharArray());

                final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                kmf.init(keyStore, KEYSTORE_FILE_PASSWORD.toCharArray());
                final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(keyStore);

                sslContext = SSLContext.getInstance(SSL);
                sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new java.security.SecureRandom());
            }
        } catch (NoSuchAlgorithmException | KeyManagementException | CertificateException
                | KeyStoreException | UnrecoverableKeyException | IOException exception) {
            logger.error("SSLContext initialization failed", exception);
            throw new SecurityException("SSL initialization failed");
        }
        return sslContext;
    }

    /* **TODO**
     * The below method should use the provided CQLSession to create a keyspace
     * using the Datastax SchemaBuilder api, if the keyspace does not exist
     * The keyspace name should be retrieved from application.properties
     * SimpleStrategy should be used for replication.
     * Set a timeout of 20 seconds for execution of query
     */
    public void createKeyspace(CqlSession session) {
        CreateKeyspace newKeyspace = SchemaBuilder.createKeyspace(KEYSPACE)
                .ifNotExists()
                .withReplicationOptions(ImmutableMap.of("class", (Object) "SimpleStrategy"));
        session.execute(newKeyspace.build().setTimeout(Duration.ofSeconds(20)));
    }

    /* **TODO**
     * The below method should use the provided CQLSession to create a table
     * using Datastax SchemaBuilder api, if the table does not exist
     * The keyspace and table name should be retrieved from application.properties
     * Set a timeout of 40 seconds for execution of query
     */
    public void createTable(CqlSession session) {
        CreateTable createTable = SchemaBuilder.createTable(KEYSPACE, TABLE_NAME)
                .ifNotExists()
                .withPartitionKey("state", DataTypes.TEXT)
                .withClusteringColumn("city", DataTypes.TEXT)
                .withClusteringColumn("hotel_id", DataTypes.INT)
                .withColumn("hotel_name", DataTypes.TEXT)
                .withColumn("website_url", DataTypes.TEXT);
        session.execute(createTable.build().setTimeout(Duration.ofSeconds(40)));
    }

}