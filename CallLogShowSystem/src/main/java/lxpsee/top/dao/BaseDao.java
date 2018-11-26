package lxpsee.top.dao;

import java.util.List;

/**
 * The world always makes way for the dreamer
 * Created by 努力常态化 on 2018/11/21 18:36.
 * <p>
 * 基本的dao接口
 */
public interface BaseDao<T> {
    public void insert(T t);

    public void update(T t);

    public void delete(Integer id);

    public T selectOne(Integer id);

    public List<T> selectAll();

    public List<T> selectPage(int offset, int len);

    public int selectCount();
}
