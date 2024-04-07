package ai.hajimebot.global;

public class LogEventUtils {

    /**
     * device_online
     */
    public static String DEVICE_ONLINE = "device_online";
    /**
     * device_offline
     */
    public static String DEVICE_OFFLINE = "device_offline";
    /**
     * p2p_connected
     */
    public static String P2P_CONNECTED = "p2p_connected";
    /**
     * model_online
     */
    public static String MODEL_ONLINE = "model_online";
    /**
     * Upload a PDF (txt) file that is successfully distributed to some devices
     */
    public static String SEND_FILE_TO_NODE = "send_file_to_node";
    /**
     * PDF (txt) file, the storage is successful
     */
    public static String FILE_EMBEDDING = "file_embedding";
    /**
     * Speech parsing
     */
    public static String DEVICE_ASR = "asr";
    /**
     * Vector library query requests
     */
    public static String QUERY_VECTOR_DB = "query_vector_db";
    /**
     * The vector library responds to the request
     */
    public static String VECTOR_DB_RESPONSE = "vector_db_response";
    /**
     * Merge vectors, and the request for the large model succeeds
     */
    public static String LLM_SERVICE = "llm_service";
    /**
     * The large model returns content text-to-speech successfully
     */
    public static String DEVICE_TTS = "tts";
    /**
     * Evidence is returned
     */
    public static String EVIDENCE = "evidence";
}
