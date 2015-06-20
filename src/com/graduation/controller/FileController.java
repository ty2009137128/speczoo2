package com.graduation.controller;

import com.graduation.model.AuthorityMethod;
import com.graduation.model.AuthorityType;
import com.graduation.model.ResultType;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    @AuthorityMethod(authorityTypes = AuthorityType.FILE_UPLOAD)
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload() {
        return "updownload/upload";

    }

    @AuthorityMethod(authorityTypes = AuthorityType.FILE_UPLOAD)
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(MultipartFile file, HttpServletRequest request, Model model) {
        try {
            String fileName = file.getOriginalFilename();
            String realpath = request.getSession().getServletContext().getRealPath("/upload");
            File dir = new File(realpath);
            if (dir.exists() == false) {
                dir.mkdirs();
            }
            File destFile = new File(dir, fileName);
            FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "updownload/upload";
    }
}
