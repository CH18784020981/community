package com.nowcoder.community.controller;

import com.fasterxml.jackson.core.SerializableString;
import com.google.code.kaptcha.Producer;
import com.nowcoder.community.config.KaptchaConfig;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Controller
public class LoginController implements CommunityConstant {
    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @Value("@{server.servlet.context-path}")
    private String contextPath;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * @return返回注册页面
     */
    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "/site/register";
    }

    /**
     * @return返回登录页面
     */
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "/site/login";
    }

    /**
     * 注册的返回
     *
     * @param model model对象
     * @param user  user对象
     * @return 返回相关的HTML页面
     */
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model, User user) {
        Map<String, Object> map = userService.register(user);
        System.out.println(map.isEmpty());
        if (map == null || map.isEmpty()) {
            model.addAttribute("msg", "注册成功,我们已经向您的邮箱发送了一封激活邮件,请尽快激活!");
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }
    }

    /**
     * @param model  Model类为view层传数据
     * @param userId 传入userId
     * @param code   激活码从点击激活链接处传来
     * @return 成功跳到登陆页面，失败跳入首页
     */
    @RequestMapping(path = "/activation/{userId}/{code}", method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code) {
        int result = userService.activation(userId, code);
        if (result == ACTIVATION_SUCCESS) {
            model.addAttribute("msg", "激活成功，您的账号已经可以正常使用了!");
            //跳到登陆页面
            model.addAttribute("target", "/login");
        } else if (result == ACTIVATION_REPEAT) {
            model.addAttribute("msg", "无效操作，该账号已经激活过了!");
            //跳到首页
            model.addAttribute("target", "/index");
        } else {
            model.addAttribute("msg", "激活失败，您的激活码有误!");
            //跳到首页
            model.addAttribute("target", "/index");
        }
        return "/site/operate-result";
    }

    /**
     * @param response 图片类型需要手动向浏览器响应
     * @param session  需要将验证码存在session中，以便登陆验证
     */
    @RequestMapping(path = "/kaptcha", method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response, HttpSession session) {
        //生成验证码
        String text = kaptchaProducer.createText();
        //将验证码放入图片
        BufferedImage image = kaptchaProducer.createImage(text);

        //将验证码放入session
        session.setAttribute("kaptcha", text);

        //将图片发给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            logger.error("响应验证码失败:{}", e.getMessage());
        }
    }

    /**
     * @param username   账号
     * @param password   密码
     * @param code       前端输入的验证码
     * @param model      Model返回视图层
     * @param session    为了获取session中的验证码
     * @param response   通过response向浏览器响应cookie
     * @param rememberme 页面上是否选择记住我
     * @return 验证码错误，重定向到login页面，登陆成功，重定向到index页面，失败返回login页面
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(String username, String password, String code, boolean rememberme,
                        Model model, HttpSession session, HttpServletResponse response) {
        // 检查验证码
        String kaptcha = (String) session.getAttribute("kaptcha");
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)) {
            model.addAttribute("codeMsg", "验证码不正确!");
            return "/site/login";
        }

        // 检查账号,密码
        int expiredSeconds = rememberme ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userService.login(username, password, expiredSeconds);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return "redirect:/index";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            return "/site/login";
        }
    }


    /**
     * 退出登陆
     * @param ticket 传入唯一凭证，清除它
     * @return 返回到login页面
     */
    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(@CookieValue("ticket")String ticket) {
        userService.logout(ticket);
        return "redirect:/login";
    }

}
