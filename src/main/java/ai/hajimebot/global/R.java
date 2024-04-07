package ai.hajimebot.global;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Respond to the subject of information
 *
 * @author Lion Li
 */
@Data
@NoArgsConstructor
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * success
     */
    public static final int SUCCESS = 200;

    /**
     * fail
     */
    public static final int FAIL = 500;

    private int code;

    private String msg;

    private T data;

    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, "Operation Success");
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, "Operation Success");
    }

    public static <T> R<T> ok(String msg) {
        return restResult(null, SUCCESS, msg);
    }

    public static <T> R<T> ok(String msg, T data) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> fail() {
        return restResult(null, FAIL, "Operation failed");
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, FAIL, "Operation failed");
    }

    public static <T> R<T> fail(String msg, T data) {
        return restResult(data, FAIL, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    /**
     * A warning message is returned
     *
     * @param msg Return to content
     * @return Warning messages
     */
    public static <T> R<T> warn(String msg) {
        return restResult(null, HttpStatus.WARN, msg);
    }

    /**
     * A warning message is returned
     *
     * @param msg Return to content
     * @param data data
     * @return Warning messages
     */
    public static <T> R<T> warn(String msg, T data) {
        return restResult(data, HttpStatus.WARN, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }

    public static <T> Boolean isError(R<T> ret) {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(R<T> ret) {
        return R.SUCCESS == ret.getCode();
    }
}
