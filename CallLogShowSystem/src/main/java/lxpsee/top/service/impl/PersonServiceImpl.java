package lxpsee.top.service.impl;

import lxpsee.top.dao.BaseDao;
import lxpsee.top.dao.impl.PersonDao;
import lxpsee.top.domain.Person;
import lxpsee.top.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/26 11:47.
 */
@Service("personService")
public class PersonServiceImpl extends BaseServiceImpl<Person> implements PersonService {

    @Resource(name = "personDao")
    public void setDao(BaseDao<Person> dao) {
        super.setDao(dao);
    }
  /*  @Resource(name ="personDao")
    private PersonDao personDao;*/


    public String findNameByPhone(String phone) {
        return null;
    }
}
