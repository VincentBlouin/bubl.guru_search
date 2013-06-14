package org.triple_brain.module.search;

import com.google.inject.AbstractModule;
import org.apache.solr.core.CoreContainer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/*
* Copyright Mozilla Public License 1.1
*/
public class SearchModule extends AbstractModule {

    private Boolean isTesting;
    private String solrHomePath;
    private String solrXmlPath;

    public SearchModule(
            Boolean isTesting
    ) {
        this(
                isTesting,
                isTesting ?
                        "target/maven-shared-archive-resources/solr/" :
                        "/var/lib/triple_brain/solr/",
                isTesting ?
                        "conf/solr.xml" :
                        "conf/solr.xml"
        );
    }

    public SearchModule(
            Boolean isTesting,
            String solrHomePath,
            String solrXmlPath
    ) {
        this.isTesting = isTesting;
        this.solrHomePath = solrHomePath;
        this.solrXmlPath = solrXmlPath;
    }

    @Override
    protected void configure() {
        try {
            File solrConfigXml = new File(solrHomePath + solrXmlPath);
            CoreContainer coreContainer = new CoreContainer(solrHomePath, solrConfigXml);
            bind(GraphIndexer.class).toInstance(GraphIndexer.withCoreContainer(coreContainer));
            bind(GraphSearch.class).toInstance(GraphSearch.withCoreContainer(coreContainer));
            if (isTesting) {
                bind(SearchUtils.class).toInstance(
                        SearchUtils.usingCoreCoreContainer(coreContainer)
                );
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}

