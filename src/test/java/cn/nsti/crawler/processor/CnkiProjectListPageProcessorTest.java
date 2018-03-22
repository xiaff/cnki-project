package cn.nsti.crawler.processor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CnkiProjectListPageProcessorTest {
    @Autowired
    private CnkiProjectListPageProcessor processor;

    @Test
    public void getListProjects() throws Exception {
        processor.getListProjects("Other");
    }
}