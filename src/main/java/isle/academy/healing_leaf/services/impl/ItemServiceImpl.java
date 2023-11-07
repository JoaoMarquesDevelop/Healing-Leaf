package isle.academy.healing_leaf.services.impl;

import isle.academy.healing_leaf.data.dto.items.CraftItemDTO;
import isle.academy.healing_leaf.data.dto.items.GroupOfItemsBaseDTO;
import isle.academy.healing_leaf.data.dto.store.PriceResponseDTO;
import isle.academy.healing_leaf.data.dto.store.StoreDataResponseDTO;
import isle.academy.healing_leaf.data.entity.user.UserEntity;
import isle.academy.healing_leaf.data.enums.CraftItemType;
import isle.academy.healing_leaf.data.repository.UserRepository;
import isle.academy.healing_leaf.exceptions.EntityNotFoundException;
import isle.academy.healing_leaf.exceptions.InvalidRequestException;
import isle.academy.healing_leaf.helpers.Converters;
import isle.academy.healing_leaf.helpers.JsonDataRetriever;
import isle.academy.healing_leaf.services.contracts.IItemService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static isle.academy.healing_leaf.data.StringsPackage.*;

@Slf4j
@Service
public class ItemServiceImpl implements IItemService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    private static final String TOWERS_ON_STORE_PATH = "/data/Towers.json";
    private static final String BLOCKS_ON_STORE_PATH = "/data/Blocks.json";
    private static final String TRAPS_ON_STORE_PATH = "/data/Traps.json";

    private StoreDataResponseDTO store;

    private Map<Integer, PriceResponseDTO> towers;
    private Map<Integer, PriceResponseDTO> blocks;
    private Map<Integer, PriceResponseDTO> traps;

    public ItemServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setupStore() {

        PriceResponseDTO[] towersArray = JsonDataRetriever.retrieveFromJson(TOWERS_ON_STORE_PATH, PriceResponseDTO[].class);
        PriceResponseDTO[] blocksArray = JsonDataRetriever.retrieveFromJson(BLOCKS_ON_STORE_PATH, PriceResponseDTO[].class);
        PriceResponseDTO[] trapsArray = JsonDataRetriever.retrieveFromJson(TRAPS_ON_STORE_PATH, PriceResponseDTO[].class);

        store = StoreDataResponseDTO
                .builder()
                .towers(towersArray)
                .blocks(blocksArray)
                .traps(trapsArray)
                .build();

        towers = Converters.convertPriceResponseArrayToHashmap(towersArray);
        blocks = Converters.convertPriceResponseArrayToHashmap(blocksArray);
        traps = Converters.convertPriceResponseArrayToHashmap(trapsArray);

        log.info(ITEM_SERVICE_SETUP_STORE);
    }

    @Override
    public StoreDataResponseDTO getStore() {
        return store;
    }

    @Override
    public void craftItem(long steamId, CraftItemDTO craftItemDTO) {

        PriceResponseDTO item = getPriceByTypeAndId(craftItemDTO.getId(), craftItemDTO.getType());

        Optional<UserEntity> dbUser = userRepository.findById(steamId);

        if (dbUser.isEmpty()) {
            throw new EntityNotFoundException(USER_NOT_FOUND + steamId);
        }

        if (dbUser.get().getGold() < item.getGold()) {
            throw new InvalidRequestException(String.format(NOT_ENOUGH_GOLD, steamId, item));
        }

        if (!dbUser.get().hasEnoughFeedstocks(item.getFeedstocks())) {
            throw new InvalidRequestException(String.format(NOT_ENOUGH_FEEDSTOCKS, steamId, item));
        }

        if (dbUser.get().hasCraftItem(craftItemDTO)) {
            throw new InvalidRequestException(String.format(USER_ALREADY_HAVE_CRAFT_ITEM, steamId, item));
        }

        GroupOfItemsBaseDTO groupOfItemsBaseDTO = mapper.map(item, GroupOfItemsBaseDTO.class);
        groupOfItemsBaseDTO.setCraftItems(Set.of(craftItemDTO));

        dbUser.get().giveReward(groupOfItemsBaseDTO, mapper, true);

        log.info(ITEM_SERVICE_CRAFT_ITEM, item, steamId);

        userRepository.save(dbUser.get());
    }

    private PriceResponseDTO getPriceByTypeAndId(Integer id, CraftItemType type) {

        if (id == null) {
            throw new EntityNotFoundException(ITEM_NOT_FOUND + "null");
        }

        PriceResponseDTO item = null;

        switch (type) {
            case TOWER -> {
                item = towers.get(id);
            }
            case BLOCK -> {
                item = blocks.get(id);
            }
            case TRAP -> {
                item = traps.get(id);
            }
        }

        if (item == null) {
            throw new EntityNotFoundException(ITEM_NOT_FOUND + type + " " + id);
        }

        return item;
    }

}
