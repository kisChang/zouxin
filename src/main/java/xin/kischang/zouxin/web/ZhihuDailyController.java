package xin.kischang.zouxin.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsslibj.elements.Channel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xin.kischang.zouxin.web.formbean.ZhihuDaily;
import xin.kischang.zouxin.web.formbean.ZhihuDailyItem;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/apis/rss/zhihu")
public class ZhihuDailyController {

	@Value("${zhihu.daily.api}")
	private String zhihuApi;

	@RequestMapping("/")
	public String welcome(Model model) {
		model.addAttribute("time", new Date());
		model.addAttribute("zhihuApi", zhihuApi);
		return "index";
	}

	private List<ZhihuDailyItem> all;
	private long lastUpdate = -1;

	@RequestMapping("/daily")
	@ResponseBody
	public String rss(HttpServletResponse response) {
		Channel channel=new Channel();
		channel.setTitle("知乎日报RSS");
		channel.setLink("http://zoudian.xin/apis/rss/zhihu/daily");
		channel.setDescription("知乎日报RSS");
		long noUp = System.currentTimeMillis() - lastUpdate;
		//1个小时更新一次缓存
		if (noUp > 3600000 || all.isEmpty()){
			updateItems();
		}

		for (ZhihuDailyItem item : all){
			channel.addItem(item.getUrl(),
					item.getContent(),
					item.getTitle());
		}
		response.setContentType("text/xml");
		try {
			return channel.getFeed("rdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private synchronized void updateItems() {
		if (System.currentTimeMillis() - lastUpdate < 100000){
			//最近刚更了
			return;
		}
		try {
			String json = ZhihuUtils.get(zhihuApi);
			ZhihuDaily daily = ZhihuUtils.objectMapper.readValue(json, new TypeReference<ZhihuDaily>(){});
			this.all = daily.getAll();
		} catch (IOException ignored) {
			this.all = Collections.emptyList();
		} finally {
			lastUpdate = System.currentTimeMillis();
		}
	}

}