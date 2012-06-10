package org.triple_brain.module.search;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.triple_brain.module.model.User;

/*
* Copyright Mozilla Public License 1.1
*/
public class SearchUtils {

    private CoreContainer coreContainer;

    public static SearchUtils usingCoreCoreContainer(CoreContainer coreContainer){
        return new SearchUtils(coreContainer);
    }

    private SearchUtils(CoreContainer coreContainer){
        this.coreContainer = coreContainer;
    }

    public SolrServer solrServerFromUser(User user){
        return new EmbeddedSolrServer(
                coreContainer,
                user.username()
        );
    }
}
