package com.zhong.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhong.model.Users;
import com.zhong.service.UsersService;
import com.zhong.utils.DateUtils;
import com.zhong.utils.Md5Utils;
import com.zhong.utils.UUIDUtils;

@Controller
public class UsersController {
	@Autowired
	private UsersService userService;

	@RequestMapping("showReg")
	public String showReg(String type, HttpServletRequest request) {
		if (type != null && type.equals("1")) {
			request.setAttribute("msg", "两次密码不一致");
		} else if (type != null && type.equals("2")) {
			request.setAttribute("msg", "验证码错误");
		} else if (type != null && type.equals("3")) {
			request.setAttribute("msg", "注册失败");
		}
		return "reg";
	}

	@RequestMapping("reg")
	public String reg(Users user, String repassword, String checkcode, HttpServletRequest request) {
		user.setRole("user");
		user.setUpdatetime(DateUtils.format(new Date()));

		// 密码确认
		String password = user.getPassword();
		if (!password.equals(repassword)) {

			return "redirect:showReg.action?type=1";
		}

		// 使用MD5加密
		user.setPassword(Md5Utils.md5(password));
		user.setState(0);

		// 生成一个激活码
		String uuid = UUIDUtils.getUUID();
		user.setActivecode(uuid);

		// 验证码是否正确
		// 从session中获取生成验证码
		String check = (String) request.getSession().getAttribute("checkcode_session");
		// 拿用户输入的验证码与生成的验证码比较
		if (!check.equals(checkcode)) {
			return "redirect:showReg.action?type=2";
		}

		// 调用Servic进行保存
		int num = userService.saveUsers(user);
		if (num == 0) {
			return "redirect:showReg.action?type=3";
		}

		return "redirect:showLogin.action?type=1";
	}

	@RequestMapping("showLogin")
	public String showLogin(String type, Model model) {
		System.out.println(type);
		if ("1".equals(type)) {
			model.addAttribute("msg", "注册成功，请登录");
		} else if ("2".equals(type)) {
			model.addAttribute("msg", "用户名或密码错误");
		} else if ("3".equals(type)) {
			model.addAttribute("msg", "登陆成功");
		} else if ("4".equals(type)) {
			model.addAttribute("msg", "请先登录后进行操作");
		}
	
		return "login";
	}

	// 激活帐户的方法
	@RequestMapping("activation")
	public String activation(String code) {
		// 根据激活码查询用户
		Users user = userService.findUserByCode(code);
		// 将帐户的状态改为1
		user.setState(1);
		userService.updateUsers(user);
		return "login";
	}

	// 异步校验用户名
	@RequestMapping("checkName")
	@ResponseBody
	public String checkName(String username) {
		System.out.println(username);
		// 根据用户名查询是否存在该用户名
		Users user = userService.findUserByName(username);
		// 当对象不为null，说明用户名存在
		if (user != null) {
			// 返回false表示用户名存在
			// {"msg":"false"}
			return "{\"msg\":\"false\"}";
		}

		return "{\"msg\":\"true\"}";
	}

	// 登陆
	@RequestMapping("login")
	public String login(Users user, String remember, String autologin, HttpServletResponse resp,
			HttpServletRequest request) {
		Users users = null;
		try {
			user.setPassword(Md5Utils.md5(user.getPassword()));

			users = userService.login(user);

			if (users == null) {
				return "redirect:showLogin?type=2";
			}

			// 记住用户
			// 判断用户是否勾选记住用户
			if (remember != null && remember.equals("on")) {
				// 将用户名以cookie的形式发送到客户端
				Cookie cookie;
				cookie = new Cookie("username", URLEncoder.encode(users.getUsername(), "utf-8"));
				// cookie默认是会话级别的,会随着浏览器的关闭，cookie消失
				// 通过setMaxAge设置cookie的存活时间，单位秒
				cookie.setMaxAge(60 * 60 * 24 * 7);// 存储7天

				resp.addCookie(cookie);

				Cookie cookie1 = new Cookie("save", "on");
				cookie1.setMaxAge(60 * 60 * 24 * 7);// 存储7天

				resp.addCookie(cookie1);

			} else {
				// 将用户名以cookie的形式发送到客户端
				Cookie cookie = new Cookie("username", "");
				// cookie默认是会话级别的,会随着浏览器的关闭，cookie消失
				// 通过setMaxAge设置cookie的存活时间，单位秒
				cookie.setMaxAge(0);// 存储7天

				resp.addCookie(cookie);

				Cookie cookie1 = new Cookie("save", "");
				cookie.setMaxAge(0);// 存储7天

				resp.addCookie(cookie1);
			}

			// 自动登录
			if (autologin != null && autologin.equals("on")) {
				Cookie cookie = new Cookie("autoLogin",
						URLEncoder.encode(users.getUsername(), "utf-8") + "-" + users.getPassword());
				cookie.setMaxAge(60 * 60 * 24 * 7);
				resp.addCookie(cookie);

			}

			// 将登陆的用户存放到session中
			request.getSession().setAttribute("user", users);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 判断登陆用户的角色
		// 如果是admin,跳转到后台管理主页面
		if (users.getRole().equals("admin")) {
			return "redirect:showAdminIndex";
		}

		return "redirect:showIndex";
	}

	// 退出登录
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse resp) {
		HttpSession session = request.getSession();

		// 删除session中存放的登录对象
		// session.removeAttribute("user");

		// 销毁session
		session.invalidate();

		// 去除自动登陆的功能
		// 将自动登陆cookie中的信息清空
		Cookie cookie = new Cookie("autoLogin", "");
		cookie.setMaxAge(0);
		resp.addCookie(cookie);

		return "redirect:showIndex";
	}
}
