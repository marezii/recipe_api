package guru.springframework.controllers;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.UnitOfMeasureService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/uoms")
@AllArgsConstructor
public class UnitOfMeasureController {

    private final UnitOfMeasureService unitOfMeasureService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('UOM_READ')")
    public Set<UnitOfMeasureCommand> getAllUoms() {
        return unitOfMeasureService.listAllUoms();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('UOM_WRITE')")
    public UnitOfMeasureCommand createUnitOfMeasure(@RequestBody UnitOfMeasureCommand unitOfMeasureCommand) {
        return unitOfMeasureService.createUom(unitOfMeasureCommand);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('UOM_UPDATE')")
    public UnitOfMeasureCommand updateUnitOfMeasure(@PathVariable Long id, @RequestBody UnitOfMeasureCommand unitOfMeasureCommand) {
        return unitOfMeasureService.updateUom(id, unitOfMeasureCommand);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUnitOfMeasure(@PathVariable Long id) {
        unitOfMeasureService.deleteUom(id);
    }

}
