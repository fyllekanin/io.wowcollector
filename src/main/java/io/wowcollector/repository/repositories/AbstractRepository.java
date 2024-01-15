package io.wowcollector.repository.repositories;

import org.bson.Document;

import com.google.gson.Gson;

import io.wowcollector.entityview.repository.AbstractDocument;

public abstract class AbstractRepository<T extends AbstractDocument> implements Repository {
    protected static final Gson GSON = new Gson();

    protected Document getDocument(T document) {
        Document document1 = Document.parse(GSON.toJson(document));
        document1.remove("_id");
        return document1;
    }
}
