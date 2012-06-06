package org.triple_brain.module.search;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.triple_brain.module.model.graph.GraphElement;
import org.triple_brain.module.model.graph.Vertex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.triple_brain.module.common_utils.CommonUtils.*;

/*
* Copyright Mozilla Public License 1.1
*/
public class GraphIndexer {

    private SolrInstance solrInstance;

    public static GraphIndexer withSolrInstance(SolrInstance solrInstance){
        return new GraphIndexer(solrInstance);
    }

    private GraphIndexer(SolrInstance solrInstance){
        this.solrInstance = solrInstance;
    }

    public void indexVertex(Vertex vertex){
        try{
        SolrInputDocument document = graphElementToDocument(vertex);
        document.addField("is_vertex", true);
        solrServer().add(document);
        solrServer().commit();
        }catch(IOException | SolrServerException e){
            throw new RuntimeException(e);
        }
    }

    private SolrInputDocument graphElementToDocument(GraphElement graphElement)throws UnsupportedEncodingException{
        SolrInputDocument document = new SolrInputDocument();
        document.addField("uri", encodeURL(graphElement.id()));
        document.addField("label", graphElement.label());
        return document;
    }

    private SolrServer solrServer(){
        return solrInstance.solrServer();
    }

    public SolrInstance solrInstance(){
        return solrInstance;
    }
}
