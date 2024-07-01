package siwei.ahon.mapserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRight {
    @TableId(type = IdType.AUTO)
    Integer id;
    @NotNull
    Integer userId;
    @TableField("`groups`")
    @NotBlank
    String groups;
    @NotBlank
    String webModuleConfig;
}
