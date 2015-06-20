package com.graduation.controller;

import java.util.*;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.graduation.model.MydbElement;
import com.graduation.model.User;
import com.graduation.service.IMydbService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.graduation.common.Pager;
import com.graduation.model.SearchParam;
import com.graduation.model.SpectroscopicElement;
import com.graduation.service.IOmlElementService;
import com.graduation.service.ISpectroscopicService;
import com.graduation.util.ParameterHandleUtil;

@Controller
@RequestMapping("/query")
public class OmlElementController {

    @Inject
    private ISpectroscopicService spectroscopicService;
    @Inject
    private IMydbService mydbService;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String conditionalSearch(SearchParam sp, BindingResult error, Model model) {
        System.out.println(sp.getTableName());
        this.getSearchResult(sp, model);
        return "query/search";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String nextPager(SearchParam sp, BindingResult error, Model model) {
//        this.getSearchResult(sp, model);
        return "query/search";
    }

    private void getSearchResult(SearchParam sp, Model model) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        List<String> errorMsg = ParameterHandleUtil.handleParameters(sp, parameters);
        if ("0".equals(sp.getOr_and())) {
            parameters.put("or_and", "0");
        } else {
            parameters.put("or_and", "1");
        }
        if (null == sp.getTableName() || "".equals(sp.getTableName().trim())) {
            sp.setTableName("fithas");
        }
        parameters.put("tableName", sp.getTableName());

        if (parameters.get("tableName").equals("fithas")){
            Pager<SpectroscopicElement> ses = this.spectroscopicService.find(parameters);
            model.addAttribute("sp", sp);
            model.addAttribute("errorMsg", errorMsg);
            model.addAttribute("ses", ses);
            model.addAttribute("tableName", sp.getTableName());
            model.addAttribute("isFithas", true);
        } else {
            MydbElement mydb = this.mydbService.getMydbElementByTableName(parameters.get("tableName").toString());
            if (null == mydb) {
                model.addAttribute("error", "You have not create the table.....");
                return;
            }
            Pager<Map<String, Object>> pager = this.spectroscopicService.findTableData(parameters);

            Set<String> names = pager.getDatas().get(0).keySet();
            model.addAttribute("names", names);
            model.addAttribute("pager", pager);
            model.addAttribute("sp", sp);
            model.addAttribute("errorMsg", errorMsg);
            model.addAttribute("tableName", sp.getTableName());
            model.addAttribute("isFithas", false);
        }
    }

    /*@RequestMapping(value = "/sqlsearch", method = RequestMethod.GET)
    public String sqlSearch() {
        return "query/sqlsearch";
    }

    @RequestMapping(value = "/sqlsearch", method = RequestMethod.POST)
    public String sqlSearch(@RequestParam String sql, Model model) {
        List<String> error = new ArrayList<String>();
        if (sql == null || "".equals(sql.trim())) {
            error.add("sql语句不能为空....");
            model.addAttribute("error", error);
            model.addAttribute("sql", sql);
            return "query/sqlsearch";
        }
        List<Map<String, Object>> lmps = this.spectroscopicService.executeSql(sql, error);
        if (lmps == null || lmps.size() == 0) {
            error.add("没有您想要的数据.....");
            model.addAttribute("sql", sql);
            model.addAttribute("error", error);
            return "query/sqlsearch";
        }
        model.addAttribute("nams", lmps.get(0).keySet());
        model.addAttribute("lmps", lmps);
        model.addAttribute("error", error);
        model.addAttribute("sql", sql);
        return "query/sqlsearch";
    }*/

    @RequestMapping(value = "/getOwnTable")
    @ResponseBody
    public List<String> getTableNameByUid(@RequestParam(value = "term", required = false, defaultValue = "f") String keyword, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        List<String> tableNames = this.mydbService.listTableNames(loginUser.getId(), keyword);
        return tableNames;
    }



   /* private void getSearchResult2(SearchParam sp, Model model) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        List<String> errorMsg = ParameterHandleUtil.handleParameters(sp, parameters);
        if ("0".equals(sp.getOr_and())) {
            parameters.put("or_and", "0");
        } else {
            parameters.put("or_and", "1");
        }
        if(null == sp.getTableName() || "".equals(sp.getTableName().trim())){
            sp.setTableName("fithas");
        }
        parameters.put("tableName",sp.getTableName());
       // Pager<SpectroscopicElement> ses = this.spectroscopicService.find(parameters);
        Pager<Map<String,Object>> pager = this.spectroscopicService.findTableData(parameters);

        Set<String> names = pager.getDatas().get(0).keySet();
        model.addAttribute("names", names);
        model.addAttribute("pager", pager);
        model.addAttribute("sp", sp);
        model.addAttribute("errorMsg", errorMsg);
        //model.addAttribute("ses", ses);
        model.addAttribute("tableName",sp.getTableName());
    }*/
}
