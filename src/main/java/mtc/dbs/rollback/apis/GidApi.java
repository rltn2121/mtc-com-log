package mtc.dbs.rollback.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface GidApi {
    // 내부 통신용 API
    @GetMapping("/mkgid")
    ResponseEntity<?> makeGid(@RequestParam(value = "svcid") String svcid) throws Exception;

    /*
    1. GET  /mkgid/{svcid}
    2. GET  /mkgid?svcid=xxx -> 이걸로
    3. POST /mkgid , body로 받아오기
    */

    // 1. 서비스 ID : 충전 (exg) / 결제 (pay)
    // 2. 시간 -> 안받아오고 여기서 찍기

}
