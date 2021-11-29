package servlet;

import dao.UserDAO;
import model.User;
import org.apache.commons.beanutils.BeanUtils;
import support.CookieUtils;
import support.PageInfo;
import support.PageType;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@WebServlet({
        "/User",
        "/Sign-in",
        "/User/Sign-up",
        "/Sign-out",
        "/User/Edit-profile"

})
public class ServletUser extends HttpServlet {
    HttpSession session;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void destroy() {
        session.setAttribute("user", null);
        session.setAttribute("remember", null);
        super.destroy();
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        session = req.getSession();
        String uri = req.getRequestURI().toLowerCase();
        if (uri.contains("sign-in")) {
            doSignin(req, resp);
        }  else if (uri.contains("user/sign-up")) {
            doSignup(req, resp);
        } else if (uri.contains("sign-out")) {
            session.setAttribute("user", null);
            doSignin(req, resp);
        } else  if (uri.contains("user/edit-profile")){
            if (session.getAttribute("user") != null){
                doEditprooffile(req, resp);
            }else {
                req.setAttribute("msgFailed", "Bạn chưa đăng nhập");
                doSignin(req, resp);
            }
        }
    }

    public void doSignin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageType pageType = PageType.SITE_SIGNIN_USER;
        UserDAO userDAO = new UserDAO();
        String method = req.getMethod();
        User usSession = (User) session.getAttribute("user");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        if (session.getAttribute("remember") != null){
            req.setAttribute("username", usSession.getUsername());
            req.setAttribute("password", usSession.getPassword());
        }
        User user;
        if (method.equalsIgnoreCase("Post")) {
            try {
                user = userDAO.findById(username);
                if (password.equals(user.getPassword())) {
                        session.setAttribute("user", user);
                    if (remember != null) {
                        session.setAttribute("remember", "remember");
                    } else {
                        session.setAttribute("remember", null);
                    }
                    req.setAttribute("msg", "Đăng nhập thành công");
                    pageType = PageType.SITE_HOME;
                } else {
                    req.setAttribute("msgFailed", "Sai tài khoản hoặc mật khẩu");
                }
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("msgFailed", "Đăng nhập không thành công" + e);
            }
        }
        PageInfo.prepareAndForward(req, resp, pageType);
    }

    public void doSignup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageType pageType = PageType.SITE_REGISTER_USER;
        UserDAO userDAO = new UserDAO();
        String method = req.getMethod();
        User user = new User();
        if (method.equalsIgnoreCase("Post")) {
            try {
                BeanUtils.populate(user, req.getParameterMap());
                userDAO.insert(user);
                req.setAttribute("msg", "Đăng ký thành công");
                pageType = PageType.SITE_SIGNIN_USER;
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("msgFailed", "Đăng ký không thành công" + e);
            }
        }
        PageInfo.prepareAndForward(req, resp, pageType);
    }
    public void doEditprooffile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PageType pageType = PageType.SITE_EDIT_PROFILE;
        UserDAO userDAO = new UserDAO();
        String method = req.getMethod();
        User user = (User) session.getAttribute("user");
        if (method.equalsIgnoreCase("Post")) {
            try {
                BeanUtils.populate(user, req.getParameterMap());
                System.out.println(user.getPassword());
//                userDAO.update(user);
//                req.setAttribute("msg", "Cập nhật thông tin thành công");
//                pageType = PageType.SITE_SIGNIN_USER;
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("msgFailed", "Cập nhật không thành công không thành công" + e);
            }
        }
        PageInfo.prepareAndForward(req, resp, pageType);
    }
}