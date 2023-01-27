package io.by.EmployeeService.Model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeRequest {

    @NonNull
    private Integer emp_id;

    @NonNull
    private String emp_name;

    @NonNull
    private String emp_city;

    @NonNull
    private String emp_phone;

    @NonNull
    private Double java_exp;

    @NonNull
    private Double spring_exp;
}
