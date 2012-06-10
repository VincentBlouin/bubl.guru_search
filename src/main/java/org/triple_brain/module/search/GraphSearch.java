package org.triple_brain.module.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.core.CoreContainer;
import org.triple_brain.graphmanipulator.jena.graph.JenaGraphManipulator;
import org.triple_brain.module.model.User;
import org.triple_brain.module.model.graph.Vertex;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.triple_brain.module.common_utils.CommonUtils.decodeURL;

/*
* Copyright Mozilla Public License 1.1
*/
public class GraphSearch {

    private CoreContainer coreContainer;
    private SearchUtils searchUtils;

    public static GraphSearch withCoreContainer(CoreContainer coreContainer){
        return new GraphSearch(coreContainer);
    }

    private GraphSearch(CoreContainer coreContainer){
        this.coreContainer = coreContainer;
        this.searchUtils = SearchUtils.usingCoreCoreContainer(coreContainer);
    }

    List<Vertex> searchVerticesForAutoCompletionByLabelAndUser(String label, User user){
        List<Vertex> vertices = new ArrayList<>();
        try{
            GraphSearchInUser graphSearchInUser = new GraphSearchInUser(
                    searchUtils.solrServerFromUser(user),
                    JenaGraphManipulator.withUser(user)
            );
            List<String> suggestions = graphSearchInUser.suggestionsForLabel(label);
            for(String suggestion : suggestions){
                vertices.addAll(
                        graphSearchInUser.verticesWithLabel(suggestion)
                );
            }
        }catch (SolrServerException | UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
        return vertices;
    }

    private class GraphSearchInUser{
        private SolrServer solrServer;
        private JenaGraphManipulator graphManipulator;
        public GraphSearchInUser (SolrServer solrServer, JenaGraphManipulator graphManipulator){
            this.solrServer = solrServer;
            this.graphManipulator = graphManipulator;
        }

        public List<String> suggestionsForLabel(String label)throws SolrServerException{
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.setParam(CommonParams.QT, "/suggest");
            solrQuery.setParam(CommonParams.Q, label);
            return solrServer.query(solrQuery).getSpellCheckResponse().getSuggestions().get(0).getAlternatives();
        }

        public Set<Vertex> verticesWithLabel(String label)throws SolrServerException, UnsupportedEncodingException{
            Set<Vertex> vertices = new HashSet<>();
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.setQuery("label:\""+label+"\"");
            QueryResponse queryResponse = solrServer.query(solrQuery);
            for(SolrDocument document : queryResponse.getResults()){
                vertices.add(
                        graphManipulator.vertexWithURI(
                                decodeURL((String) document.get("uri"))
                        )
                );
            }
            return vertices;
        }
    }

}
