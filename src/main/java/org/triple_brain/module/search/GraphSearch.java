package org.triple_brain.module.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.core.CoreContainer;
import org.codehaus.jettison.json.JSONArray;
import org.triple_brain.module.model.User;
import org.triple_brain.module.search.json.SearchJsonConverter;

import java.util.StringTokenizer;

/*
* Copyright Mozilla Public License 1.1
*/
public class GraphSearch {

    private SearchUtils searchUtils;

    public static GraphSearch withCoreContainer(CoreContainer coreContainer){
        return new GraphSearch(coreContainer);
    }

    private GraphSearch(CoreContainer coreContainer){
        this.searchUtils = SearchUtils.usingCoreCoreContainer(coreContainer);
    }

    public JSONArray searchVerticesForAutoCompletionByLabelAndUser(String label, User user){
        JSONArray vertices = new JSONArray();
        try{
            SolrServer solrServer = searchUtils.getServer();
            SolrQuery solrQuery = new SolrQuery();
            String sentenceMinusLastWord = sentenceMinusLastWord(label);
            String lastWord = lastWordOfSentence(label);
            solrQuery.setQuery(
                    "label:"+sentenceMinusLastWord +"* AND " +
                            "owner_username:" + user.username()
            );
            solrQuery.addFilterQuery("label:"+sentenceMinusLastWord+"*"+lastWord+"*");
            QueryResponse queryResponse = solrServer.query(solrQuery);
            for(SolrDocument document : queryResponse.getResults()){
                vertices.put(SearchJsonConverter.documentToJson(
                        document
                ));

            }
        }catch (SolrServerException e){
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
