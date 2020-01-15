package com.nowcoder.community.MapperTest;

import com.nowcoder.community.CommunityApplication;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
//获取和项目相同的配置
@ContextConfiguration(classes = CommunityApplication.class)

public class MapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testSelectUser() {
        User user = userMapper.selectByName("test");
        System.out.println(user);
    }


    @Test
    public void testSelectPost() {
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0, 0, 10);
        for (DiscussPost post : list
        ) {
            System.out.println(post);

        }
        int i = discussPostMapper.selectDiscussPostRows(0);
        System.out.println(i);
    }

    @Test
    public void testLoginTicketInsert() {
        LoginTicket loginTicket = new LoginTicket();
//        loginTicket.setId(1);
        loginTicket.setUserId(101);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));
        loginTicket.setStatus(0);
        loginTicket.setTicket("nihao");
        int i = loginTicketMapper.insertLoginTicket(loginTicket);
        System.out.println(i);
    }
    @Test
    public void testLoginTicketSlect() {
        LoginTicket loginTicket = new LoginTicket();

//        loginTicket.setId(1);
        String nihao = "nihao";
        LoginTicket ticket = loginTicketMapper.selectByTicket(nihao);
        System.out.println(ticket);
    }
    @Test
    public void testLoginTicketUpdate() {
        LoginTicket loginTicket = new LoginTicket();
        int nihao = loginTicketMapper.updateStatus("nihao", 1);
        System.out.println(nihao);

    }
}
