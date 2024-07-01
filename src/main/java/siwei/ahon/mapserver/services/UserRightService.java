package siwei.ahon.mapserver.services;

import siwei.ahon.mapserver.expection.PageData;
import siwei.ahon.mapserver.model.User;
import siwei.ahon.mapserver.model.UserRight;
import siwei.ahon.mapserver.pojo.PageFilterPojo;

public interface UserRightService {
    PageData<UserRight> getUserPermissionsList(UserRight userRight, PageFilterPojo pj);

    Integer addUserPermissions(UserRight userRight);

    UserRight getUserPermissions(User user);

    Integer updateUserPermissions(UserRight userRight);
}
