package com.graduation.controller;

import com.graduation.common.Pager;
import com.graduation.model.*;
import com.graduation.model.dto.UserDto;
import com.graduation.service.IGroupService;
import com.graduation.service.IRoleService;
import com.graduation.service.IUserService;
import com.graduation.util.AuthorityUtil;
import com.graduation.util.ResponseUtil;
import com.graduation.web.SessionContext;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Inject
    private IUserService userService;
    @Inject
    private IRoleService roleService;
    @Inject
    private IGroupService groupService;

    // Login part ************************************************************************************

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "admin/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(required = true, value = "username") String username,
                        @RequestParam(required = true, value = "password") String password,
                        @RequestParam(required = false, value = "checkCode") String checkCode,
                        HttpServletRequest request, Model model, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Object rightCheckCode = session.getAttribute("checkCode");
        if (rightCheckCode == null) {
            model.addAttribute("error", "Session timeout, please retry!");
            return "admin/login";
        } else if (!rightCheckCode.toString().equals(checkCode)) {
            model.addAttribute("error", "Check code is incorrect!");
            return "admin/login";
        }

        username = username.trim();
        password = password.trim();
        String msgUserName = User.checkUserName(username);
        String msgPassword = User.checkPassword(password);

        if (msgUserName != null || msgPassword != null) {
            model.addAttribute("error", "User name or password is incorrect!");
            return "admin/login";
        }

        User user = this.userService.login(username, password);
        if (null == user) {
            model.addAttribute("error", "User name or password is incorrect!");
            return "admin/login";
        }

        session.setAttribute("loginUser", user);
        session.removeAttribute("checkCode");
        SessionContext.addSessoin(session);
        model.addAttribute("loginUser", user);
        //"redirect:/home"
        return "redirect:/";
    }

    @RequestMapping(value = "/getCheckCode", method = RequestMethod.GET)
    public void drawCheckCode(HttpServletResponse response, HttpSession session) throws IOException {
        response.setContentType("image/jpg");
        Captcha captcha = Captcha.getInstance();
        captcha.setWidth(80);
        captcha.setHeight(25);
        String checkCode = captcha.generateCheckCode();
        session.setAttribute("checkCode", checkCode);
        BufferedImage img = captcha.generateCheckImg(checkCode);
        OutputStream os = response.getOutputStream();
        ImageIO.write(img, "jpg", os);
        os.close();
    }

    @RequestMapping(value = "/checkCheckCode", method = RequestMethod.GET)
    @ResponseBody
    public void checkCheckCode(HttpServletRequest request, HttpServletResponse response,
                               String checkCode) {
        String result = "right";

        if (!request.getSession().getAttribute("checkCode").equals(checkCode)) {
            result = "error";
        }

        ResponseUtil.sendOperationResult(response, result);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        session.invalidate();
        return "redirect:login";
    }


    // Admin part ************************************************************************************

    @AuthorityMethod(authorityTypes = {AuthorityType.USER_LIST})
    @RequestMapping("/users")
    public String toUsers(Model model) {
        Pager<User> pager = this.userService.findPager();
        model.addAttribute("pager", pager);
        return "user/user";
    }

    @AuthorityMethod(authorityTypes = AuthorityType.USER_CREATE)
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createUser(Model model) {
        UserDto userDto = new UserDto();
        userDto.setStatus(1);
        List<Role> roles = this.roleService.list();
        List<Group> groups = this.groupService.list();

        model.addAttribute("userDto", userDto);
        model.addAttribute("roles", roles);
        model.addAttribute("groups", groups);
        return "user/create";
    }

    @AuthorityMethod(authorityTypes = AuthorityType.USER_CREATE)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createUser(UserDto userDto, HttpServletRequest request, Model model) {
        if (userDto != null) {
            boolean isValid = true;
            String msg = User.checkUserName(userDto.getUsername());
            if (msg != null) {
                isValid = false;
            } else if ((msg = User.checkPassword(userDto.getPassword())) != null) {
                isValid = false;
            } else if (userDto.getRoleIds() == null || userDto.getRoleIds().size() == 0) {
                msg = "Please select a role.";
                isValid = false;
            } else if (userDto.getGroupIds() == null || userDto.getGroupIds().size() == 0) {
                msg = "Please select a group.";
                isValid = false;
            }

            if (!isValid) {
                model.addAttribute("errorMsg", msg);
                List<Role> roles = this.roleService.list();
                List<Group> groups = this.groupService.list();
                model.addAttribute("userDto", userDto);
                model.addAttribute("roles", roles);
                model.addAttribute("groups", groups);
                return "user/create";
            }

            User user = transformUserDtoToUser(userDto);
            int userId = userService.createUser(user, userDto.getRoleIds(), userDto.getGroupIds());
        }

        return "redirect:users";
    }

    @AuthorityMethod(authorityTypes = AuthorityType.USER_UPDATE, resultType = ResultType.PAGE)
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Integer id, Model model) {
        if (id != null) {
            User user = this.userService.getUserById(id);
            UserDto userDto = transformUserToUserDto(user);
            List<Role> roles = this.roleService.list();
            List<Group> groups = this.groupService.list();

            model.addAttribute("userDto", userDto);
            model.addAttribute("roles", roles);
            model.addAttribute("groups", groups);
        }

        return "user/update";
    }

    @AuthorityMethod(authorityTypes = AuthorityType.USER_UPDATE, resultType = ResultType.PAGE)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(UserDto userDto, HttpServletRequest request, Model model) {
        if (userDto != null) {
            boolean isValid = true;
            String msg = User.checkUserName(userDto.getUsername());

            if (msg != null) {
                isValid = false;
            } else if ((msg = User.checkPassword(userDto.getPassword())) != null) {
                isValid = false;
            } else if (userDto.getRoleIds() == null || userDto.getRoleIds().size() == 0) {
                msg = "Please select a role.";
                isValid = false;
            } else if (userDto.getGroupIds() == null || userDto.getGroupIds().size() == 0) {
                msg = "Please select a group.";
                isValid = false;
            }

            if (!isValid) {
                model.addAttribute("errorMsg", msg);
                List<Role> roles = this.roleService.list();
                List<Group> groups = this.groupService.list();
                model.addAttribute("userDto", userDto);
                model.addAttribute("roles", roles);
                model.addAttribute("groups", groups);
                return "user/update";
            }

            int cnt = this.userService.update(transformUserDtoToUser(userDto));
        }

        return "redirect:/user/users";
    }

    @AuthorityMethod(authorityTypes = AuthorityType.USER_DELETE, resultType = ResultType.JSON)
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public String delete(HttpServletResponse response, Integer id) {
        int cnt = 0;

        if (id != null) {
            cnt = this.userService.deleteUserById(id);
        }

        String result = "success";
        if (cnt != 1) {
            result = "failure";
        }
        ResponseUtil.sendOperationResult(response, result);

        return null;
    }

    @AuthorityMethod(authorityTypes = AuthorityType.USER_INIT_PASSWORD, resultType = ResultType.JSON)
    @RequestMapping("/initPassword")
    @ResponseBody
    public String initPs(HttpServletResponse response, Integer id) {
        String result = "failure";

        if (userService.initPassword(id) == 1) {
            result = "success";
        }

        ResponseUtil.sendOperationResult(response, result);
        return null;
    }

    @AuthorityMethod(authorityTypes = AuthorityType.AUTHORITY_EDIT)
    @RequestMapping(value = "/authority/{id}", method = RequestMethod.GET)
    public String editAuthority(@PathVariable("id") Integer id, Model model) {
        if (id != null) {
            EnumSet<AuthorityType> allAuthorityTypes = EnumSet.allOf(AuthorityType.class);
            User user = this.userService.getUserById(id);
            List<Integer> authorityTypeIndexes = AuthorityUtil.getAuthorityTypeIndexes(user.getAuthority());

            model.addAttribute("userId", id);
            model.addAttribute("allAuthorityTypes", allAuthorityTypes);
            model.addAttribute("authorityTypeIndexes", authorityTypeIndexes);
        }

        return "admin/authority_edit";
    }

    @AuthorityMethod(authorityTypes = AuthorityType.AUTHORITY_EDIT)
    @RequestMapping(value = "/authority", method = RequestMethod.POST)
    public String editAuthority(HttpServletRequest request) {
        String tempUserId = request.getParameter("userId");
        int userId = 0;

        try {
            userId = Integer.parseInt(tempUserId);
        } catch (Exception e) {
            e.printStackTrace();
            userId = 0;
        }

        if (userId > 0) {
            StringBuilder authority = new StringBuilder();

            EnumSet<AuthorityType> allAuthorityTypes = EnumSet.allOf(AuthorityType.class);
            Iterator<AuthorityType> iterator = allAuthorityTypes.iterator();
            while (iterator.hasNext()) {
                AuthorityType authorityType = iterator.next();
                String authorityItem = request.getParameter(authorityType.getName());

                if ("1".equals(authorityItem)) {
                    authority.append("1");
                } else {
                    authority.append("0");
                }
            }

            int cnt = this.userService.updateUserAuthorityById(userId, authority.toString());
        } else {
            //Prompt code.
        }

        return "redirect:/user/users";
    }

