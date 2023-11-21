package mtc.dbs.rollback.queue;

import lombok.RequiredArgsConstructor;

import mtc.dbs.rollback.dto.LogDto;
import mtc.dbs.rollback.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class LogKafka {
    private final static Logger log = LoggerFactory.getLogger(LogKafka.class);
    private final LogRepository repository = new LogRepository();

    // 큐 인풋 :  gid / 서비스아이디 / 인풋스트링 / 아웃풋스트링
    // 원장 레이아웃 : ( gid / 일련번호 01 02 / ) / 서비스명 / 인풋 json string / 아웃풋 json string.
    @KafkaListener(topics = "mtc.com.log", groupId = "amugurnahasam")
    public void consumeMessage(@Payload LogDto data,
                               @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                               @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp,
                               @Header(KafkaHeaders.OFFSET) long offset) throws SQLException {
        log.info("listener --- [{}][{}][{}][{}][{}]", topic, timestamp, offset, key, data.toString());

        // gid 원장에 넣어주기
        data.setSno(repository.getMaxSno(data.getGid())+1);
        repository.insertLogMas(data);

    }
}