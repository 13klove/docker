package com.gdev.boot.util.sms;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import com.gdev.boot.domain.sms.SmsVo;
import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;


//status code
//0   : 정상발송
//100 : POST validation 실패
//101 : sender 유효한 번호가 아님
//102 : recipient 유효한 번호가 아님
//103 : 회원정보가 일치하지 않음
//104 : 받는 사람이 없습니다
//105 : message length = 0, message length >= 2000, title >= 20
//106 : message validation 실패
//107 : 이미지 업로드 실패
//108 : 이미지 갯수 초과
//109 : return_url이 유효하지 않습니다
//110 : 이미지 용량 300kb 초과
//111 : 이미지 확장자 오류
//112 : euckr 인코딩 에러 발생
//114 : 예약정보가 유효하지 않습니다.
//200 : 동일 예약시간으로는 200회 이상 발송할 수 없습니다.
//201 : 분당 300회 이상 발송할 수 없습니다.
//205 : 잔액부족
//999 : Internal Error.

@Setter @Getter
@Component
public class DsendSms{
	
	@Autowired
	private CloseableHttpClient httpClient;
	private static final String HOST = "https://directsend.co.kr/index.php/api_v2/sms_change_word";
	private static final String HOST_SUB = "https://directsend.co.kr/index.php/api/remaining_money";
	private static final int LIMIT_BYTE = 90; // SMS기준 (이값보다 길면 LMS)
	private String DM_KEY = "nIDNRrBdHfiwG2n";
	private String DS_ID = "11h11m";
	private String SENDER = "01099153163";
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void sends(List<SmsVo> smsVos, int splitSize) throws ClientProtocolException, JSONException, IOException{
		List<List<SmsVo>> sendList = splitBySize(smsVos, splitSize);
		for(List<SmsVo> tempSend : sendList){
			for(SmsVo temp : tempSend){
				send(temp);
			}
		}		
		//		List<List<SmsSend>> sendList = CollectionUtil.splitBySize(esvs, 250);
//		for(List<SmsSend> tempSend : sendList){
//			for(SmsSend temp : tempSend){
//				send(temp);
//			}
//			Thread.sleep(90000L);
//		}
	}
	
	public List<List<SmsVo>> splitBySize(List<SmsVo> smsVos, int splitSize){
		List<List<SmsVo>> returnValue = Lists.newArrayList();
		int startIdx = 0;
		int endIdx = smsVos.size()<splitSize?smsVos.size():splitSize;
		int cycle = (smsVos.size()/splitSize);
		cycle += smsVos.size()%splitSize>0?1:0;
		for(int i = 0; i < cycle; i++) {
			returnValue.addAll(Arrays.asList(smsVos.subList(startIdx, endIdx)));
			startIdx = endIdx;
			
			endIdx = endIdx+splitSize>smsVos.size()?smsVos.size():endIdx+splitSize;
		}
		return returnValue;
	}
	
