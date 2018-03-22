package cn.nsti.crawler.processor;

import cn.nsti.crawler.dao.ProjectBriefRepo;
import cn.nsti.crawler.entity.ProjectBriefDO;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Cnki project list page
 * Created by Lu Chenwei on 2017/7/30.
 */
@Component
public class CnkiProjectListPageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(CnkiProjectListPageProcessor.class);
    private WebDriver webDriver;

    @Resource
    private ProjectBriefRepo projectBriefRepo;

    private void init() {
        webDriver = new ChromeDriver();
        webDriver.get("http://projects.cnki.net/approvalitem.aspx");
        new WebDriverWait(webDriver, 50)
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("table > thead > tr > th"), 0));
    }

    public boolean getProjectsOnOnePage(int page, String searchCategory) {
        if (webDriver == null) {
            init();
        }
        String url = "http://projects.cnki.net/Asynch/GetData.ashx?" +
                "id=ApprovalItem&sc=ApprovalItem&cf=%2FCoreResources%2FConfigFolder%2FListView%2FListView.xml" +
                "&ps=10&ac=Page&cp=" + page;
        webDriver.get(url);

        String pageSource = webDriver.getPageSource();
        Document document = Jsoup.parse(pageSource);
        Elements lineElements = document.select("table > tbody > tr");
        List<ProjectBriefDO> projectBriefList = new ArrayList<>();
        for (Element lineElement : lineElements) {
            ProjectBriefDO projectBrief = getProjectBriefFromLineElement(lineElement, searchCategory);
            if (projectBrief != null) {
                projectBriefList.add(projectBrief);
                logger.info("Add {}.", projectBrief);
            }
        }
        if (CollectionUtils.isNotEmpty(projectBriefList)) {
            projectBriefRepo.save(projectBriefList);
            return true;
        } else {
            return false;
        }
    }

    public void getListProjects(String searchCategory) throws InterruptedException {
        long num = projectBriefRepo.countBySearchCategory(searchCategory);
        int nextPage = 1;
        if (num > 0) {
            nextPage = 1 + (int) num / 10;
        }
        logger.info("Go to page #{}...", nextPage);
        while (true) {
            boolean hasMore = getProjectsOnOnePage(nextPage++, searchCategory);
            if (!hasMore) {
                break;
            }
            Thread.sleep(2 * 1000);
        }
    }
    private ProjectBriefDO getProjectBriefFromLineElement(Element lineElement, String searchCategory) {
        Elements tdElements = lineElement.select("td");
        if (tdElements.size() != 8) {
            return null;
        }
        ProjectBriefDO projectBrief = new ProjectBriefDO();
        projectBrief.setSearchCategory(searchCategory);
        String index = tdElements.get(0).text();
        projectBrief.setCnkiIndex(Long.parseLong(index));
        Element titleTd = tdElements.get(1);
        Elements titleElement = titleTd.select(">a");
        String title = titleElement.text();
        projectBrief.setTitle(title);
        String url = titleElement.attr("href");
        if (projectBriefRepo.findByUrl(url) != null) {
            return null;
        }
        projectBrief.setUrl("http://projects.cnki.net" + url);
        String source = tdElements.get(2).text();
        projectBrief.setSource(source);
        String orgAndLeader = tdElements.get(3).text();
        projectBrief.setOrgAndLeader(orgAndLeader);
        String fund = tdElements.get(4).text();
        projectBrief.setFund(BigDecimal.valueOf(Double.parseDouble(fund)));
        String result = tdElements.get(5).text();
        projectBrief.setResult(Integer.parseInt(result));
        String startYear = tdElements.get(6).text();
        projectBrief.setStartYear(Integer.parseInt(startYear));
        return projectBrief;
    }

    public static void main(String[] args) {
        CnkiProjectListPageProcessor processor = new CnkiProjectListPageProcessor();
        processor.getProjectsOnOnePage(1, "");
    }

}
