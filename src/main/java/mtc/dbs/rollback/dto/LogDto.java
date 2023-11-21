package mtc.dbs.rollback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LogDto {
    private String gid;
    private int sno;
    private String svcid;
    private String inputJson;
    private String outputJson;
}
