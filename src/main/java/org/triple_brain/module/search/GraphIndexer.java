package org.triple_brain.module.search;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.core.CoreContainer;
import org.triple_brain.module.model.User;
import org.triple_brain.module.model.graph.Edge;
import org.triple_brain.module.model.graph.GraphElement;
import org.triple_brain.module.model.graph.Vertex;

import java.io.IOException;

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

    public void indexVertex(Vertex vertex) {
        indexVertexOfUser(
                vertex,
                vertex.owner()
        );
    }

    public void indexVertexOfUser(Vertex vertex, User user) {
        try {
            SolrInputDocument document = graphElementToDocument(vertex, user);
            document.addField("is_vertex", true);
            document.addField("is_public", vertex.isPublic());
            document.addField("comment", vertex.comment());
            for (Edge edge : vertex.connectedEdges()) {
                document.addField(
                        "relation_name",
                        edge.label()
                );
            }
            SolrServer solrServer = searchUtils.getServer();
            solrServer.add(document);
            solrServer.commit();
        } catch (IOException | SolrServerException e) {
            throw new RuntimeException(e);
        }
    }

    public void indexRelationOfUser(Edge edge, User user) {
        try {
            SolrInputDocument document = graphElementToDocument(edge, user);
            document.addField("is_vertex", false);
            SolrServer solrServer = searchUtils.getServer();
            solrServer.add(document);
            solrServer.commit();
        } catch (IOException | SolrServerException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteGraphElementOfUser(GraphElement graphElement, User user) {
        try {
            SolrServer solrServer = searchUtils.getServer();
            solrServer.deleteByQuery(
                    "uri:" + encodeURL(
                            graphElement.uri()
                    )
            );
            solrServer.commit();
        } catch (IOException | SolrServerException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        coreContainer.shutdown();
    }

    private SolrInputDocument graphElementToDocument(GraphElement graphElement, User owner) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("uri", encodeURL(graphElement.uri()));
        document.addField("label", graphElement.label());
        document.addField("owner_username", owner.username());
        return document;
    }
}
