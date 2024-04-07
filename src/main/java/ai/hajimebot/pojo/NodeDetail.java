package ai.hajimebot.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NodeDetail {
    private String imei;

    private HwInfo hw_info;

    private String compute_cap_level;

    private ComputeCap compute_cap;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HwInfo {
        private String cpu;
        private String mem_size;
        private String mem_pcnt;
        private String hd_size;
        private String hd_pcnt;
        private String gpu_name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class ComputeCap {
        private boolean stt;
        private boolean tts;
        private boolean llm;
        private List<String> ai_modules;
    }
}
