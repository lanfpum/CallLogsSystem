package lxpsee.top.dao.impl;

import lxpsee.top.dao.BaseDao;
import lxpsee.top.domain.Person;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/26 10:29.
 */
@Repository("personDao")
public class PersonDao extends SqlSessionDaoSupport implements BaseDao<Person> {
    public void insert(Person person) {
        getSqlSession().insert("persons.insert", person);
    }

    public void update(Person person) {

    }

    public void delete(Integer id) {

    }

    public Person selectOne(Integer id) {
        return null;
    }

    public List<Person> selectAll() {
        return getSqlSession().selectList("persons.selectAll");
    }

    public List<Person> selectPage(int offset, int len) {
        return null;
    }

    public int selectCount() {
        return 0;
    }

    /**
     * 根据电话查询用户名
     */
    public String findNameByPhone(String phone) {
        return getSqlSession().selectOne("persons.selectNameByPhone", phone);
    }

}
