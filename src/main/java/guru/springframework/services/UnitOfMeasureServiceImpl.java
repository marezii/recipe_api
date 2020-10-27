package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@AllArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    @Override
    @Transactional
    public Set<UnitOfMeasureCommand> listAllUoms() {

        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public UnitOfMeasureCommand createUom(UnitOfMeasureCommand uom) {
        UnitOfMeasure convertedUOM = unitOfMeasureCommandToUnitOfMeasure.convert(uom);
        UnitOfMeasure savedUOM = unitOfMeasureRepository.save(convertedUOM);
        return unitOfMeasureToUnitOfMeasureCommand.convert(savedUOM);
    }

    @Override
    @Transactional
    public UnitOfMeasureCommand updateUom(Long id, UnitOfMeasureCommand uom) {
        UnitOfMeasure foundUOM = unitOfMeasureCommandToUnitOfMeasure.convert(uom);
        foundUOM.setId(id);
        UnitOfMeasure savedUOM = unitOfMeasureRepository.save(foundUOM);
        return unitOfMeasureToUnitOfMeasureCommand.convert(savedUOM);

    }

    @Override
    @Transactional
    public void deleteUom(Long id) {
        unitOfMeasureRepository.deleteById(id);
    }
}