// Private part ************************************************************************************

    @RequestMapping(value = "/myInfo", method = RequestMethod.GET)
    public String getMyInfo(Model model, HttpServletRequest request) {
        Object user = request.getSession().getAttribute("loginUser");

        if (user != null) {
            User temp = this.userService.getUserById(((User) user).getId());

            List<String> roleNames = new ArrayList<String>();
            if (temp.getRoleIds() != null && temp.getRoleIds().size() > 0) {
                for (int i = 0; i < temp.getRoleIds().size(); i++) {
                    String roleName = this.roleService.getRoleById(temp.getRoleIds().get(i)).getName();
                    roleNames.add(roleName);
                }
            }

            List<String> groupNames = new ArrayList<String>();
            if (temp.getGroupIds() != null && temp.getGroupIds().size() > 0) {
                for (int i = 0; i < temp.getGroupIds().size(); i++) {
                    String groupName = this.groupService.getGroupById(temp.getGroupIds().get(i)).getName();
                    groupNames.add(groupName);
                }
            }

            UserDto userDto = transformUserToUserDto(temp);
            model.addAttribute("userInfo", userDto);
            model.addAttribute("roleNames", roleNames);
            model.addAttribute("groupNames", groupNames);
        }

        return "user/myInfo";
    }

    @RequestMapping(value = "/updateMyInfo", method = RequestMethod.GET)
    public String updateMyInfo(HttpServletRequest request, Model model) {
        Object temp = request.getSession().getAttribute("loginUser");

        if (temp != null) {
            User user = this.userService.getUserById(((User) temp).getId());
            UserDto userDto = transformUserToUserDto(user);
            model.addAttribute("userDto", userDto);
        }

        return "user/updateMyInfo";
    }

    @RequestMapping(value = "/updateMyInfo", method = RequestMethod.POST)
    public String saveMyInfo(UserDto userDto, HttpServletRequest request, Model model) {
        User user = null;

        if (userDto != null) {
            boolean isValid = true;
            String msg = User.checkUserName(userDto.getUsername());

            if (msg != null) {
                isValid = false;
            }

            if (!isValid) {
                model.addAttribute("errorMsg", msg);
                model.addAttribute("userDto", userDto);
                return "user/updateMyInfo";
            }

            user = transformUserDtoToUser(userDto);
            int cnt = this.userService.updateUserInfo(user);
            request.getSession().setAttribute("loginUser", user);
        }

        return "redirect:myInfo";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePassword() {
        return "user/changePassword";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String savePassword(@RequestParam(value = "originalPsw", required = true) String originalPsw,
                               @RequestParam(value = "newPsw", required = true) String newPsw,
                               HttpServletRequest request, Model model) {
        Object temp = request.getSession().getAttribute("loginUser");

        if (temp != null) {
            User user = this.userService.getUserById(((User) temp).getId());
            boolean isValid = true;
            String msg = null;

            if (!user.getPassword().equals(originalPsw)) {
                isValid = false;
                msg = "The original password is incorrect.";
            } else if ((msg = User.checkPassword(newPsw)) != null) {
                isValid = false;
            }

            if (!isValid) {
                model.addAttribute("errorMsg", msg);
                return "user/changePassword";
            }

            int cnt = this.userService.updatePasswordById(user.getId(), newPsw);
            request.getSession().setAttribute("loginUser", user);
        }

        return "redirect:myInfo";
    }

    private UserDto transformUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setConfirmPwd(user.getPassword());
        userDto.setNickname(user.getNickname());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setStatus(user.getStatus());
        userDto.setCreateDate(user.getCreateDate());
        userDto.setCompany(user.getCompany());
        userDto.setRoleIds(user.getRoleIds());
        userDto.setGroupIds(user.getGroupIds());
        return userDto;
    }

    private User transformUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setNickname(userDto.getNickname());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setStatus(userDto.getStatus());
        user.setCreateDate(userDto.getCreateDate());
        user.setCompany(userDto.getCompany());
        user.setRoleIds(userDto.getRoleIds());
        user.setGroupIds(userDto.getGroupIds());
        return user;
    }
}
