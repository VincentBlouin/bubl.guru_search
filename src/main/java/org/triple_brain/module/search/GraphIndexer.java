package org.triple_brain.module.search;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.core.CoreContainer;
import org.triple_brain.module.model.User;
import org.triple_brain.module.model.graph.GraphElement;
import org.triple_brain.module.model.graph.Vertex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.triple_brain.module.common_utils.Uris.encodeURL;

/*
* Copyright Mozilla Public License 1.1
*/

public class GraphIndexer {

    private CoreContainer coreContainer;
    private SearchUtils searchUtils;

    public static GraphIndexer withCoreContainer(CoreContainer coreContainer) {
        return new GraphIndexer(coreContainer);
    }

    private GraphIndexer(CoreContainer coreContainer) {
        this.coreContainer = coreContainer;
        this.searchUtils = SearchUtils.usingCoreCoreContainer(coreContainer);
    }

    private String configFilePath() {
        return coreContainer.getConfigFile().getParent() + "/" +
                coreContainer.getCore(coreContainer.getDefaultCoreName()).getCoreDescriptor().getConfigName();
    }

    private String schemaFilePath() {
        return coreContainer.getConfigFile().getParent() + "/" +
                coreContainer.getCore(coreContainer.getDefaultCoreName()).getCoreDescriptor().getSchemaName();
    }

    public void indexVertexOfUser(Vertex vertex, User user) {
        try {
            SolrInputDocument document = graphElementToDocument(vertex);
            document.addField("is_vertex", true);
            document.addField("owner_username", user.username());
            SolrServer solrServer = searchUtils.getServer();
            solrServer.add(document);
            solrServer.commit();
        } catch (IOException | SolrServerException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteVertexOfUser(Vertex vertex, User user) {
        try {
            SolrServer solrServer = searchUtils.getServer();
            solrServer.deleteByQuery("uri:" + encodeURL(vertex.id()));
            solrServer.commit();
        } catch (IOException | SolrServerException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        coreContainer.shutdown();
    }

    private SolrInputDocument graphElementToDocument(GraphElement graphElement) throws UnsupportedEncodingException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("uri", encodeURL(graphElement.id()));
        document.addField("label", graphElement.label());
        return document;
    }
}
