package learning;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.core.CoreContainer;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/*
* Copyright Mozilla Public License 1.1
*/
public class SolrJTest {
    String solrHomeRelativePath = "src/test/resources/learning/solr/";
    String solrXMLRelativePath = "conf/solr.xml";
    CoreContainer coreContainer;
    SolrServer solrServer;

    @Before
    public void before()throws Exception{
        solrServer = startUpSolrServer();
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }

    private SolrServer startUpSolrServer()throws Exception{
        File solrConfigXml = new File(solrHomeRelativePath + solrXMLRelativePath);
        String solrHome = solrHomeRelativePath;
        coreContainer = new CoreContainer(solrHome, solrConfigXml);
        SolrServer solrServer = new EmbeddedSolrServer(coreContainer, "collection1");
        return solrServer;
    }

    @Test
    public void can_add_document()throws Exception{
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        docs.add(doc1Example());
        docs.add(doc2Example());
        solrServer.add(docs);
        solrServer.commit();
    }

    @Test
    public void can_query_documents()throws Exception{
        add1Document();
        SolrDocumentList docs = resultsOfQuery(
                new SolrQuery().setQuery("*:*")
        );
        assertThat(docs.size(), is(1));
    }

    @Test
    public void can_query_for_field_value()throws Exception{
        add2Documents();
        SolrDocumentList docs = resultsOfQuery(
                new SolrQuery().setQuery("*:*")
        );
        assertThat(docs.size(), is(2));
        docs = resultsOfQuery(
                new SolrQuery().setQuery("name:doc1")
        );

        assertThat(docs.size(), is(1));
    }

    @Test
    public void can_search()throws Exception{
        add2Documents();
        SolrDocumentList docs = resultsOfQuery(
                new SolrQuery().setQuery("*:*")
        );
        assertThat(docs.size(), is(2));
        docs = resultsOfQuery(
                new SolrQuery().setQuery("name:*doc*")
        );
        assertThat(docs.size(), is(2));
    }


    @Test
    public void can_get_auto_complete_suggestions()throws Exception{
        add2Documents();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam(CommonParams.QT, "/suggest");
        solrQuery.setParam(CommonParams.Q, "do");
        List<String> suggestions = suggestionsOfQuery(solrQuery);
        assertThat(suggestions.size(), is(2));
    }

    @Test
    public void can_persist_documents()throws Exception{
        assertTrue(coreContainer.isPersistent());
        add2Documents();
        assertFalse(getAllDocsUsingSolr().isEmpty());
        coreContainer.shutdown();
        try{
            getAllDocsUsingSolr().isEmpty();
            fail();
        }catch(SolrServerException e){

        }
        solrServer = startUpSolrServer();
        assertFalse(getAllDocsUsingSolr().isEmpty());
    }

    private SolrDocumentList getAllDocsUsingSolr()throws Exception{
        return resultsOfQuery(
                new SolrQuery().setQuery("*:*")
        );
    }

    private SolrDocumentList resultsOfQuery(SolrQuery solrQuery)throws Exception{
        return solrServer.query(solrQuery).getResults();
    }

    private List<String> suggestionsOfQuery(SolrQuery solrQuery)throws Exception{
        return solrServer.query(solrQuery).getSpellCheckResponse().getSuggestions().get(0).getAlternatives();
    }

    private void add1Document()throws Exception{
        solrServer.add(doc1Example());
        solrServer.commit();
    }

    private void add2Documents()throws Exception{
        solrServer.add(new ArrayList<SolrInputDocument>(){{
            add(doc1Example());
            add(doc2Example());
        }});
        solrServer.commit();
    }

    private SolrInputDocument doc1Example(){
        SolrInputDocument doc1 = new SolrInputDocument();
        doc1.addField( "id", "id1", 1.0f );
        doc1.addField( "name", "doc1", 1.0f );
        doc1.addField( "price", 10 );
        return doc1;
    }

    private SolrInputDocument doc2Example(){
        SolrInputDocument doc2 = new SolrInputDocument();
        doc2.addField( "id", "id2", 1.0f );
        doc2.addField( "name", "doc2", 1.0f );
        doc2.addField( "price", 20 );
        return doc2;
    }
}
