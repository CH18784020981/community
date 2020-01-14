package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//获取和项目相同的配置
@ContextConfiguration(classes = CommunityApplication.class)

public class MailTest {
    @Autowired
    private MailClient mailClient;
    @Test
    public void mailTest(){
        mailClient.sendMail("2454512759@qq.com","TEST","welcome");
    }
}
