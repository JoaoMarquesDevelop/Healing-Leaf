package isle.academy.healing_leaf.data.dto.lobby.data;

import lombok.Data;

@Data
public class StageData {

    private int id;
    private String name;
    private WaveData[] waves;

    @Data
    public static class WaveData {
        private int index;
        private CreatureQuantity[] creatures;

        @Data
        public static class CreatureQuantity {
            private int id;
            private int quantity;
            private CreatureDropData creatureDropData;
        }
    }

    public void SetCreatures(CreatureDropData[] creaturesDropsData) {
        for (WaveData waveData : waves) {
            for (WaveData.CreatureQuantity creatureQuantity : waveData.creatures) {
                CreatureDropData creatureDropData = creaturesDropsData[creatureQuantity.id - 1];
                creatureQuantity.setCreatureDropData(creatureDropData);
            }
        }
    }
}
