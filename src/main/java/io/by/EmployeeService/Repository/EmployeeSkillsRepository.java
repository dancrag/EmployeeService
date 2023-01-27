package io.by.EmployeeService.Repository;

import io.by.EmployeeService.Model.EmployeeSkillsModel;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeSkillsRepository extends CrudRepository<EmployeeSkillsModel, MapId> {

    @AllowFiltering
    List<EmployeeSkillsModel> findByJavaExpGreaterThanEqual(Double javaExp);

    @AllowFiltering
    List<EmployeeSkillsModel> findBySpringExpGreaterThanEqual(Double springExp);

    @Query("SELECT * FROM emp_skill WHERE java_exp >= :javaExp AND spring_exp >= :springExp ALLOW FILTERING")
    List<EmployeeSkillsModel> findByJavaAndSpringExp(@Param("javaExp") Double javaExp, @Param("springExp") Double springExp);
}
