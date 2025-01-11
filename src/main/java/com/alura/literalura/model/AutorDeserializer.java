package com.alura.literalura.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class AutorDeserializer extends JsonDeserializer<DadosAutor> {

    @Override
    public DadosAutor deserialize(JsonParser p, DeserializationContext contexto)
            throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        if (node.isArray() && node.size() > 0) {
            return p.getCodec().treeToValue(node.get(0), DadosAutor.class);
        }
        return null;
    }
}
