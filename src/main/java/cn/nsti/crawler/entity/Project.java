package cn.nsti.crawler.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Project info from CNKI
 * Created by Lu Chenwei on 2017/7/30.
 */
@Entity
@Table(name = "cnki_project")
public class Project {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 500)
    private String title; // 项目名称

    @Column(length = 25)
    private String number; //项目编号

    @Column(length = 500)
    private String leaders; // 负责人

    @Column(length = 500)
    private String organizations; // 承担单位

    @Column(length = 20)
    private String fund; // 项目经费

    @Column(length = 500)
    private String source; // 项目来源

    @Column(length = 20)
    private String startYear; // 立项时间

    @Column(length = 20)
    private String endYear; // 完成时间

    @Column(length = 100)
    private String subject; // 所属学科

    @Column(length = 500)
    private String keyword; // 关键词

    @Column(length = 100)
    private String url;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;

    public Project() {
    }

    public Project(String title, String number, String leaders, String organizations, String fund, String source, String startYear, String endYear, String subject, String keyword, String url, Date createTime, Date updateTime) {
        this.title = title;
        this.number = number;
        this.leaders = leaders;
        this.organizations = organizations;
        this.fund = fund;
        this.source = source;
        this.startYear = startYear;
        this.endYear = endYear;
        this.subject = subject;
        this.keyword = keyword;
        this.url = url;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLeaders() {
        return leaders;
    }

    public void setLeaders(String leaders) {
        this.leaders = leaders;
    }

    public String getOrganizations() {
        return organizations;
    }

    public void setOrganizations(String organizations) {
        this.organizations = organizations;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", number='" + number + '\'' +
                ", leaders='" + leaders + '\'' +
                ", organizations='" + organizations + '\'' +
                ", fund='" + fund + '\'' +
                ", source='" + source + '\'' +
                ", startYear='" + startYear + '\'' +
                ", endYear='" + endYear + '\'' +
                ", subject='" + subject + '\'' +
                ", keyword='" + keyword + '\'' +
                ", url='" + url + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
