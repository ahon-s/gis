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
public class ModulePermission {
     @TableId(type = IdType.AUTO)
     Integer id;
     @FilterFiled(type = FilterTypeEnum.EQ)
     @NotBlank(message = "typeCode不能为空")
     String typeCode;
     String code;
     @NotBlank
     String name;
     String jumpUrl;
}
