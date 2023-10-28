package com.example.demo.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@ToString
public class Profile {

    @Id
    private Long empCode;
    private String empName;
    private String profileName;

    public Profile(Long empCode, String empName, String profileName) {
        this.empCode = empCode;
        this.empName = empName;
        this.profileName = profileName;
    }

}
