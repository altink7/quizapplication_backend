package at.technikum.springrestbackend.config.mapper;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class InternalModelMapperTest {
    private InternalModelMapper internalModelMapper;

    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        ModelMapperConfig config = new ModelMapperConfig();
        modelMapper = Mockito.mock(config.modelMapper().getClass());
        internalModelMapper = new InternalModelMapper(modelMapper);
    }

    @Test
    public void testMapToDTO() {
        ClassA entity = new ClassA(1, "Max");
        ClassB dto = new ClassB(1, "Muster");

        when(modelMapper.map(entity, ClassB.class)).thenReturn(dto);

        ClassB resultDto = internalModelMapper.mapToDTO(entity, ClassB.class);

        assertThat(resultDto, is(dto));
    }

    @Test
    public void testMapToEntity() {
        ClassA entity = new ClassA(1, "Max");
        ClassB dto = new ClassB(1, "Muster");

        when(modelMapper.map(dto, ClassA.class)).thenReturn(entity);
        
        ClassA resultEntity = internalModelMapper.mapToEntity(dto, ClassA.class);
        
        assertThat(resultEntity, is(entity));
    }
    
    @AllArgsConstructor
    static class ClassA {
        int id;
        String name;
    }

    @AllArgsConstructor
    static class ClassB {
        int id;
        String name;
    }
}
