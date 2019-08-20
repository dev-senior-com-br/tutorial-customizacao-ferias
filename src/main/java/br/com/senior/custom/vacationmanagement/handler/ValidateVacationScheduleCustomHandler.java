package br.com.senior.custom.vacationmanagement.handler;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.senior.custom.vacationmanagement.validation.ValidateVacationScheduleInput;
import br.com.senior.custom.vacationmanagement.validation.ValidateVacationScheduleOutput;
import br.com.senior.custom.vacationmanagement.validation.ValidateVacationScheduleRequest;
import br.com.senior.custom.vacationmanagement.validation.ValidatorService;

@RestController
public class ValidateVacationScheduleCustomHandler {

    @Inject
    private ValidatorService validatorService;

    @RequestMapping("/")
    public String index() {
        return "O aplicativo de customização de férias está Online!";
    }

    /**
     * Responsável por receber os dados da solicitação de férias (input e output da validação original) e retornar um novo output.
     * 
     * @param request DTO com o input e o output da validação original
     * @return Novo output que será retornado pela primitiva "validateVacationSchedule"
     */
    @PostMapping(path = "/validateVacationSchedule")
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidateVacationScheduleOutput> customValidateVacationSchedule(@RequestBody ValidateVacationScheduleRequest request) {
        ValidateVacationScheduleInput input = request.input;
        ValidateVacationScheduleOutput output = request.output;

        System.out.println("customValidateVacationSchedule");

        input.vacationSchedules.forEach(vacationSchedule -> {
            System.out.println(String.format("validating employeeId: %s", vacationSchedule.employeeId));
            validatorService.validate(output, vacationSchedule);
        });

        return ResponseEntity.ok(output);
    }

}
