package siwei.ahon.mapserver.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    Integer id;
    String username;
    String token;
    String avatarUrl;
    String groups;
    String webModuleConfig;
}
