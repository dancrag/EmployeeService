package io.by.EmployeeService.Model;

import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "emp_skill")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeSkillsModel {

    @PrimaryKeyColumn(value = "emp_id", type = PrimaryKeyType.PARTITIONED)
    @NonNull
    private Integer emp_id;

    @PrimaryKeyColumn(value = "java_exp")
    @NonNull
    private Double javaExp;

    @PrimaryKeyColumn(value = "spring_exp")
    @NonNull
    private Double springExp;

}
