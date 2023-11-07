package isle.academy.healing_leaf.data.mapper.items;

import isle.academy.healing_leaf.data.dto.items.CraftItemDTO;
import isle.academy.healing_leaf.data.dto.items.GroupOfItemsBaseDTO;
import isle.academy.healing_leaf.data.dto.store.PriceResponseDTO;
import isle.academy.healing_leaf.data.entity.user.CraftItemEntity;
import isle.academy.healing_leaf.data.mapper.BaseMapperConfiguration;
import isle.academy.healing_leaf.helpers.TimeHelper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class CraftItemMapperConfiguration extends BaseMapperConfiguration {

    @Autowired
    public CraftItemMapperConfiguration(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    protected void createTypeMappers() {

        Converter<CraftItemEntity, CraftItemDTO> craftItemResponseConverter = context -> {
            CraftItemEntity craftItemEntity = context.getSource();

            return CraftItemDTO.builder()
                    .id(craftItemEntity.getIdentifier())
                    .type(craftItemEntity.getType())
                    .build();
        };

        mapper.addConverter(craftItemResponseConverter, CraftItemEntity.class, CraftItemDTO.class);

        Converter<CraftItemDTO, CraftItemEntity> craftItemEntityConverter = context -> {
            CraftItemDTO craftItemDTO = context.getSource();

            return CraftItemEntity.builder()
                    .identifier(craftItemDTO.getId())
                    .type(craftItemDTO.getType())
                    .acquiredDate(TimeHelper.getCurrentTimeInIsleFormat())
                    .build();
        };

        mapper.addConverter(craftItemEntityConverter, CraftItemDTO.class, CraftItemEntity.class);

        Converter<PriceResponseDTO, GroupOfItemsBaseDTO> groupOfItemsConverter = context -> {

            PriceResponseDTO price = context.getSource();

            return GroupOfItemsBaseDTO.builder()
                    .feedstocks(price.getFeedstocks())
                    .gold(price.getGold())
                    .build();
        };

        mapper.addConverter(groupOfItemsConverter, PriceResponseDTO.class, GroupOfItemsBaseDTO.class);
    }
}
