package com.wompi.api.utils.objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wompi.api.utils.GeneralUtil;

public class MergerUtil {

    private static final ObjectMapper mapper = new ObjectMapper()
            //.setSerializationInclusion(JsonInclude.Include.NON_NULL)
            ;

    private MergerUtil(){
        GeneralUtil.notAllowInstantiation();
    }

    public static void merge(Object source, Object target) throws JsonProcessingException {

        JsonNode sourceNode = MergerUtil.mapper.valueToTree(source);
        JsonNode targetNode = MergerUtil.mapper.valueToTree(target);

        mergeNodes(sourceNode, targetNode);

        //MergerUtil.mapper.readerForUpdating(target).readValue(targetNode.toString());
        MergerUtil.mapper.updateValue(target, targetNode);
        //MergerUtil.mapper.updateValue(target, sourceNode);
    }

    private static void mergeNodes(JsonNode sourceNode, JsonNode targetNode) {
        if (sourceNode.isObject() && targetNode.isObject()) {
            ObjectNode targetObjectNode = (ObjectNode) targetNode;
            ObjectNode sourceObjectNode = (ObjectNode) sourceNode;

            sourceObjectNode.fieldNames().forEachRemaining(fieldName -> {
                JsonNode sourceFieldValue = sourceObjectNode.get(fieldName);
                JsonNode targetFieldValue = targetObjectNode.get(fieldName);

                if (sourceFieldValue.isObject()) {
                    if (targetFieldValue == null || targetFieldValue.isNull()) {
                        targetObjectNode.set(fieldName, sourceFieldValue);
                    } else {
                        mergeNodes(sourceFieldValue, targetFieldValue);
                    }
                } else if (!sourceFieldValue.isNull()) {
                    targetObjectNode.set(fieldName, sourceFieldValue);
                }
            });
        }
    }

//    private static void mergeNodes(JsonNode sourceNode, JsonNode targetNode) {
//        if (sourceNode.isObject() && targetNode.isObject()) {
//            ObjectNode targetObjectNode = (ObjectNode) targetNode;
//            ObjectNode sourceObjectNode = (ObjectNode) sourceNode;
//
//            sourceObjectNode.fieldNames().forEachRemaining(fieldName -> {
//                JsonNode sourceFieldValue = sourceObjectNode.get(fieldName);
//                JsonNode targetFieldValue = targetObjectNode.get(fieldName);
//
//                if (sourceFieldValue.isObject()) {
//                    if (targetFieldValue == null || targetFieldValue.isNull()) {
//                        targetObjectNode.set(fieldName, sourceFieldValue);
//                    } else {
//                        mergeNodes(sourceFieldValue, targetFieldValue);
//                    }
//                } else if (sourceFieldValue.isArray()) {
//                    if (targetFieldValue == null || !targetFieldValue.isArray()) {
//                        targetObjectNode.set(fieldName, sourceFieldValue);
//                    } else {
//                        // Fusionar arrays
//                        ArrayNode targetArrayNode = (ArrayNode) targetFieldValue;
//                        ArrayNode sourceArrayNode = (ArrayNode) sourceFieldValue;
//
//                        for (JsonNode sourceArrayElement : sourceArrayNode) {
//                            targetArrayNode.add(sourceArrayElement);
//                        }
//                    }
//                } else if (!sourceFieldValue.isNull()) {
//                    targetObjectNode.set(fieldName, sourceFieldValue);
//                }
//            });
//        }
//    }


}

