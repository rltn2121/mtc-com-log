package mtc.dbs.rollback.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mtc.dbs.rollback.dto.LogDto;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class MessageDeserializer implements Deserializer<LogDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public LogDto deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(new String(bytes), LogDto.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getOriginalMessage());
            throw new SerializationException(e);
        }
    }
}

