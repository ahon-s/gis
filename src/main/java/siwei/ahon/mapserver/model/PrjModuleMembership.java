package siwei.ahon.mapserver.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrjModuleMembership {
    @TableId(type = IdType.AUTO)
    Integer id;
    String userId;
    @NotBlank
    Integer ifShow;
    @NotBlank
    Integer ifChecked;
    @NotBlank
    Integer moduleId;
    @NotBlank
    Integer prjId;

//    public PrjModuleMembership(Integer id, Integer ifShow, Integer ifChecked) {
//        this.id = id;
//        this.ifShow = ifShow;
//        this.ifChecked = ifChecked;
//    }
}
