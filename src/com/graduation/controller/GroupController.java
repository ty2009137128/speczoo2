package com.graduation.controller;

import com.graduation.model.AjaxObj;
import com.graduation.model.AuthorityMethod;
import com.graduation.model.AuthorityType;
import com.graduation.model.Group;
import com.graduation.model.dto.GroupDto;
import com.graduation.service.IGroupService;
import com.graduation.util.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/group")
public class GroupController {

    @Inject
    private IGroupService groupService;

    @AuthorityMethod(authorityTypes = AuthorityType.GROUP_LIST)
    @RequestMapping("/groups")
    public String toGroup(Model model) {
        List<Group> groups = this.groupService.list();
        model.addAttribute("groups", groups);
        return "group/group";
    }

    @AuthorityMethod(authorityTypes = AuthorityType.GROUP_CREATE)
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        GroupDto groupDto = new GroupDto();
        model.addAttribute("groupDto", groupDto);
        return "group/create";
    }

    @AuthorityMethod(authorityTypes = AuthorityType.GROUP_CREATE)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createGroup(GroupDto groupDto, HttpServletResponse response,
                              HttpServletRequest request, Model model) throws IOException {
        if (groupDto != null) {
            boolean isValid = true;
            String msg = null;

            if (groupDto.getName() == null) {
                isValid = false;
                msg = "Please enter group name.";
            }

            if (!isValid) {
                model.addAttribute("errorMsg", msg);
                model.addAttribute("groupDto", groupDto);
                return "group/create";
            }

            Group group = transformGroupDtoToGroup(groupDto);
            this.groupService.add(group);
            response.sendRedirect("groups");
        }

        return null;
    }

    @AuthorityMethod(authorityTypes = AuthorityType.GROUP_DELETE)
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteGroup(Integer id) {
        int cnt = 0;
        if (id != null) {
            cnt = this.groupService.deleteGroupById(id);
        }

        AjaxObj ajax = new AjaxObj();
        ajax.setResult(cnt);
        if (cnt == 0) {
            ajax.setMessage("Delete group failed, please retry later.");
        }

        return JsonUtil.getInstance().obj2json(ajax);
    }

    @AuthorityMethod(authorityTypes = AuthorityType.GROUP_UPDATE)
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Integer id, Model model) {
        if (id != null) {
            Group group = this.groupService.getGroupById(id);
            GroupDto groupDto = transformGroupToGroupDto(group);
            model.addAttribute("groupDto", groupDto);
        }

        return "group/update";
    }

    @AuthorityMethod(authorityTypes = AuthorityType.GROUP_UPDATE)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateGroup(GroupDto groupDto, HttpServletResponse response,
                              HttpServletRequest request, Model model) throws IOException {
        if (groupDto != null) {
            boolean isValid = true;
            String msg = null;

            if (groupDto.getName() == null) {
                isValid = false;
                msg = "Please enter group name.";
            }

            if (!isValid) {
                model.addAttribute("errorMsg", msg);
                model.addAttribute("groupDto", groupDto);
                return "group/update";
            }

            Group group = transformGroupDtoToGroup(groupDto);
            int cnt = this.groupService.update(group);
            response.sendRedirect("groups");
        }

        return null;
    }

    private GroupDto transformGroupToGroupDto(Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setName(group.getName());
        groupDto.setDec(group.getDec());
        return groupDto;
    }

    private Group transformGroupDtoToGroup(GroupDto groupDto) {
        Group group = new Group();
        group.setId(groupDto.getId());
        group.setName(groupDto.getName());
        group.setDec(groupDto.getDec());
        return group;
    }
}
