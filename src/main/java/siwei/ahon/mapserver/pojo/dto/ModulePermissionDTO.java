package siwei.ahon.mapserver.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModulePermissionDTO {
    Integer id;
    String typeCode;
    String typeName;
    String code;
    String name;
    String jumpUrl;

}
