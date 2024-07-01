package siwei.ahon.mapserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class User {
    @TableId(type = IdType.AUTO)
    @FilterFiled(type = FilterTypeEnum.EQ)
    Integer id;
    @NotBlank
    String username;
    @NotBlank
    String password;
    String token;
    Date gmtCreate;
    Date gmtModified;
    Date gmtLogin;
    String avatarUrl;
}
