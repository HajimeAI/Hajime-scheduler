package ai.hajimebot.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import java.util.Date;

/**
 * {@code description}
 * {@code author} xc.deng
 * {@code create} 2024/03/06 11:09
 */
public class P6spySqlFormatConfig implements MessageFormattingStrategy {
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return StrUtil.isNotBlank(sql) ? DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss")
                + " | Take " + elapsed + " ms | SQL statement：" + StrUtil.LF + sql.replaceAll("[\\s]+", StrUtil.SPACE) + ";" : "";

    }
}
