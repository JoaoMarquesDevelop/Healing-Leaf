package isle.academy.healing_leaf.services.impl;

import isle.academy.healing_leaf.data.dto.user.OtherUserResponseDTO;
import isle.academy.healing_leaf.data.dto.user.UserResponseDTO;
import isle.academy.healing_leaf.data.dto.user.request.LogoutRequestDTO;
import isle.academy.healing_leaf.data.entity.user.UserEntity;
import isle.academy.healing_leaf.data.repository.UserRepository;
import isle.academy.healing_leaf.exceptions.EntityNotFoundException;
import isle.academy.healing_leaf.services.contracts.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static isle.academy.healing_leaf.data.StringsPackage.*;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final Environment environment;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           Environment environment,
                           ModelMapper mapper) {
        this.userRepository = userRepository;
        this.environment = environment;
        this.mapper = mapper;
        log.info(USER_SERVICE_START);
    }

    @Override
    public UserResponseDTO getUser(Long steamId) {
        Optional<UserEntity> dbUser = userRepository.findById(steamId);

        if (dbUser.isEmpty()) {
            log.info(USER_SERVICE_CREATE_USER, steamId);
            return mapper.map(CreateNewUser(steamId), UserResponseDTO.class);
        }

        log.info(USER_SERVICE_RETRIEVING_USER, steamId);

        return mapper.map(dbUser.get(), UserResponseDTO.class);
    }

    @Override
    public OtherUserResponseDTO getOtherUser(Long steamId) {
        Optional<UserEntity> dbUser = userRepository.findById(steamId);

        if (dbUser.isEmpty()) {
            throw new EntityNotFoundException(USER_NOT_FOUND);
        }

        log.info(USER_SERVICE_RETRIEVING_OTHER_USER, steamId);

        return mapper.map(dbUser.get(), OtherUserResponseDTO.class);
    }

    @Override
    public void logoutRequest(Long steamId, LogoutRequestDTO logoutRequestDTO) {
        Optional<UserEntity> dbUser = userRepository.findById(steamId);

        if (dbUser.isEmpty()) {
            throw new EntityNotFoundException(USER_NOT_FOUND + " " + steamId);
        }

        dbUser.get().setLogout(logoutRequestDTO);
        userRepository.save(dbUser.get());

        log.info(USER_SERVICE_LOGOUT_USER, steamId);
    }

    private UserEntity CreateNewUser(long steamId) {
        float x = Float.parseFloat(environment.getProperty("isle.userDefaults.position.x", "0"));
        float y = Float.parseFloat(environment.getProperty("isle.userDefaults.position.y", "0"));
        float z = Float.parseFloat(environment.getProperty("isle.userDefaults.position.z", "0"));
        int soundtrackVolume = Integer.parseInt(environment.getProperty("isle.userDefaults.volume.soundtrack", "5"));
        int uiVolume = Integer.parseInt(environment.getProperty("isle.userDefaults.volume.ui", "5"));
        int fightVolume = Integer.parseInt(environment.getProperty("isle.userDefaults.volume.fight", "5"));
        int currentIsland = Integer.parseInt(environment.getProperty("isle.userDefaults.currentIsland", "1"));

        UserEntity user = UserEntity.builder()
                .steamId(steamId)
                .positionX(x)
                .positionY(y)
                .positionZ(z)
                .soundtrackVolume(soundtrackVolume)
                .uiVolume(uiVolume)
                .fightVolume(fightVolume)
                .currentIsland(currentIsland)
                .build();

        userRepository.save(user);

        return user;
    }

}
