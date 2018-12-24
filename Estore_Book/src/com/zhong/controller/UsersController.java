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
			request.setAttribute("msg", "�������벻һ��");
		} else if (type != null && type.equals("2")) {
			request.setAttribute("msg", "��֤�����");
		} else if (type != null && type.equals("3")) {
			request.setAttribute("msg", "ע��ʧ��");
		}
		return "reg";
	}

	@RequestMapping("reg")
	public String reg(Users user, String repassword, String checkcode, HttpServletRequest request) {
		user.setRole("user");
		user.setUpdatetime(DateUtils.format(new Date()));

		// ����ȷ��
		String password = user.getPassword();
		if (!password.equals(repassword)) {

			return "redirect:showReg.action?type=1";
		}

		// ʹ��MD5����
		user.setPassword(Md5Utils.md5(password));
		user.setState(0);

		// ����һ��������
		String uuid = UUIDUtils.getUUID();
		user.setActivecode(uuid);

		// ��֤���Ƿ���ȷ
		// ��session�л�ȡ������֤��
		String check = (String) request.getSession().getAttribute("checkcode_session");
		// ���û��������֤�������ɵ���֤��Ƚ�
		if (!check.equals(checkcode)) {
			return "redirect:showReg.action?type=2";
		}

		// ����Servic���б���
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
			model.addAttribute("msg", "ע��ɹ������¼");
		} else if ("2".equals(type)) {
			model.addAttribute("msg", "�û������������");
		} else if ("3".equals(type)) {
			model.addAttribute("msg", "��½�ɹ�");
		} else if ("4".equals(type)) {
			model.addAttribute("msg", "���ȵ�¼����в���");
		}
	
		return "login";
	}

	// �����ʻ��ķ���
	@RequestMapping("activation")
	public String activation(String code) {
		// ���ݼ������ѯ�û�
		Users user = userService.findUserByCode(code);
		// ���ʻ���״̬��Ϊ1
		user.setState(1);
		userService.updateUsers(user);
		return "login";
	}

	// �첽У���û���
	@RequestMapping("checkName")
	@ResponseBody
	public String checkName(String username) {
		System.out.println(username);
		// �����û�����ѯ�Ƿ���ڸ��û���
		Users user = userService.findUserByName(username);
		// ������Ϊnull��˵���û�������
		if (user != null) {
			// ����false��ʾ�û�������
			// {"msg":"false"}
			return "{\"msg\":\"false\"}";
		}

		return "{\"msg\":\"true\"}";
	}

	// ��½
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

			// ��ס�û�
			// �ж��û��Ƿ�ѡ��ס�û�
			if (remember != null && remember.equals("on")) {
				// ���û�����cookie����ʽ���͵��ͻ���
				Cookie cookie;
				cookie = new Cookie("username", URLEncoder.encode(users.getUsername(), "utf-8"));
				// cookieĬ���ǻỰ�����,������������Ĺرգ�cookie��ʧ
				// ͨ��setMaxAge����cookie�Ĵ��ʱ�䣬��λ��
				cookie.setMaxAge(60 * 60 * 24 * 7);// �洢7��

				resp.addCookie(cookie);

				Cookie cookie1 = new Cookie("save", "on");
				cookie1.setMaxAge(60 * 60 * 24 * 7);// �洢7��

				resp.addCookie(cookie1);

			} else {
				// ���û�����cookie����ʽ���͵��ͻ���
				Cookie cookie = new Cookie("username", "");
				// cookieĬ���ǻỰ�����,������������Ĺرգ�cookie��ʧ
				// ͨ��setMaxAge����cookie�Ĵ��ʱ�䣬��λ��
				cookie.setMaxAge(0);// �洢7��

				resp.addCookie(cookie);

				Cookie cookie1 = new Cookie("save", "");
				cookie.setMaxAge(0);// �洢7��

				resp.addCookie(cookie1);
			}

			// �Զ���¼
			if (autologin != null && autologin.equals("on")) {
				Cookie cookie = new Cookie("autoLogin",
						URLEncoder.encode(users.getUsername(), "utf-8") + "-" + users.getPassword());
				cookie.setMaxAge(60 * 60 * 24 * 7);
				resp.addCookie(cookie);

			}

			// ����½���û���ŵ�session��
			request.getSession().setAttribute("user", users);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// �жϵ�½�û��Ľ�ɫ
		// �����admin,��ת����̨������ҳ��
		if (users.getRole().equals("admin")) {
			return "redirect:showAdminIndex";
		}

		return "redirect:showIndex";
	}

	// �˳���¼
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse resp) {
		HttpSession session = request.getSession();

		// ɾ��session�д�ŵĵ�¼����
		// session.removeAttribute("user");

		// ����session
		session.invalidate();

		// ȥ���Զ���½�Ĺ���
		// ���Զ���½cookie�е���Ϣ���
		Cookie cookie = new Cookie("autoLogin", "");
		cookie.setMaxAge(0);
		resp.addCookie(cookie);

		return "redirect:showIndex";
	}
}
