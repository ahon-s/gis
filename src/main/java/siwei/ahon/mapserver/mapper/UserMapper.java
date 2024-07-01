package siwei.ahon.mapserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import siwei.ahon.mapserver.model.User;
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
