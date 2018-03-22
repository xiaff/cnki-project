package cn.nsti.crawler.processor;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CnkiProjectPageProcessorTest {
    @Autowired
    private CnkiProjectPageProcessor cnkiProjectPageProcessor;

    @Test
    public void getPcParamFromUrl() throws Exception {
        String pc = CnkiProjectPageProcessor.getPcParamFromUrl("http://projects.cnki.net/DetailView.aspx?t=2&pc=571420001");
        System.out.println("pc = " + pc);
        Assert.assertEquals("571420001", pc);
    }

    @Test
    public void fetchAll() throws Exception {
        cnkiProjectPageProcessor.fetchAll();
    }
}