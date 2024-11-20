package cz.oksystem.aihackathon.writerassistant.repository;

import cz.oksystem.aihackathon.writerassistant.utils.PersonInfoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PersonInfoRepository {

    public PersonInfoTemplate getPersonInfoTemplate(Long id) {
        if (id == 2) {
            return PersonInfoTemplate.getPersonWithoutCurrentAddress();
        }
        return PersonInfoTemplate.getFilledPersonInfo();
    }

}
