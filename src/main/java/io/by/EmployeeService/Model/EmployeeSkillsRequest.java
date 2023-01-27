package io.by.EmployeeService.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeSkillsRequest {
    private Double java_exp;
    private Double spring_exp;
}
