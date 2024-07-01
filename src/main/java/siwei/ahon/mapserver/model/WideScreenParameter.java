package siwei.ahon.mapserver.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WideScreenParameter {
    @TableId(type = IdType.AUTO)
    Integer id;
    @NotBlank
    String prjTag;
    @NotBlank
    String screenWidth;
    @NotBlank
    String type;
}
