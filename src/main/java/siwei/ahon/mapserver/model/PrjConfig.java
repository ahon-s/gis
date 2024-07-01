package siwei.ahon.mapserver.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class PrjConfig {
    @TableId(type = IdType.AUTO)
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer id;
    @NotBlank
    @FilterFiled(type = FilterTypeEnum.EQ)
    String prjCode;
    @NotBlank
    String prjName;
    String projectTag;
    @TableField(fill = FieldFill.UPDATE)
    Integer projectNum;
    String projectName;
    String accessAddress;
    String personnelAccessAddress;


    String mapCoordinate;

    Integer mapLevel;

//    String viewCoordinate;
}
