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
    //schema文件的配置路径，现在是单一的schema形式
    private static String SCHEMA_PATH = "E:/IdeaProjects/testP.json";
    private JsonNode jsonNode;
    private static ProcessingReport report = null;
    private JsonNode jsonSchema;
    public MqJsonValidator(){

    }
    //从json字符串中获取jsonNode对象
    public static JsonNode getJsonNodeFrom(String json) throws IOException {
        JsonNode jsonNode = JsonLoader.fromString(json);
        return jsonNode;
    }
    //从json文件中获取jsonNode对象
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
    //验证jsonNode的方法
    public static ProcessingReport validate(JsonNode jsonNode,JsonNode jsonSchema){
        report = JsonSchemaFactory.byDefault().getValidator().
                validateUnchecked(jsonSchema,jsonNode);
        return report;
    }
    //传入的对象是JSONObject验证方法
    public static ProcessingReport jsonValidate(JSONPObject jsonpObject) throws IOException {
        if(report == null){

            JsonNode jsonSchema = JsonLoader.fromPath(SCHEMA_PATH);
            return validate(JsonLoader.fromString(String.valueOf(jsonpObject)), jsonSchema);
        }
        return report;
    }
}
