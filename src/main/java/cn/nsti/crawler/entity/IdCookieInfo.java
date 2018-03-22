package cn.nsti.crawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 访问CNKI项目页面得到的Cookie和id
 * Created by Lu Chenwei on 2017/8/2.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdCookieInfo {
    private Long id;

    private Map<String, String> cookies;
}
