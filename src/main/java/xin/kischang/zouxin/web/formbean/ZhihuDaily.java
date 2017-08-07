package xin.kischang.zouxin.web.formbean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KisChang
 * @version 1.0
 */
public class ZhihuDaily implements java.io.Serializable {

    private String date;
    private List<ZhihuDailyItem> stories;
    private List<ZhihuDailyItem> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhihuDailyItem> getStories() {
        return stories;
    }

    public void setStories(List<ZhihuDailyItem> stories) {
        this.stories = stories;
    }

    public List<ZhihuDailyItem> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<ZhihuDailyItem> top_stories) {
        this.top_stories = top_stories;
    }

    public List<ZhihuDailyItem> getAll() {
        List<ZhihuDailyItem> all = new ArrayList<>();
        if (stories != null && !stories.isEmpty()){
            all.addAll(stories);
        }
        if (top_stories != null && !top_stories.isEmpty()){
            all.addAll(top_stories);
        }
        return all;
    }
}
