package mtc.dbs.rollback.apis;

import lombok.RequiredArgsConstructor;
import mtc.dbs.rollback.dto.LogDto;
import mtc.dbs.rollback.queue.LogKafka;
import mtc.dbs.rollback.repository.LogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;

@Controller
@RequestMapping("/log")
@RequiredArgsConstructor
public class GidController implements GidApi {

    @Override
    public ResponseEntity<?> makeGid(String svcid) throws Exception {
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());


        String timeStamp = date.format(new Date());
        String serial = String.format("%04d", random.nextInt(10000));
        String gid = timeStamp + svcid + serial;

        // 로그 쌓기 테스트용 임시로직
        LogRepository repository = new LogRepository();
        LogDto dto = new LogDto(gid, 0, "tmp", "injson", "outjson");
        dto.setSno(repository.getMaxSno(gid)+1);
        repository.insertLogMas(dto);
        return ResponseEntity.ok(gid);
    }
}
