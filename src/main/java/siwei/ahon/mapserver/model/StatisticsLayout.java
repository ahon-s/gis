package siwei.ahon.mapserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import siwei.ahon.mapserver.annotation.FilterFiled;
import siwei.ahon.mapserver.annotation.FilterTypeEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsLayout {
    @TableId(type = IdType.AUTO)
    Integer id;
    @NotNull
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer statisticsCode;
    @NotNull
    Integer moduleId;
    @NotBlank
    @FilterFiled(type = FilterTypeEnum.EQ)
    String prjTag;
}
