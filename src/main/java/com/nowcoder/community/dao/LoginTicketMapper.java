package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * 凭证查询，不使用.xml文件配置
 */
@Mapper
public interface LoginTicketMapper {
    /**
     * 插入一条凭证
     *
     * @param loginTicket 插入一条LoginTicket对象
     * @return 返回int类型，使用useGeneratedKeys返回自增id
     */
    @Insert({"insert into login_ticket(user_id,ticket,status,expired)",
            "value(#{userId},#{ticket},#{status},#{expired})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    /**
     * @param ticket 传入唯一凭证ticket
     * @return 返回LoginTicket对象
     */
    @Select({"select id,user_id,ticket,status,expired", "from login_ticket where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    /**
     * @param ticket 传入唯一ticket
     * @param status 传入要修改的status
     * @return 返回更新状态，更新成功>0
     */
    @Update({"<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket}",
            "<if test =\"ticket!=null\">",
            "and 1=1",
            "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);
}
