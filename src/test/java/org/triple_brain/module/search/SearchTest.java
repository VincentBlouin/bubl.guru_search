package org.triple_brain.module.search;

import com.google.inject.Guice;
import graph.JenaSQLTestModule;
import graph.mock.JenaGraphManipulatorMock;
import graph.scenarios.GraphScenariosGenerator;
import graph.scenarios.VertexABAndC;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.triple_brain.graphmanipulator.jena.graph.JenaEdgeManipulator;
import org.triple_brain.graphmanipulator.jena.graph.JenaVertexManipulator;
import org.triple_brain.module.model.User;
import org.triple_brain.module.model.graph.Vertex;

/*
* Copyright Mozilla Public License 1.1
*/
public class SearchTest {

    protected JenaGraphManipulatorMock graphManipulator;
    protected JenaVertexManipulator vertexManipulator;
    protected JenaEdgeManipulator edgeManipulator;

    protected Vertex vertexA;
    protected Vertex vertexB;
    protected Vertex vertexC;

    protected User user;

    protected static SolrInstance solrInstance;

    @BeforeClass
    public static void beforeClass(){
        Guice.createInjector(new JenaSQLTestModule());
        solrInstance = SolrInstance.withSolrHomeAndRelativeSolrXmlPath(
                "src/test/resources/solr/",
                "conf/solr.xml"
                );
    }

    @AfterClass
    public static void afterClass(){
        solrInstance.coreContainer().shutdown();
    }

    @Before
    public void before() throws Exception{
        user = User.withUsernameAndEmail("test", "test@example.org");
        solrServer().deleteByQuery("*:*");
        solrServer().commit();
        graphManipulator = JenaGraphManipulatorMock.mockWithUser(user);
        vertexManipulator = JenaVertexManipulator.withUser(user);
        edgeManipulator = JenaEdgeManipulator.withUser(user);
        makeGraphHave3VerticesABCWhereAIsDefaultCenterVertexAndAPointsToBAndBPointsToC();
    }

    protected void makeGraphHave3VerticesABCWhereAIsDefaultCenterVertexAndAPointsToBAndBPointsToC() throws Exception {
        GraphScenariosGenerator graphScenariosGenerator = GraphScenariosGenerator.withUserManipulators(
                user,
                graphManipulator,
                vertexManipulator,
                edgeManipulator
        );
        VertexABAndC vertexABAndC = graphScenariosGenerator.makeGraphHave3VerticesABCWhereAIsDefaultCenterVertexAndAPointsToBAndBPointsToC();
        vertexA = vertexABAndC.vertexA();
        vertexB = vertexABAndC.vertexB();
        vertexC = vertexABAndC.vertexC();
    }

    protected SolrServer solrServer(){
        return solrInstance.solrServer();
    }

    protected SolrDocumentList resultsOfSearchQuery(SolrQuery solrQuery)throws Exception{
        QueryResponse queryResponse = solrServer().query(solrQuery);
        return queryResponse.getResults() == null ?
                new SolrDocumentList() :
                solrServer().query(solrQuery).getResults();
    }
}
