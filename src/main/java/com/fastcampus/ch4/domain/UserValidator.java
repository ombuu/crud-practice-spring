package com.fastcampus.ch4.domain;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
//		return User.class.equals(clazz); // 寃�利앺븯�젮�뒗 媛앹껜媛� User���엯�씤吏� �솗�씤
        return User.class.isAssignableFrom(clazz); // clazz媛� User �삉�뒗 洹� �옄�넀�씤吏� �솗�씤
    }

    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("UserValidator.validate() is called");

        User user = (User)target;

        String id = user.getId();

//		if(id==null || "".equals(id.trim())) {
//			errors.rejectValue("id", "required");
//		}
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",  "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "required");

        if(id==null || id.length() <  5 || id.length() > 12) {
            errors.rejectValue("id", "invalidLength", new String[]{"5","12"}, null);
        }
    }
}