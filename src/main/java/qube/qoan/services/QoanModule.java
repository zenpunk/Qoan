package qube.qoan.services;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import qube.qoan.authentication.UserManager;

/**
 * Created by rainbird on 11/2/15.
 */
public class QoanModule extends AbstractModule {

    // for the time being we leave it at that
    private static String QAI_NODE_NAME = "QaiNode";
    private HazelcastInstance hazelcastInstance;
    private UserManager userManager;
    @Override
    protected void configure() {
        // for the moment being there is not much here to configure really
        //bind(ProcedureSource.class).to(ProcedureSourceService.class);
    }

    @Provides @Singleton
    UserManager provideUserManager() {

        if (userManager != null) {
            return userManager;
        }

        userManager = new UserManager();
        return userManager;
    }

    @Provides @Singleton
    HazelcastInstance provideHazelcastInstance() {

        if (hazelcastInstance != null) {
            return hazelcastInstance;
        }

        ClientConfig clientConfig = new ClientConfig();
        //clientConfig.setInstanceName(QAI_NODE_NAME);
        clientConfig.getNetworkConfig().addAddress("127.0.0.1:5701");
        hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);

        return hazelcastInstance;
    }
}
