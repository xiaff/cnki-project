package cn.nsti.crawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Project info from CNKI
 * Created by Lu Chenwei on 2017/7/30.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
