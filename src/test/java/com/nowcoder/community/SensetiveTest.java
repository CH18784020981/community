package com.nowcoder.community;

import com.nowcoder.community.util.SensitiveFilter;
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

public class SensetiveTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void test() {
//你在****,你是****吗，我们去吃鸡？ bug
        String str = "你在吸毒,你是×傻逼&吗，我们去吃鸡……叭？";
        str = sensitiveFilter.filter(str);
        System.out.println(str);
    }

}
