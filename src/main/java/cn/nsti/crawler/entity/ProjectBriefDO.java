package cn.nsti.crawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by Lu Chenwei on 2017/7/30.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
