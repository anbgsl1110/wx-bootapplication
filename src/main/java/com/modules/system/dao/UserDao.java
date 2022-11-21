package com.modules.system.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.modules.system.entity.User;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.common.ExampleMapper;

/**
* @author chenTom
*
*@date 2020-07-23 16:18:12
*/
public interface UserDao extends BaseMapper<User>, ExampleMapper<User> {
    /**
    * 下一页
    * @author chenTom
    * @date 2020-07-23 16:18:12
    * @param id
    * @return com.modules.system.entity.User
    */
    User next(Long id);
    /**
    * 上一页
    * @author chenTom
    * @date 2020-07-23 16:18:12
    * @param id
    * @return com.modules.system.entity.User
    */
    User prev(Long id);
    /**
    * 获取最后一个编号
    * @author caizx
    * @date 2020/2/28 20:45
    * @param
    * @return java.lang.String
    */
    @Select("SELECT (number+0) numberStr FROM user WHERE is_deleted=0 ORDER BY numberStr DESC LIMIT 1")
    @ResultType(Integer.class)
    Integer getLastNumber();

    /**
     * 查询该openid是否存在
     * @return
     */
    @Select("select * from user where wechat_id=#{wechatId}")
    @ResultType(Integer.class)
    User getCountByOpenId(String  openId);


    @Select("select * from user where wechat_id=#{wechatId}")
    @ResultType(User.class)
    User getByOpenId(User userInfo);

}
