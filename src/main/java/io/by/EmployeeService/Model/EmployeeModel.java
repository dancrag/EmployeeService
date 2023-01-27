package io.by.EmployeeService.Model;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "emp")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeModel {

    @PrimaryKey
    @NonNull
    private Integer emp_id;

    @Column(value = "emp_city")
    @NonNull
    private String city;

    @Column(value = "emp_name")
    @NonNull
    private String name;


    @Column(value = "emp_phone")
    @NonNull
    private String phone;
}
