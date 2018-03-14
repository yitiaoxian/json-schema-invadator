package xiao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.examples.Utils;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.IOException;

/**
 * author xiao
 * 2018/3/8
 * note:用于验证从mq中获取的json数据
 */
public class MqJsonValidator {

    /***
     *  schema file path , now it is just one schema.
     */

    private static String SCHEMA_PATH = "E:/IdeaProjects/testP.json";
    private JsonNode jsonNode;
    private static ProcessingReport report = null;
    private JsonNode jsonSchema;
    public MqJsonValidator(){

    }
    //form json string to get jsonNode
    public static JsonNode getJsonNodeFrom(String json) throws IOException {
        JsonNode jsonNode = JsonLoader.fromString(json);
        return jsonNode;
    }
    /**
     * from json file to get jsonNode
     */

    public static JsonNode getJsonSchemaFrom(String path) throws IOException {
        JsonNode jsonSchema = JsonLoader.fromPath(path);
        return jsonSchema;
    }
    public static void main(String[] args) throws IOException {
        String jsonStr = "{\"name\":\"张三\",\"age\":20}";

        JsonNode jsonNode = getJsonNodeFrom(jsonStr);

        JsonNode jsonSchema = getJsonSchemaFrom(SCHEMA_PATH);

        report = validate(jsonNode,jsonSchema);

        System.out.println(report);
    }
    /**
     * the function of checking jsonNode
     */

    public static ProcessingReport validate(JsonNode jsonNode,JsonNode jsonSchema){
        report = JsonSchemaFactory.byDefault().getValidator().
                validateUnchecked(jsonSchema,jsonNode);
        return report;
    }
    /**
     * object of the checking is JSONObject
     */
    public static ProcessingReport jsonValidate(JSONPObject jsonpObject) throws IOException {
        if(report == null){

            JsonNode jsonSchema = JsonLoader.fromPath(SCHEMA_PATH);
            return validate(JsonLoader.fromString(String.valueOf(jsonpObject)), jsonSchema);
        }
        return report;
    }
}
