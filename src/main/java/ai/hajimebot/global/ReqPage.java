package ai.hajimebot.global;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqPage<T> {
    /**
     * Current page
     */
    private long pageNumber = 1;
    /**
     * page Size
     */
    private long pageSize = 10;
    /**
     * Query criteria
     */
    private T data;
}
