package xin.kischang.zouxin.web.formbean;

import okhttp3.OkHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;
import xin.kischang.zouxin.web.ZhihuDailyController;
import xin.kischang.zouxin.web.ZhihuUtils;

import java.io.IOException;

/**
 * @author KisChang
 * @version 1.0
 */
public class ZhihuDailyItem implements java.io.Serializable {

    private long id;
    private String title;

    private String image;
    private String[] images;

    private int type;
    private String ga_prefix;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    private String getRelImage(){
        if (!StringUtils.isEmpty(image)){
            return image;
        }
        if (images != null && images.length >= 1){
            return images[0];
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZhihuDailyItem that = (ZhihuDailyItem) o;

        if (id != that.id) return false;
        return title != null ? title.equals(that.title) : that.title == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ZhihuItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public String getUrl() {
        return "http://daily.zhihu.com/story/" + this.id;
    }

    private String content = null;

    public synchronized String getContent() {
        while (this.content == null){
            try {
                String htmlCon = ZhihuUtils.get(getUrl());
                Document doc = Jsoup.parse(htmlCon);
                Elements contentEle = doc.select(".content");
                this.content = contentEle.html();
            } catch (IOException e) {
                this.content = "错误：" + e.getMessage();
            }
        }
        return this.content;
    }
}
