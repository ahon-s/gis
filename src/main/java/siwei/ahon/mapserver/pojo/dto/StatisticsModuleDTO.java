package siwei.ahon.mapserver.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsModuleDTO {
    Integer layoutId;
    Integer statisticsCode;
    Integer moduleId;
    String moduleName;
    String  componentName;
}
