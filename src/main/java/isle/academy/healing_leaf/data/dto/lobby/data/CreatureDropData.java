package isle.academy.healing_leaf.data.dto.lobby.data;


import lombok.Data;

@Data
public class CreatureDropData {

    private int id;

    private String name;

    private DropData[] drops;

    private long goldMin;

    private long goldMax;

    /**
     * chances are in %
     */
    @Data
    public static class DropData {

        private int id;

        private int feedstock;

        private float chance1;

        private float chance2;

        private float chance3;

        public float GetChanceByDifficulty(int difficulty){
            return switch (difficulty) {
                case 1 -> chance1;
                case 2 -> chance2;
                case 3 -> chance3;
                default -> 0;
            };
        }
    }
}


