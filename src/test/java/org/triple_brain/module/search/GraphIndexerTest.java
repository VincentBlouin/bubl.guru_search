package org.triple_brain.module.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.triple_brain.module.model.User;
import org.triple_brain.module.model.graph.Vertex;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.triple_brain.module.common_utils.Uris.encodeURL;

/*
* Copyright Mozilla Public License 1.1
*/

public class GraphIndexerTest extends SearchRelatedTest {

    @Test
    public void can_index_vertex()throws Exception{
        SolrDocumentList documentList = queryVertex(vertexA);
        assertThat(documentList.size(), is(0));
        graphIndexer().indexVertexOfUser(vertexA, user);
        documentList = queryVertex(vertexA);
        assertThat(documentList.size(), is(1));
        assertThat(
                labelOfGraphElementSearchResult(documentList.get(0)),
                is("vertex Azure")
        );
    }

    @Test
    public void can_create_user_core(){
        User nonExisting = testScenarios.randomUser();
        SolrServer solrServer = searchUtils.solrServerFromUser(nonExisting);
        try{
            solrServer.query(new SolrQuery("*:*"));
            fail();
        }catch(SolrServerException e){
              assertTrue(e.getCause().getMessage().startsWith("No such core"));
        }
        graphIndexer().createUserCore(nonExisting);
        solrServer = searchUtils.solrServerFromUser(nonExisting);
        try{
            solrServer.query(new SolrQuery("*:*"));
        }catch(SolrServerException e){
            fail();
        }
    }

    @Test
    public void create_core_can_be_reused_after_server_shutdown()throws Exception{
        User nonExisting = testScenarios.randomUser();
        graphIndexer().createUserCore(nonExisting);
        coreContainer.shutdown();
        coreContainer = getCoreContainerForTests();
        searchUtils = SearchUtils.usingCoreCoreContainer(coreContainer);
        SolrServer solrServer = searchUtils.solrServerFromUser(nonExisting);
        try{
            solrServer.query(new SolrQuery("*:*"));
        }catch (SolrServerException e){
            fail();
        }
    }

    private String labelOfGraphElementSearchResult(SolrDocument solrDocument){
        return (String) solrDocument.getFieldValue("label");
    }

    private SolrDocumentList queryVertex(Vertex vertex)throws Exception{
        return resultsOfSearchQuery(
                new SolrQuery().setQuery(
                        "uri:" + encodeURL(vertex.id())
                )
        );
    }
}