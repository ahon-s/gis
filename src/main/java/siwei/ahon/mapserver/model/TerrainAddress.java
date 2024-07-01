package siwei.ahon.mapserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import siwei.ahon.mapserver.annotation.FilterFiled;
import siwei.ahon.mapserver.annotation.FilterTypeEnum;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerrainAddress {
    @TableId(type = IdType.AUTO)
    Integer id;
    @FilterFiled(type = FilterTypeEnum.EQ)
    @NotBlank
    String prjCode;
    @TableField("`key`")
    @FilterFiled(type = FilterTypeEnum.EQ)
    @NotBlank
    String key;
    @NotBlank
    @FilterFiled(type = FilterTypeEnum.EQ)
    String mDate;
    @NotBlank
    String mapUrl;
}
