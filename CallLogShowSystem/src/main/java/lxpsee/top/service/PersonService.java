package lxpsee.top.service;

import lxpsee.top.domain.Person;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/26 11:46.
 */
public interface PersonService extends BaseService<Person> {
    public String findNameByPhone(String phone);
}
