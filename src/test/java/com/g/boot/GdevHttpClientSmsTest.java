package com.g.boot;

import java.io.IOException;
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gdev.boot.domain.sms.SmsVo;
import com.gdev.boot.util.crw.GdevCrwConfig;
import com.gdev.boot.util.sms.DsendSms;
import com.gdev.boot.util.sms.GameSchedule;
import com.google.common.collect.Lists;



@RunWith(SpringRunner.class)
@SpringBootTest(classes= {GdevCrwConfig.class})
@AutoConfigureMockMvc
public class GdevHttpClientSmsTest {

	@Autowired
	private CloseableHttpClient httpClient;
	
	@Test
	public void cre() throws org.json.JSONException, IOException, ParseException {
        List<GameSchedule> gameSchedule = Lists.newArrayList();
        String urlParam = "stage=regular_season&season=2020&locale=ko-kr&page=1";
        Document doc = Jsoup.connect("https://wzavfvwgfk.execute-api.us-east-2.amazonaws.com/production/owl/paginator/schedule?" + urlParam)
                .header("accept", "*/*")
                .header("accept-encoding", "gzip, deflate, br")
                .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("origin", "https://overwatchleague.com")
                .header("Referer", "https://overwatchleague.com/ko-kr/schedule")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "cross-site")
                .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36")
                .header("x-origin", "overwatchleague.com")
                .header("content-type", "application/json")
                .ignoreContentType(true)
                .get();
        JSONObject gameObj = new JSONObject(doc.text());
        gameObj = gameObj.getJSONObject("content");
        gameObj = gameObj.getJSONObject("tableData");
        JSONArray events = gameObj.getJSONArray("events");
        
        for(int i = 0; i < events.length(); i++) {
        	JSONObject tempObj = events.getJSONObject(i);
        	JSONArray tempArray = tempObj.getJSONArray("matches");
        	for(int j = 0; j < tempArray.length(); j++) {
        		JSONObject matches = tempArray.getJSONObject(j);
        		gameSchedule.add(GameSchedule.of(matches.getLong("id"), matches.getString("startDate"), matches.getString("endDate"), null, null));	
        	}
        }
		System.out.println(gameSchedule);
	}
	
	public void httpClientSmsTest() throws IOException, InterruptedException, JSONException {
		DsendSms ds = new DsendSms();
		ds.setHttpClient(httpClient);
		System.out.println(ds.getHttpClient());
		List<SmsVo> a = Lists.newArrayList();
		a.add(SmsVo.builder().title("1").context("test").build());
		ds.sends(a, 1);
	}
}
