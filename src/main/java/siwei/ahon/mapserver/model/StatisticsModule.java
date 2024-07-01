package siwei.ahon.mapserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import siwei.ahon.mapserver.annotation.FilterFiled;
import siwei.ahon.mapserver.annotation.FilterTypeEnum;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsModule {
    @TableId(type = IdType.AUTO)
    Integer id;
    @NotBlank
    String name;
    @NotBlank
    String componentName;
    @NotBlank
    @FilterFiled(type = FilterTypeEnum.EQ)
    String prjTag;
}
