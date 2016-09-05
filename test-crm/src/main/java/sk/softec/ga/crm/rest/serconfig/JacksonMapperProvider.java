package sk.softec.ga.crm.rest.serconfig;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Created by jankovj on 31. 8. 2016.
 */
@Provider
public class JacksonMapperProvider implements ContextResolver<ObjectMapper> {

    private static ObjectMapper apiMapper = createMapperForApi();

    private static ObjectMapper errorMapper = createMapperForError();

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return apiMapper;
    }

    public static ObjectMapper createMapperForApi() {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("JacksonConfigurer called ... ");
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);

        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, false);

        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);

        mapper.setConfig(mapper.getSerializationConfig().with(new JacksonAnnotationIntrospector()));

        return mapper;
    }

    public static ObjectMapper createMapperForError() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);

        mapper.setConfig(mapper.getSerializationConfig().with(new JacksonAnnotationIntrospector()));

        return mapper;
    }

}
