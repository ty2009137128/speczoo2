package com.graduation.controller;

import com.graduation.model.AuthorityMethod;
import com.graduation.model.AuthorityType;
import com.graduation.model.Role;
import com.graduation.model.dto.RoleDto;
import com.graduation.service.IRoleService;
import com.graduation.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Inject
    private IRoleService roleService;

    @AuthorityMethod(authorityTypes = AuthorityType.ROLE_LIST)
    @RequestMapping("/roles")
    public String toRoles(Model model) {
        List<Role> roles = this.roleService.list();
        model.addAttribute("roles", roles);
        return "role/role";
    }

    @AuthorityMethod(authorityTypes = AuthorityType.ROLE_CREATE)
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        RoleDto roleDto = new RoleDto();
        model.addAttribute("roleDto", roleDto);
        return "role/create";
    }

    @AuthorityMethod(authorityTypes = AuthorityType.ROLE_CREATE)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(RoleDto roleDto, HttpServletRequest request, Model model) {
        if (roleDto != null) {
            boolean isValid = true;
            String msg = null;

            if (roleDto.getName() == null) {
                isValid = false;
                msg = "Please enter role name.";
            }

            if (!isValid) {
                model.addAttribute("errorMsg", msg);
                model.addAttribute("roleDto", roleDto);
                return "role/create";
            }

            Role role = transformRoleDtoToRole(roleDto);
            int cnt = roleService.add(role);
        }

        return "redirect:/role/roles";
    }

    @AuthorityMethod(authorityTypes = AuthorityType.ROLE_UPDATE)
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Integer id, Model model) {
        if (id != null) {
            Role role = this.roleService.getRoleById(id);
            RoleDto roleDto = transformRoleToRoleDto(role);
            model.addAttribute("roleDto", roleDto);
        }

        return "role/update";
    }

    @AuthorityMethod(authorityTypes = AuthorityType.ROLE_UPDATE)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateRole(RoleDto roleDto, HttpServletRequest request, Model model) {
        if (roleDto != null) {
            boolean isValid = true;
            String msg = null;

            if (roleDto.getName() == null) {
                isValid = false;
                msg = "Please enter role name.";
            }

            if (!isValid) {
                model.addAttribute("errorMsg", msg);
                model.addAttribute("roleDto", roleDto);
                return "role/update";
            }

            Role role = transformRoleDtoToRole(roleDto);
            int cnt = this.roleService.update(role);
        }

        return "redirect:roles";
    }

    @AuthorityMethod(authorityTypes = AuthorityType.ROLE_DELETE)
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteRole(HttpServletResponse response, Integer id) {
        int cnt = 0;

        if (id != null) {
            cnt = this.roleService.deleteRoleById(id);
        }

        String result = "failure";
        if (cnt == 1) {
            result = "success";
        }
        ResponseUtil.sendOperationResult(response, result);

        return null;
    }

    private RoleDto transformRoleToRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        roleDto.setDec(role.getDec());
        return roleDto;
    }

    private Role transformRoleDtoToRole(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        role.setDec(roleDto.getDec());
        return role;
    }
}
