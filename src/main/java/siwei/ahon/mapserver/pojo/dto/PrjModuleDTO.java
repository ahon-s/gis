package siwei.ahon.mapserver.pojo.dto;

import lombok.Data;

@Data
public class PrjModuleDTO {
    Integer membershipId;
    Integer moduleId;
    String typeName;
    String name;
    String code;
    Integer ifShow;
    Integer ifChecked;
}
