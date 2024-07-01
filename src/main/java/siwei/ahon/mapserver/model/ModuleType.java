package siwei.ahon.mapserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleType {
    @TableId(type = IdType.AUTO)
    Integer id;
    @NotBlank
    String typeCode;
    @NotBlank
    String typeName;
}
