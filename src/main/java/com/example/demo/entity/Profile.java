package com.example.demo.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "profile")
@Entity
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "EMP_CODE", nullable = false)
    private Long empCode;

    @Column(name = "EMP_NAME")
    private String empName;

    @Column(name = "PROFILE_NAME")
    private String profileName;

    public Profile(Long empCode, String empName, String profileName) {
        this.empCode = empCode;
        this.empName = empName;
        this.profileName = profileName;
    }

}
