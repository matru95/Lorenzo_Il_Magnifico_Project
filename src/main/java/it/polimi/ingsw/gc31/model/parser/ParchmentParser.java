package it.polimi.ingsw.gc31.model.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import it.polimi.ingsw.gc31.enumerations.ResourceName;
import it.polimi.ingsw.gc31.model.Parchment;
import it.polimi.ingsw.gc31.model.resources.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParchmentParser {
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode rootNode;
    private List<Parchment> parchments;

    public ParchmentParser(String path) throws IOException {
        File inputFile = new File(path);
        rootNode = mapper.readTree(inputFile).path("parchments");
    }

    public void parse() {
        for(JsonNode parchmentNode: rootNode) {
            String parchmentID = parchmentNode.path("id").toString();
            JsonNode resourcesNode = parchmentNode.path("resources");
            List<Resource> parchmentResources = parseResources(resourcesNode);

            Parchment parchment = new Parchment(parchmentID, parchmentResources);
            parchments.add(parchment);
        }
    }

    private List<Resource> parseResources(JsonNode resourcesNode) {
        List<Resource> result = new ArrayList<>();

        for(JsonNode resourceNode: resourcesNode) {
            String resourceNameString = resourceNode.fieldNames().next();
            int amount = resourceNode.path(resourceNameString).asInt();

            Resource resource = new Resource(ResourceName.valueOf(resourceNameString.toUpperCase()), amount);
            result.add(resource);
        }

        return result;
    }

    public List<Parchment> getParchments() {
        return parchments;
    }
}
