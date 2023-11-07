package isle.academy.healing_leaf.data;

public class StringsPackage {

    public static final String START_ISLE_TD = "Starting Isle Tower Defense, the best game of Steam.";

    // Logs
    // User Controller
    public static final String USER_CONTROLLER_GET_USER_CALLED = "User Controller -> getUser -> Called with steamId: {}";
    public static final String USER_CONTROLLER_GET_OTHER_USER_CALLED = "User Controller -> getOtherUser -> Called with steamId: {}";
    public static final String USER_CONTROLLER_LOGOUT_CALLED = "User Controller -> logout -> Called with steamId: {}";

    // User Service
    public static final String USER_SERVICE_START = "User Service -> Starting service...";
    public static final String USER_SERVICE_CREATE_USER = "User Service -> getUser -> creating user with steamId: {}";
    public static final String USER_SERVICE_RETRIEVING_USER = "User Service -> getUser -> retrieving user with steamId: {}";
    public static final String USER_SERVICE_RETRIEVING_OTHER_USER = "User Service -> getOtherUser -> retrieving user with steamId: {}";
    public static final String USER_SERVICE_LOGOUT_USER = "User Service -> logout -> logout user with steamId: {}";

    // Story Controller
    public static final String STORY_CONTROLLER_INCREMENT_STORY = "Story Controller -> nextStory -> Called with steamId: {}";
    public static final String STORY_CONTROLLER_RETRIEVE_STORY_POINT = "Story Controller -> retrieveStoryPoint -> Called with steamId: {} and storyPoint : {}";

    // Story Service
    public static final String STORY_SERVICE_START = "Story Service -> Starting service...";
    public static final String STORY_SERVICE_SETUP_STORY_REWARDS = "Story Service -> setupStoryRewards";
    public static final String STORY_SERVICE_INCREMENT_STORY = "Story Service -> nextStory -> User with steamId: {} is on story: {}";
    public static final String STORY_SERVICE_RETRIEVE_REPEATED_STORY = "Story Service -> retrieveStoryPoint -> User with steamId: {} already have story: {}";

    // Token Controller
    public static final String TOKEN_CONTROLLER_GET_TOKEN_CALLED = "Token Controller -> getToken";

    // Token Service
    public static final String TOKEN_SERVICE_GET_TOKEN_CALLED = "Token Service -> getAuthenticationToken -> retrieved jwt to user: {}";
    public static final String TOKEN_SERVICE_DECODE_TOKEN = "Token Service -> decodeAuthenticationToken -> decoding jwt: {}";
    public static final String TOKEN_SERVICE_START = "Token Service -> Starting service...";
    public static final String INITIALIZING_TOKEN_SERVICE = "Token Service -> setupJWTSignature";
    public static final String STEAM_NULL_RESPONSE_BODY = "Token Service -> RetrieveSteamId -> Steam returning null response body.";
    public static final String STEAM_NULL_RESPONSE = "Token Service -> RetrieveSteamId -> Steam returning null response.";
    public static final String STEAM_ERROR_WITH_DESCRIPTION = "Token Service -> RetrieveSteamId -> Steam error: {}, with description: {}.";
    public static final String STEAM_RETURNING_NULL_PARAMS = "Token Service -> RetrieveSteamId -> Steam returning null params.";

    // Lobby Controller
    public static final String LOBBY_CONTROLLER_CREATE_LOBBY = "Lobby Controller -> createLobby -> By user: {}";
    public static final String LOBBY_CONTROLLER_END_MATCH = "Lobby Controller -> endMatch -> By user: {}";

    // Lobby Service
    public static final String LOBBY_SERVICE_START = "Lobby Service -> Starting service...";
    public static final String LOBBY_SERVICE_SETUP_STAGE_DATA = "Lobby Service -> setupStageData";
    public static final String LOBBY_SERVICE_CREATE_LOBBY = "Lobby Service -> createLobby -> Lobby {} created.";
    public static final String LOBBY_SERVICE_END_LOBBY = "Lobby Service -> endLobby -> Lobby {} ended by user {}";

    // Item Controller
    public static final String ITEM_CONTROLLER_GET_STORE = "Item Controller -> getStore";
    public static final String ITEM_CONTROLLER_CRAFT_ITEM = "Item Controller -> craftItem -> {} by user: {}";

    // Item Service
    public static final String ITEM_SERVICE_SETUP_STORE = "Item Service -> setupStore";
    public static final String ITEM_SERVICE_CRAFT_ITEM = "Item Service -> craftItem -> item {} crafted by user: {}";


    // Responses
    public static final String MISSING_TOKEN = "Request is missing the Authorization token.";
    public static final String CANNOT_RETRIEVE_VALID_DATA_FROM_STEAM = "Could not retrieve valid data from steam.";

    // Errors
    public static final String COULD_NOT_AUTHENTICATE_WITH_STEAM = "Could not Authenticate with Steam.";
    public static final String COULD_NOT_AUTHENTICATE_TOKEN = "Could not Authenticate token.";
    public static final String EXPIRED_TOKEN = "The Authorization token has expired.";
    public static final String USER_NOT_FOUND = "Not found in db, user: ";
    public static final String LOBBY_NOT_FOUND = "Not found in db, lobby: ";
    public static final String ITEM_NOT_FOUND = "Not found in db, item: ";
    public static final String USER_NOT_ALLOWED_TO_END_LOBBY = "user not allowed to end lobby: ";
    public static final String LOBBY_ALREADY_ENDED = "lobby already ended: ";
    public static final String MAX_VALUE_FOR_STORY_POINT_ID = "The max value for storyPointId is: ";
    public static final String LOBBY_CREATOR_IN_LOBBY = "Lobby creator can't be in lobby.";
    public static final String LOBBY_ALREADY_EXISTS = "Lobby already exists with id: ";
    public static final String NOT_ENOUGH_GOLD = "User %s not enough gold to buy %s";
    public static final String NOT_ENOUGH_FEEDSTOCKS = "User %s not enough feedstocks to buy %s";
    public static final String USER_ALREADY_HAVE_CRAFT_ITEM = "User %s already have craft item: %s";

    // Exception Handling
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error -> ";
    public static final String AUTHENTICATION_EXCEPTION = "Authentication Exception -> {}";
    public static final String FORBIDDEN_EXCEPTION = "Forbidden Exception -> {}";
    public static final String REQUEST_VALIDATION_EXCEPTION = "Request Validation Exception -> {}";
    public static final String INVALID_REQUEST_EXCEPTION = "Invalid Request Exception -> {}";

    // Validations
    public static final String MUST_BE_AT_LEAST = "Must be at least ";
    public static final String MUST_BE_AT_MAX = "Must be at maximum ";

}
