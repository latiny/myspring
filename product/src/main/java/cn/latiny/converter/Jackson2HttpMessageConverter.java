package cn.latiny.converter;

import cn.latiny.error.ErrorCode;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.TypeUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public class Jackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
    public static final String EXCLUDE = "springfox.documentation";

    public Jackson2HttpMessageConverter() {
        this(Jackson2ObjectMapperBuilder.json().featuresToEnable(new Object[]{DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL}).build());
    }

    public Jackson2HttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, new MediaType[]{MediaType.APPLICATION_JSON, new MediaType("application", "*+json")});
    }

    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (!(object instanceof Result) && !object.getClass().getName().contains("springfox.documentation")) {
            if (object instanceof Collection) {
                Collection<?> col = (Collection)object;
                if (col.iterator().hasNext() && col.iterator().next().getClass().getName().contains("springfox.documentation")) {
                    this.writeInternal0(object, type, outputMessage);
                    return;
                }
            }

            Object result;
            if (object instanceof ErrorCode) {
                ErrorCode errorCode = (ErrorCode)object;
                result = Result.newBuilder().fail(errorCode.getCode(), errorCode.getMessage()).build();
            } else {
                result = Result.newBuilder().success().data(object).build();
            }
            this.writeInternal0(result, type, outputMessage);

        } else {
            this.writeInternal0(object, type, outputMessage);
        }
    }

    private void writeInternal0(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        MediaType contentType = outputMessage.getHeaders().getContentType();
        JsonEncoding encoding = this.getJsonEncoding(contentType);
        JsonGenerator generator = this.objectMapper.getFactory().createGenerator(outputMessage.getBody(), encoding);

        try {
            this.writePrefix(generator, object);
            Class<?> serializationView = null;
            FilterProvider filters = null;
            Object value = object;
            JavaType javaType = null;
            if (object instanceof MappingJacksonValue) {
                MappingJacksonValue container = (MappingJacksonValue)object;
                value = container.getValue();
                serializationView = container.getSerializationView();
                filters = container.getFilters();
            }

            if (type != null && value != null && TypeUtils.isAssignable(type, value.getClass())) {
                javaType = this.getJavaType(type, (Class)null);
            }

            ObjectWriter objectWriter;
            if (serializationView != null) {
                objectWriter = this.objectMapper.writerWithView(serializationView);
            } else if (filters != null) {
                objectWriter = this.objectMapper.writer(filters);
            } else {
                objectWriter = this.objectMapper.writer();
            }

            if (javaType != null && javaType.isContainerType()) {
                objectWriter = objectWriter.forType(javaType);
            }

            objectWriter.writeValue(generator, value);
            this.writeSuffix(generator, object);
            generator.flush();
        } catch (JsonProcessingException var12) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + var12.getOriginalMessage(), var12);
        }
    }
}