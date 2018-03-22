package cn.nsti.crawler.dao;

import cn.nsti.crawler.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Project Repository
 * Created by Lu Chenwei on 2017/8/5.
 */
public interface ProjectRepo extends JpaRepository<Project, Long> {

    Project findByUrl(String url);
}
