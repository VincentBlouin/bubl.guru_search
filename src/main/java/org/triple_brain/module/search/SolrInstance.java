package org.triple_brain.module.search;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/*
* Copyright Mozilla Public License 1.1
*/
public class SolrInstance {

    private CoreContainer coreContainer;
    private SolrServer solrServer;

    public static SolrInstance withSolrHomeAndRelativeSolrXmlPath(String solrHomePath, String solrXMLPath){
        return new SolrInstance(solrHomePath, solrXMLPath);
    }

    private SolrInstance(String solrHomePath, String solrXMLPath){
        try{
            instantiateServer(solrHomePath, solrXMLPath);
        }catch(ParserConfigurationException | IOException | SAXException e){
            throw new RuntimeException(e);
        }

    }

    private void instantiateServer(String solrHomePath, String solrXMLPath)throws ParserConfigurationException, IOException, SAXException{
        File solrConfigXml = new File(solrHomePath + solrXMLPath);
        coreContainer = new CoreContainer(solrHomePath, solrConfigXml);
        solrServer = new EmbeddedSolrServer(coreContainer, "triple_brain");
    }

    public CoreContainer coreContainer(){
        return coreContainer;
    }

    public SolrServer solrServer(){
        return solrServer;
    }

}
