package com.example.demo.processor;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Profile;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeProcessor implements ItemProcessor<Employee, Profile> {
    @Override
    public Profile process(Employee emp) throws Exception {
        String profileName="";

        if(emp.getExpInYears() < 5){
            profileName = "Developer";
        } else if(emp.getExpInYears() <=8) {
            profileName = "Team lead";
        } else if(emp.getExpInYears() > 8){
            profileName = "manager";
        }

        System.out.println("Emp Code : "+ emp.getEmpCode()+
                " , Emp Name : "+emp.getEmpName()+ ", Profile Name : "+profileName );

        return new Profile(emp.getEmpCode(), emp.getEmpName(),profileName);
    }
}
