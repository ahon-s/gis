package siwei.ahon.mapserver.services;

import siwei.ahon.mapserver.model.WideScreenParameter;

public interface WideScreenParameterService {


    WideScreenParameter getScreenWidth (String prjTag,String type);

    String setScreenWidth(WideScreenParameter wideScreenParameter);

    String deleteScreenWidth(WideScreenParameter wideScreenParameter);


}
