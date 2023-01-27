package io.by.EmployeeService.Controller;

import io.by.EmployeeService.Model.*;
import io.by.EmployeeService.Repository.EmployeeRepository;
import io.by.EmployeeService.Repository.EmployeeSkillsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@Slf4j
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeSkillsRepository employeeSkillsRepository;

    @Autowired
    KafkaTemplate<String, EmployeeRequest> kafkaTemplate;

    @PostMapping("/createEmployee")
    public ResponseEntity<EmployeeRequest> createEmployee(@RequestBody EmployeeRequest employee) {
        try {
            EmployeeModel newEmployee = employeeRepository.save(
                    new EmployeeModel(
                            employee.getEmp_id(),
                            employee.getEmp_city(),
                            employee.getEmp_name(),
                            employee.getEmp_phone()
                    )
            );

            try {
                EmployeeSkillsModel employeeSkillsModel = employeeSkillsRepository.save(
                        new EmployeeSkillsModel(
                                employee.getEmp_id(),
                                employee.getJava_exp(),
                                employee.getSpring_exp()
                        )
                );

                kafkaTemplate.send("app_update", employee);

                return new ResponseEntity<>(employee, HttpStatus.CREATED);

            } catch (Exception exception) {
                System.out.println(exception);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (Exception exception) {
            System.out.println(exception);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/findEmpSkillset")
    public ResponseEntity<List<EmployeeResponse>> findEmpSkillset(@RequestBody EmployeeSkillsRequest employeeSkillsRequest) {

        try {

            if (employeeSkillsRequest.getSpring_exp() == null || employeeSkillsRequest.getJava_exp() == null) {
                throw new Exception("Some of the fields are null, please provide a correct value");
            }

            List<EmployeeSkillsModel> employeeSkillsModelList =  employeeSkillsRepository.findByJavaAndSpringExp(employeeSkillsRequest.getJava_exp(), employeeSkillsRequest.getSpring_exp());

            List<Integer> listOfIds = employeeSkillsModelList.stream().map(EmployeeSkillsModel::getEmp_id).collect(Collectors.toList());

            List<EmployeeModel> employeeModelList = new ArrayList<>();
            employeeRepository.findAllById(listOfIds).forEach(employeeModelList::add);

            List<EmployeeResponse> employeeResponseList = new ArrayList<>();

            listOfIds.forEach(id -> {
                EmployeeModel employeeModel = employeeModelList.stream().filter(employee -> employee.getEmp_id().equals(id)).findFirst().orElse(null);
                EmployeeSkillsModel employeeSkillsModel = employeeSkillsModelList.stream().filter(employee -> employee.getEmp_id().equals(id)).findFirst().orElse(null);
                employeeResponseList.add(
                        new EmployeeResponse(
                                employeeModel.getEmp_id(),
                                employeeModel.getName(),
                                employeeModel.getCity(),
                                employeeModel.getPhone(),
                                employeeSkillsModel.getJavaExp(),
                                employeeSkillsModel.getSpringExp()
                        )
                );
            });

            return new ResponseEntity<>(employeeResponseList, HttpStatus.OK);
        } catch (Exception exception) {
            log.error("exception: {}", exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
