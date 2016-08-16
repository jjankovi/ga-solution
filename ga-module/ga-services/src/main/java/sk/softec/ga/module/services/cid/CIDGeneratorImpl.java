package sk.softec.ga.module.services.cid;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by jankovj on 15. 8. 2016.
 */
@Service
public class CIDGeneratorImpl implements CIDGenerator {
    @Override
    public String generateCID(String inputValue) {
        if (inputValue != null) {
            return UUID.nameUUIDFromBytes(inputValue.getBytes()).toString();
        }
        return null;
    }
}
