package org.triple_brain.module.search;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.triple_brain.module.model.graph.GraphFactory;
import org.triple_brain.module.model.graph.jena.JenaTestModule;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.core.CoreContainer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.triple_brain.module.model.User;
import org.triple_brain.module.model.graph.Vertex;
import org.triple_brain.module.model.graph.scenarios.TestScenarios;
import org.triple_brain.module.model.graph.scenarios.VerticesCalledABAndC;

import javax.inject.Inject;
import java.io.File;

/*
* Copyright Mozilla Public License 1.1
*/
public class SearchRelatedTest {


    @Inject
    GraphFactory graphMaker;

    @Inject
    protected TestScenarios testScenarios;

    protected SearchUtils searchUtils;
    protected Vertex vertexA;
    protected Vertex vertexB;
    protected Vertex vertexC;
    protected Vertex pineApple;
    protected User user;
    protected static CoreContainer coreContainer;

    protected static Injector injector;

    @BeforeClass
    public static void beforeClass()throws Exception{
        injector = Guice.createInjector(new JenaTestModule());
        coreContainer = getCoreContainerForTests();
    }

    protected static CoreContainer getCoreContainerForTests()throws Exception{
        String solrHomePath = "src/test/resources/solr/";
        String solrXMLPath = "conf/solr.xml";
        File solrConfigXml = new File(solrHomePath + solrXMLPath);
        return new CoreContainer(solrHomePath, solrConfigXml);
    }

    @Before
    public void before() throws Exception{
        injector.injectMembers(this);
        searchUtils = SearchUtils.usingCoreCoreContainer(coreContainer);
        user = User.withUsernameAndEmail("test", "test@example.org");
        graphIndexer().createUserCore(user);
        deleteAllDocsOfUser(user);
        makeGraphHave3SerialVerticesWithLongLabels();
        pineApple = testScenarios.addPineAppleVertexToVertex(vertexC);
    }

    @AfterClass
    public static void afterClass(){
        coreContainer.shutdown();
    }



    protected void makeGraphHave3SerialVerticesWithLongLabels() throws Exception {
        VerticesCalledABAndC vertexABAndC = testScenarios.makeGraphHave3SerialVerticesWithLongLabels(
                graphMaker.createForUser(user)
        );
        vertexA = vertexABAndC.vertexA();
        vertexB = vertexABAndC.vertexB();
        vertexC = vertexABAndC.vertexC();
    }

    protected void deleteAllDocsOfUser(User user)throws Exception{
        SolrServer solrServer = solrServerFromUser(user);
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }

    protected SolrDocumentList resultsOfSearchQuery(SolrQuery solrQuery)throws Exception{
        SolrServer solrServer = solrServerFromUser(user);
        QueryResponse queryResponse = solrServer.query(solrQuery);
        return queryResponse.getResults() == null ?
                new SolrDocumentList() :
                solrServer.query(solrQuery).getResults();
    }

    protected SolrServer solrServerFromUser(User user){
        return searchUtils.solrServerFromUser(user);
    }

    protected GraphIndexer graphIndexer(){
        return GraphIndexer.withCoreContainer(coreContainer);
    }

    protected void indexVertexABAndC(){
        GraphIndexer graphIndexer = GraphIndexer.withCoreContainer(coreContainer);
        graphIndexer.indexVertexOfUser(vertexA, user);
        graphIndexer.indexVertexOfUser(vertexB, user);
        graphIndexer.indexVertexOfUser(vertexC, user);
    }

    protected void indexVertex(Vertex vertex){
        GraphIndexer graphIndexer = GraphIndexer.withCoreContainer(coreContainer);
        graphIndexer.indexVertexOfUser(pineApple, user);
    }
}