package at.technikum.springrestbackend.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * A utility class for mapping between Data Transfer Objects (DTOs) and entity classes
 * using the ModelMapper library.
 */
@Component
public class InternalModelMapper {

    private final ModelMapper modelMapper;

    /**
     * Constructs an InternalModelMapper with a ModelMapper instance.
     *
     * @param modelMapper The ModelMapper instance to use for mapping.
     */
    public InternalModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Maps an entity object to its corresponding Data Transfer Object (DTO).
     *
     * @param entity    The entity object to be mapped.
     * @param dtoClass  The class of the target DTO.
     * @param <T>       The type of the entity.
     * @param <U>       The type of the DTO.
     * @return The mapped DTO object.
     */
    public <T, U> U mapToDTO(T entity, Class<U> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    /**
     * Maps a Data Transfer Object (DTO) to its corresponding entity object.
     *
     * @param dto         The DTO object to be mapped.
     * @param entityClass The class of the target entity.
     * @param <T>         The type of the entity.
     * @param <U>         The type of the DTO.
     * @return The mapped entity object.
     */
    public <T, U> T mapToEntity(U dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
}

