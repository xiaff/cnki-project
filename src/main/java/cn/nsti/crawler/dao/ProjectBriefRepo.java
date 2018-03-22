package cn.nsti.crawler.dao;

import cn.nsti.crawler.entity.ProjectBriefDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Project brief info Repository
 * Created by Lu Chenwei on 2017/7/30.
 */
public interface ProjectBriefRepo extends JpaRepository<ProjectBriefDO, Long> {
    ProjectBriefDO findByUrl(String url);

    long count();

    long countBySearchCategory(String searchCategory);

    @Query("select distinct p.url from ProjectBriefDO p")
    List<String> findDistinctUrl();
}
