//package isle.academy.healing_leaf;
//
//import isle.academy.healing_leaf.data.dto.items.FeedstockDTO;
//import isle.academy.healing_leaf.data.dto.items.GroupOfItemsBaseDTO;
//import isle.academy.healing_leaf.data.entity.user.FeedstockEntity;
//import isle.academy.healing_leaf.data.entity.user.UserEntity;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class CraftItemTests {
//
//    private ModelMapper modelMapper;
//
//    @Test
//    public void evaluatesExpression() {
//        UserEntity userEntity = new UserEntity();
//
//        FeedstockDTO feedstock1 = FeedstockDTO.builder()
//                .id(1)
//                .quantity(6)
//                .build();
//
//        FeedstockDTO feedstock2 = FeedstockDTO.builder()
//                .id(2)
//                .quantity(10)
//                .build();
//
//        GroupOfItemsBaseDTO reward1 = GroupOfItemsBaseDTO.builder()
//                .gold(100)
//                .feedstocks(List.of(feedstock1, feedstock2))
//                .build();
//
//        userEntity.giveReward(reward1, modelMapper, false);
//
//        assertEquals(6, userEntity.getFeedstocks().get(0).getQuantity());
//    }
//}
