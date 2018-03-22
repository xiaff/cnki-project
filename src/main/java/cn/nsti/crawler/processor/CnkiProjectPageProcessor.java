package cn.nsti.crawler.processor;

import cn.nsti.crawler.dao.ProjectBriefRepo;
import cn.nsti.crawler.dao.ProjectRepo;
import cn.nsti.crawler.entity.Project;
import org.apache.commons.lang3.StringUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 知网项目详细页面
 * Created by Lu Chenwei on 2017/8/5.
 */
@Component
public class CnkiProjectPageProcessor {
    public static final String BASE_URL = "http://projects.cnki.net/Asynch/DetailView.ashx?ac=item&t=2&pc=#{pc}";
    public static final Logger logger = LoggerFactory.getLogger(CnkiProjectPageProcessor.class);
    @Resource
    private ProjectRepo projectRepo;
    @Resource
    private ProjectBriefRepo projectBriefRepo;

    private WebDriver[] webDrivers = new WebDriver[1];

    public void init() {
        for (int i = 0; i < webDrivers.length; i++) {
            WebDriver webDriver = new ChromeDriver();
            webDriver.get("http://projects.cnki.net/DetailView.aspx?t=2&pc=571420001");
            WebDriverWait wait = new WebDriverWait(webDriver, 100);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#lixiangItem>h2")));
            webDrivers[i] = webDriver;
        }
    }

    public void fetchAll() throws InterruptedException {
        List<String> urls = projectBriefRepo.findDistinctUrl();
        urls = urls.stream()
                .filter(url -> projectRepo.findByUrl(url) == null)
                .collect(Collectors.toList());
        init();
        int eachSize = urls.size() / 1;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            int skip = i * eachSize;
            List<String> subUrlList = urls.stream()
                    .skip(skip)
                    .limit(eachSize)
                    .collect(Collectors.toList());
            FetchProjectThread thread = new FetchProjectThread(webDrivers[i], subUrlList);
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    class FetchProjectThread extends Thread {
        private WebDriver webDriver;
        private List<String> urls;

        public FetchProjectThread(WebDriver webDriver, List<String> urls) {
            this.webDriver = webDriver;
            this.urls = urls;
            logger.info("Received: {} URLs.", urls.size());
        }

        public Project fetchOneByUrl(String url) {
            Project project = new Project();
            project.setUrl(url);

            String pcNumStr = getPcParamFromUrl(url);
            if (pcNumStr == null) {
                logger.error("No param{pc} found in {} !", url);
                return null;
            }
            webDriver.get(BASE_URL.replace("#{pc}", pcNumStr));

            Document document = Jsoup.parse(webDriver.getPageSource());
            // title
            Elements titleElement = document.select("h2");
            if (StringUtils.isEmpty(titleElement.text())) {
                return null;
            }
            if (titleElement.text().split("　").length < 2) {
                return null;
            }
            project.setTitle(titleElement.text().split("　")[1].trim());

            // body > div.point_mainMess > table > tbody > tr:nth-child(1) > td:nth-child(1)
            Elements mainInfoLines = document.select("div.point_mainMess > table > tbody > tr");
            if (mainInfoLines.size() != 3) {
                logger.error("main info table line size != 3 !!");
                return project;
            }
            for (Element mainInfoLine : mainInfoLines) {
                Elements tds = mainInfoLine.select("td");
                for (int i = 0; i < tds.size() - 1; i += 2) {
                    String name = tds.get(i).text();
                    String value = tds.get(i + 1).text();
                    if (StringUtils.isEmpty(value)) {
                        continue;
                    }
                    switch (name) {
                        case "项目编号：":
                            project.setNumber(value);
                            break;
                        case "负 责 人：":
                            project.setLeaders(value);
                            break;
                        case "项目经费：":
                            project.setFund(value);
                            break;
                        case "承担单位：":
                            project.setOrganizations(value);
                            break;
                        case "项目来源：":
                            project.setSource(value);
                            break;
                        case "立项/完成时间：":
                            String[] split = value.split("/");
                            if (split.length == 1) {
                                project.setStartYear(split[0]);
                            } else if (split.length == 2) {
                                project.setStartYear(split[0]);
                                project.setEndYear(split[1]);
                            }
                            break;
                    }
                }
            }

            Elements otherInfoDiv = document.select("body > div:nth-child(6)");
            String blockName = otherInfoDiv.select("h3").text();
            if ("项目参与机构".equals(blockName)) {
                Elements lineElements = otherInfoDiv.select("table > tbody > tr");
                Element subjectLine = lineElements.get(0);
                Element keywordLine = lineElements.get(1);
                String subject = subjectLine.select("span.info_list").text();
                if (StringUtils.isNotEmpty(subject)) {
                    project.setSubject(subject);
                }
                Elements keywordElements = keywordLine.select("td > span");
                StringBuilder sb = new StringBuilder("");
                for (int i = 1; i < keywordElements.size(); i++) {
                    sb.append(keywordElements.get(i).text());
                }
                String keyword = sb.toString();
                if (StringUtils.isNotEmpty(keyword)) {
                    project.setKeyword(keyword);
                }
            }

            logger.info("Fetch project: {}", project);
            return project;
        }

        @Override
        public void run() {
            for (String url : urls) {
                Project project = fetchOneByUrl(url);
                if (project != null) {
                    projectRepo.save(project);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String getPcParamFromUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        if (!url.contains("pc=")) {
            return null;
        }
        int index = url.indexOf("pc=");
        String pc = url.substring(index + 3);
        List<String> blockList = new ArrayList<>();
        for (int i = 712451; i <= 712476; i++) {
            blockList.add(i + "001");
        }
        blockList.add("719549001");
        blockList.add("719553001");
        blockList.add("719554001");
        blockList.add("719555001");
        blockList.add("719577001");
        blockList.add("719602001");
        blockList.add("719609001");
        blockList.add("719610001");
        blockList.add("728079001");
        blockList.add("750297001");
        if (blockList.contains(pc)) {
            return null;
        }
        return pc;
    }
}
