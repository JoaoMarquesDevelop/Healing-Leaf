package isle.academy.healing_leaf.data.mapper.items;

import isle.academy.healing_leaf.data.dto.items.FeedstockDTO;
import isle.academy.healing_leaf.data.entity.user.FeedstockEntity;
import isle.academy.healing_leaf.data.mapper.BaseMapperConfiguration;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeedstockMapperConfiguration extends BaseMapperConfiguration {

    @Autowired
    public FeedstockMapperConfiguration(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void createTypeMappers() {

        Converter<FeedstockEntity, FeedstockDTO> feedstockResponseConverter = context -> {
            FeedstockEntity feedstockEntity = context.getSource();

            return FeedstockDTO.builder()
                    .id(feedstockEntity.getIdentifier())
                    .quantity(feedstockEntity.getQuantity())
                    .build();
        };

        mapper.addConverter(feedstockResponseConverter, FeedstockEntity.class, FeedstockDTO.class);

        Converter<FeedstockDTO, FeedstockEntity> feedstockEntityConverter = context -> {
            FeedstockDTO feedstockDTO = context.getSource();

            return FeedstockEntity.builder()
                    .identifier(feedstockDTO.getId())
                    .quantity(feedstockDTO.getQuantity())
                    .build();
        };

        mapper.addConverter(feedstockEntityConverter, FeedstockDTO.class, FeedstockEntity.class);

    }
}
