package com.example.app.helper;

import com.example.app.config.GlobalDataHolder;
import com.example.app.dto.TodoEntityDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SecurityHelper {
    private final StringHelper stringHelper;
    public SecurityHelper(){
        this.stringHelper = new StringHelper();
    }

    public boolean securityCheckTodoEntity(TodoEntityDto dto){
        String name = dto.getName();
        List<String> items = dto.getItems();

        boolean nameCheckRes = stringHelper.checkIfStringLengthLessThan(GlobalDataHolder.maxTodoNameLength, name.length());
        if(!nameCheckRes) return false;

        for(String s : items){
            boolean res = stringHelper.checkIfStringLengthLessThan(GlobalDataHolder.maxTodoNameLength, s.length());
            if(!res) return false;
        }

        return true;
    }
}
