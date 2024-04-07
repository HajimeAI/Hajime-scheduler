package ai.hajimebot.global;

/**
 * Returns the status code
 */
public interface HttpStatus {
    /**
     * The operation was successful
     */
    int SUCCESS = 200;

    /**
     * The object was created successfully
     */
    int CREATED = 201;

    /**
     * The request has been accepted
     */
    int ACCEPTED = 202;

    /**
     * The operation has been executed successfully, but no data has been returned
     */
    int NO_CONTENT = 204;

    /**
     * Resources have been removed
     */
    int MOVED_PERM = 301;

    /**
     * redirect
     */
    int SEE_OTHER = 303;

    /**
     * The resource has not been modified
     */
    int NOT_MODIFIED = 304;

    /**
     * Wrong parameter list (missing, format mismatch)
     */
    int BAD_REQUEST = 400;

    /**
     * Unauthorized
     */
    int UNAUTHORIZED = 401;

    /**
     * Access is restricted, and the license expires
     */
    int FORBIDDEN = 403;

    /**
     * Resources, services not found
     */
    int NOT_FOUND = 404;

    /**
     * HTTP methods are not allowed
     */
    int BAD_METHOD = 405;

    /**
     * Resource conflicts, or resources are locked
     */
    int CONFLICT = 409;

    /**
     * Unsupported data, media types
     */
    int UNSUPPORTED_TYPE = 415;

    /**
     * System internal errors
     */
    int ERROR = 500;

    /**
     * The interface is not implemented
     */
    int NOT_IMPLEMENTED = 501;

    /**
     * System warning message
     */
    int WARN = 601;
}