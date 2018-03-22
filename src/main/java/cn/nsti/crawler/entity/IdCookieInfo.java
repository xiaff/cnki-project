package cn.nsti.crawler.entity;

import java.util.Map;

/**
 * 访问CNKI项目页面得到的Cookie和id
 * Created by Lu Chenwei on 2017/8/2.
 */
public class IdCookieInfo {
    private Long id;

    private Map<String, String> cookies;

    public IdCookieInfo() {
    }

    public IdCookieInfo(Long id, Map<String, String> cookies) {
        this.id = id;
        this.cookies = cookies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    @Override
    public String toString() {
        return "IdCookieInfo{" +
                "id=" + id +
                ", cookies=" + cookies +
                '}';
    }
}
