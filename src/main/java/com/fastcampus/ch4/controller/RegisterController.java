package com.fastcampus.ch4.controller;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.fastcampus.ch4.dao.UserDao;
import com.fastcampus.ch4.domain.User;
import com.fastcampus.ch4.domain.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller // ctrl+shift+o �옄�룞 import
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserDao userDao;
    final int FAIL = 0;

    @InitBinder
    public void toDate(WebDataBinder binder) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
        binder.setValidator(new UserValidator()); // UserValidator瑜� WebDataBinder�쓽 濡쒖뺄 validator濡� �벑濡�
        //	List<Validator> validatorList = binder.getValidators();
        //	System.out.println("validatorList="+validatorList);
    }

    @GetMapping("/add")
    public String register() {
        return "registerForm"; // WEB-INF/views/registerForm.jsp
    }

    @PostMapping("/add")
    public String save(@Valid User user, BindingResult result, Model m) throws Exception {
        System.out.println("result="+result);
        System.out.println("user="+user);

        // User媛앹껜瑜� 寃�利앺븳 寃곌낵 �뿉�윭媛� �엳�쑝硫�, registerForm�쓣 �씠�슜�빐�꽌 �뿉�윭瑜� 蹂댁뿬以섏빞 �븿.
        if(!result.hasErrors()) {


            // 2. DB�뿉 �떊洹쒗쉶�썝 �젙蹂대�� ���옣
            int rowCnt = userDao.insertUser(user);


            if (rowCnt != FAIL)
                return "registerInfo";

        }
        return "registerForm";
    }

    private boolean isValid(User user) {
        return true;
    }
}