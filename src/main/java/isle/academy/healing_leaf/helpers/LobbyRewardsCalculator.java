package isle.academy.healing_leaf.helpers;

import isle.academy.healing_leaf.data.dto.lobby.data.CreatureDropData;
import isle.academy.healing_leaf.data.dto.lobby.data.StageData;
import isle.academy.healing_leaf.data.dto.lobby.response.CreatureRewardsResponseDTO;
import isle.academy.healing_leaf.data.dto.lobby.response.WaveRewardsResponseDTO;
import isle.academy.healing_leaf.data.entity.lobby.LobbyEntity;
import isle.academy.healing_leaf.data.entity.lobby.LobbyFeedstockEarningEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LobbyRewardsCalculator {

    public static List<WaveRewardsResponseDTO> CalculateLobbyDrops(StageData stage, int difficulty, LobbyEntity lobby, float chanceMultiplier) {

        StageData.WaveData[] waves = stage.getWaves();
        List<WaveRewardsResponseDTO> wavesDrops = new ArrayList<>();

        int possibleGoldEarning = 0;
        int possibleXpEarned = 0;
        List<LobbyFeedstockEarningEntity> possibleFeedstockEarnings = new ArrayList<>();

        for (StageData.WaveData wave : waves) {
            WaveRewardsResponseDTO waveDrops = new WaveRewardsResponseDTO();
            StageData.WaveData.CreatureQuantity[] waveCreatures = wave.getCreatures();

            for (StageData.WaveData.CreatureQuantity creatureInWave : waveCreatures) {

                for (int k = 0; k < creatureInWave.getQuantity(); k++) {

                    ArrayList<Integer> drops = new ArrayList<>();
                    CreatureDropData creatureDropData = creatureInWave.getCreatureDropData();
                    long goldGambled = gambleValue(creatureDropData.getGoldMin(), creatureDropData.getGoldMax());
                    long xpGambled = gambleValue(creatureDropData.getGoldMin(), creatureDropData.getGoldMax());

                    for (CreatureDropData.DropData dropData : creatureDropData.getDrops()) {
                        float chance = dropData.GetChanceByDifficulty(difficulty) * chanceMultiplier;
                        if (gambleDrop(chance)) {
                            drops.add(dropData.getFeedstock());
                            insertQuantityToEarning(possibleFeedstockEarnings, dropData.getFeedstock(), lobby);

                        }
                    }

                    CreatureRewardsResponseDTO clientDrop = new CreatureRewardsResponseDTO(creatureInWave.getId(), drops, goldGambled, xpGambled);
                    possibleGoldEarning += goldGambled;
                    possibleXpEarned += xpGambled;
                    waveDrops.getRewards().add(clientDrop);
                }
            }
            wavesDrops.add(waveDrops);
        }

        lobby.setEarnings(possibleFeedstockEarnings, possibleGoldEarning, possibleXpEarned);
        return wavesDrops;
    }


    private static void insertQuantityToEarning(List<LobbyFeedstockEarningEntity> currentList, int feedstockId, LobbyEntity lobby) {
        for (LobbyFeedstockEarningEntity lobbyFeedstockEarningEntity : currentList) {
            if (lobbyFeedstockEarningEntity.getIdentifier() == feedstockId) {
                lobbyFeedstockEarningEntity.insert(1);
                return;
            }
        }

        LobbyFeedstockEarningEntity newFeedstock = LobbyFeedstockEarningEntity
                .builder()
                .identifier(feedstockId)
                .quantity(1)
                .lobby(lobby)
                .build();

        currentList.add(newFeedstock);
    }

    private static boolean gambleDrop(float dropChance) {
        Random random = new Random();
        return random.nextFloat() <= dropChance / 100;
    }

    private static long gambleValue(long min, long max) {
        if (max <= min) {
            return min;
        }
        Random random = new Random();
        return random.nextLong(max - min) + min;
    }
}
