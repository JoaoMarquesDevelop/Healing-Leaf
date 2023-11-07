package isle.academy.healing_leaf.controllers;

import isle.academy.healing_leaf.controllers.generic.BaseApiV1Controller;
import isle.academy.healing_leaf.data.dto.items.CraftItemDTO;
import isle.academy.healing_leaf.data.dto.store.StoreDataResponseDTO;
import isle.academy.healing_leaf.services.contracts.IItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static isle.academy.healing_leaf.data.StringsPackage.ITEM_CONTROLLER_CRAFT_ITEM;
import static isle.academy.healing_leaf.data.StringsPackage.ITEM_CONTROLLER_GET_STORE;


@Slf4j
@RestController
public class ItemController extends BaseApiV1Controller {

    @Autowired
    private IItemService itemService;


    @GetMapping(path = "/items")
    public StoreDataResponseDTO getStore() {
        log.info(ITEM_CONTROLLER_GET_STORE);
        return itemService.getStore();
    }

    @PostMapping(path = "/craft")
    @PreAuthorize("permitAll()")
    public void craftItem(@RequestAttribute long steamId,
                          @Valid @RequestBody CraftItemDTO craftItemDTO) {
        log.info(ITEM_CONTROLLER_CRAFT_ITEM, craftItemDTO, steamId);
        itemService.craftItem(steamId, craftItemDTO);
    }
}
