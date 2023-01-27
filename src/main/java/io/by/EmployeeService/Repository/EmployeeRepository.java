package io.by.EmployeeService.Repository;

import io.by.EmployeeService.Model.EmployeeModel;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<EmployeeModel, Integer> {
}
