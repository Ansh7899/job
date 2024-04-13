package com.jobapp.job.external;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Company {
    private long id;
    private String name;
    private String description;

//    This means that it has a one-to-many relationship with Job table,
//    meaning one company can have several jobs
//    mapped by parameter basically tells that within the Job Entity, we have a field company which maps the relationship
//    JsonIgnore is used to remove the recursive calls between job and company dependencies
//    @JsonIgnore
//    @OneToMany(mappedBy = "company")
//    private List<Long> jobIds;

//    @OneToMany(mappedBy = "company")
//    private List<Long> reviewIds;
}
