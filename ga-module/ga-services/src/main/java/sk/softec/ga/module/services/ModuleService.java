package sk.softec.ga.module.services;

import sk.softec.ga.module.connector.model.CidData;
import sk.softec.ga.module.connector.model.ClientEvent;

import java.util.List;

/**
 * Created by jankovj on 15. 8. 2016.
 */
public interface ModuleService {

    String generateCID(String input);

    CidData generateCIDData(String input);

    void checkNewClientEvents();

}
