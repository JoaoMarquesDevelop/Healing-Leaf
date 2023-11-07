package isle.academy.healing_leaf.data.mapper;

import org.modelmapper.ModelMapper;

public abstract class BaseMapperConfiguration {

    protected final ModelMapper mapper;

    protected BaseMapperConfiguration(ModelMapper mapper) {
        this.mapper = mapper;

        createTypeMappers();
    }

    protected abstract void createTypeMappers();
}