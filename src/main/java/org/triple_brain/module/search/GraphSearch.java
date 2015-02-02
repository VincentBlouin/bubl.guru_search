/*
 * Copyright Vincent Blouin under the Mozilla Public License 1.1
 */

package org.triple_brain.module.search;

import org.triple_brain.module.model.User;

import java.net.URI;
import java.util.List;

public interface GraphSearch {
    public List<VertexSearchResult> searchSchemasOwnVerticesAndPublicOnesForAutoCompletionByLabel(
            String searchTerm,
            User user
    );

    public List<VertexSearchResult> searchOnlyForOwnVerticesOrSchemasForAutoCompletionByLabel(
            String label, User user
    );

    public List<VertexSearchResult> searchOnlyForOwnVerticesForAutoCompletionByLabel(
            String searchTerm, User user
    );
    public List<GraphElementSearchResult> searchRelationsPropertiesOrSchemasForAutoCompletionByLabel(
            String searchTerm,
            User user
    );
    public GraphElementSearchResult getByUri(URI uri, User user);
}
