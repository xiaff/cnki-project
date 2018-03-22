package cn.nsti.crawler.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by Lu Chenwei on 2017/7/30.
 */
@Entity
@Table(name = "project_brief")
public class ProjectBriefDO {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String url;
    private String source;
    private String orgAndLeader;
    private BigDecimal fund;
    private Integer result;
    private Integer startYear;
    private Long cnkiIndex;
    private String searchCategory;

    public ProjectBriefDO() {
    }

    public ProjectBriefDO(String title, String url, String source, String orgAndLeader, BigDecimal fund, Integer result, Integer startYear, Long cnkiIndex, String searchCategory) {
        this.title = title;
        this.url = url;
        this.source = source;
        this.orgAndLeader = orgAndLeader;
        this.fund = fund;
        this.result = result;
        this.startYear = startYear;
        this.cnkiIndex = cnkiIndex;
        this.searchCategory = searchCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOrgAndLeader() {
        return orgAndLeader;
    }

    public void setOrgAndLeader(String orgAndLeader) {
        this.orgAndLeader = orgAndLeader;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Long getCnkiIndex() {
        return cnkiIndex;
    }

    public void setCnkiIndex(Long cnkiIndex) {
        this.cnkiIndex = cnkiIndex;
    }

    public String getSearchCategory() {
        return searchCategory;
    }

    public void setSearchCategory(String searchCategory) {
        this.searchCategory = searchCategory;
    }

    @Override
    public String toString() {
        return "ProjectBriefDO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", source='" + source + '\'' +
                ", orgAndLeader='" + orgAndLeader + '\'' +
                ", fund=" + fund +
                ", result=" + result +
                ", startYear=" + startYear +
                ", cnkiIndex=" + cnkiIndex +
                ", searchCategory='" + searchCategory + '\'' +
                '}';
    }
}
