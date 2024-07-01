package siwei.ahon.mapserver.services;

import siwei.ahon.mapserver.pojo.dto.UserDTO;
import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.model.User;
import siwei.ahon.mapserver.pojo.PageFilterPojo;


public interface UserService {
    PageData<User> getUserList(User user, PageFilterPojo pj);

    Integer addUser(User user);

    Integer updateUser(User user);

    Integer deleteUser(User user);

    UserDTO userLogin(User user);

    User findUserByToken(String token);

    String userLoginOut(User user);

    String getUserToken(User user);

    String updateUserToken(User user);

    User verifyToken(String token);


}
