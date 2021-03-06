package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 话题查询
 */
@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    //@Param注解用于给参数取别名
    //如果只有一个参数，并且在if里面使用，则必须取别名

    /*
     *
     * @param userId
     * @return 返回userId下的discuss条数
     */
    int selectDiscussPostRows(@Param("userId") int userId);

    /**
     *
     * @param discussPost DiscussPost对象
     * @return 返回当前条数
     */
    int insertDiscussPost(DiscussPost discussPost);
}
