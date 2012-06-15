package org.triple_brain.module.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.core.CoreContainer;
import org.triple_brain.graphmanipulator.jena.graph.JenaGraphManipulator;
import org.triple_brain.module.model.User;
import org.triple_brain.module.model.graph.Vertex;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

    public List<Vertex> searchVerticesForAutoCompletionByLabelAndUser(String label, User user){
        List<Vertex> vertices = new ArrayList<>();
        JenaGraphManipulator jenaGraphManipulator = JenaGraphManipulator.withUser(user);
        try{
            SolrServer solrServer = searchUtils.solrServerFromUser(user);
            SolrQuery solrQuery = new SolrQuery();
            String sentenceMinusLastWord = sentenceMinusLastWord(label);
            String lastWord = lastWordOfSentence(label);
            solrQuery.setQuery("label:"+sentenceMinusLastWord +"*");
            solrQuery.addFilterQuery("label:"+sentenceMinusLastWord+"*"+lastWord+"*");
            QueryResponse queryResponse = solrServer.query(solrQuery);
            for(SolrDocument document : queryResponse.getResults()){
                vertices.add(
                        jenaGraphManipulator.vertexWithURI(
                                decodeURL((String) document.get("uri"))
                        )
                );
            }
        }catch (SolrServerException | UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
        return vertices;
    }

    private String lastWordOfSentence(String sentence){
        StringTokenizer tokenizer = new StringTokenizer(
                sentence,
                " "
        );
        String lastWord = "";
        while(tokenizer.hasMoreTokens()){
            lastWord = tokenizer.nextToken();
        }
        return lastWord;
    }

    private String sentenceMinusLastWord(String sentence){
        if(sentence.contains(" ")){
            return sentence.substring(0, sentence.lastIndexOf(" "));
        }else{
            return "";
        }
    }
}
