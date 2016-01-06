package qube.qoan.services;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import qube.qai.services.ProcedureSourceInterface;
import qube.qai.services.SearchServiceInterface;
import qube.qai.services.implementation.DistributedSearchService;
import qube.qoan.authentication.UserManager;
import qube.qoan.gui.components.workspace.procedure.ProcedureMenu;

import javax.inject.Named;

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
        //bind(ProcedureSourceInterface.class).to(ProcedureMenu.class);
    }

    @Provides @Singleton
    ProcedureCache provideProcedureSource() {
        return new ProcedureCache();
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
        clientConfig.getNetworkConfig().addAddress("192.168.1.2:5701");
        hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);

        return hazelcastInstance;
    }

    @Provides @Named("Wiktionary_en") @Singleton
    SearchServiceInterface provideWiktionarySearchService() {
        DistributedSearchService distributedSearch = new DistributedSearchService("Wiktionary_en");
        distributedSearch.setHazelcastInstance(hazelcastInstance);
        distributedSearch.initialize();

        return distributedSearch;
    }

    @Provides @Named("Wikipedia_en") @Singleton
    SearchServiceInterface provideWikipediaSearchService() {
        DistributedSearchService distributedSearch = new DistributedSearchService("Wikipedia_en");
        distributedSearch.setHazelcastInstance(hazelcastInstance);
        distributedSearch.initialize();

        return distributedSearch;
    }
}