	public void send(SmsVo vo) throws JSONException, ClientProtocolException, IOException {
		JSONObject sendObj = new JSONObject();
		sendObj.put("title", vo.getTitle());
		sendObj.put("message", vo.getContext());
		sendObj.put("sender", SENDER);
		sendObj.put("username", DS_ID);
		
		JSONArray sendObjSub = new JSONArray();
		JSONObject man = new JSONObject();
		man.put("name", "testMan");
		man.put("mobile", "01099153163");
		sendObjSub.put(man);
		/*
		 * if(esv.getReceiver().isEmpty()){ //없으면 보낼 수 없음. 임시처리다. return; }else{
		 * for(SmsSend temp : esv.getReceiver()){ JSONObject subCom = new JSONObject();
		 * if(temp.getAdvName() != null){ subCom.addProperty("name", temp.getAdvName());
		 * } subCom.addProperty("mobile", temp.getRecveHp()); sendObjSub.add(subCom); }
		 * }
		 */
		sendObj.put("receiver", sendObjSub);
		sendObj.put("key", DM_KEY);
		sendObj.put("type", "java");
		
		HttpPost hp = new HttpPost(HOST);
		hp.setEntity(new StringEntity(sendObj.toString())); //json 메시지 입력 

		HttpResponse response = httpClient.execute(hp);

		
		
//		sendObj.addProperty("title", esv.getTitle());
//		if(esv.getContent().getBytes(CharEncodeUtil.C_MS949).length > LIMIT_BYTE){
//			//80이상이면 sms가 아니다. 임시처리다.
//			return;
//		}
//		sendObj.addProperty("message", esv.getContent());
//		sendObj.addProperty("sender", SENDER);
//		sendObj.addProperty("username", DS_ID);
//		JsonArray sendObjSub = new JsonArray();
//		if(esv.getReceiver().isEmpty()){
//			//없으면 보낼 수 없음. 임시처리다.
//			return;
//		}else{
//			for(SmsSend temp : esv.getReceiver()){
//				JsonObject subCom = new JsonObject();
//				if(temp.getAdvName() != null){
//					subCom.addProperty("name", temp.getAdvName());	
//				}
//				subCom.addProperty("mobile", temp.getRecveHp());
//				sendObjSub.add(subCom);				
//			}
//		}
//		sendObj.add("receiver", sendObjSub);
//		sendObj.addProperty("key", DM_KEY);
//		sendObj.addProperty("type", "java");
//
//		HttpRequest2 req = new HttpRequest2();
//		req.post(HOST).entity(sendObj);
//		req.defaultHeaderAjaxSms();
//		
//		HttpResult2 hr = client.read(req);
//
//		GsonVo gv = GsonVo.parse(hr.getText());
//		String resultCode = gv.getString("status");
//		if(!resultCode.equals("0")){
//			log.error(esv.getReceiver()+"[ERROR] : "+resultCode);
//		}
	}
	
	public void directPoint() throws IOException{
//		NameValueList nvps = new NameValueList();
//		nvps.put("username", DS_ID);
//		nvps.put("key", DM_KEY);
//		
//		HttpRequest2 req = new HttpRequest2();
//		req.post(HOST_SUB).entity(nvps);
//		req.defaultHeaderTxt();
//		
//		HttpResult2 hr = client.read(req);
//
//		GsonVo gv = GsonVo.parse(hr.getText());
//		String point = gv.getString("point").replaceAll(",", "");
//		Long serverPoint = Long.parseLong(point.substring(0, point.lastIndexOf(".")));
//		
//		log.info("다이렉트 센드 잔여 포인트 : " + point);
//		
//		if(serverPoint <= 10000L){
///*			EpmMessageClient emp = new EpmMessageClient(client);	
//			emp.send("다이렉트 센드 잔액 : "+serverPoint.toString(), "13klove,mschoi,ccs0083");*/
//		}
		
	}
	
	public void testSend() throws IOException {
//		String title = "";
//		String message = "여기에 공백도 안된다.";
//		String sender = "01099153163";		
//		
//		JsonObject sendObj = new JsonObject();
//		sendObj.addProperty("title", title);
//		sendObj.addProperty("message", message);
//		sendObj.addProperty("sender", sender);
//		sendObj.addProperty("username", DS_ID);
//		JsonArray sendObjSub = new JsonArray();
//		JsonObject subCom = new JsonObject();
//		//subCom.addProperty("name", "장혁빈");
//		subCom.addProperty("mobile", "01099153163");
//		sendObjSub.add(subCom);
//		sendObj.add("receiver", sendObjSub);
//		sendObj.addProperty("key", DM_KEY);
//		sendObj.addProperty("type", "java");
//		
//		HttpRequest2 req = new HttpRequest2();
//		req.post(HOST).entity(sendObj);
//		req.defaultHeaderAjaxSms();
//		
//		HttpResult2 hr = client.read(req);
//		GsonVo gv = GsonVo.parse(hr.getText());
//		String resultCode = gv.getString("status");
	}	
	
}