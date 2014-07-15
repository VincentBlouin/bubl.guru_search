package org.triple_brain.module.search;

import org.triple_brain.module.model.User;

import java.net.URI;
import java.util.List;

/*
* Copyright Mozilla Public License 1.1
*/
public interface GraphSearch {
    public List<VertexSearchResult> searchOwnVerticesAndPublicOnesForAutoCompletionByLabel(
            String label,
            User user
    );

    public List<VertexSearchResult> searchOnlyForOwnVerticesForAutoCompletionByLabel(
            String label, User user
    );
    public List<EdgeSearchResult> searchRelationsForAutoCompletionByLabel(
            String label,
            User user
    );
    public GraphElementSearchResult getByUri(URI uri, User user);
}
