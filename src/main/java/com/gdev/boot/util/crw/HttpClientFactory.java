package com.gdev.boot.util.crw;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import lombok.Data;

@Data
public class HttpClientFactory {

	private int maxConnect = 1;
	private int maxTimeOut = 2000;
	
	public static CloseableHttpClient redirectHttpClient(int maxConnect, int maxTimeOut){
		PoolingHttpClientConnectionManager phccm = new PoolingHttpClientConnectionManager();
		phccm.setMaxTotal(maxConnect*2);//커넥션 풀 수
		phccm.setDefaultMaxPerRoute(maxConnect);//한 서버에 접근 가능한 커넥션 수
		
		RequestConfig rc = RequestConfig.custom()
				.setConnectTimeout(maxTimeOut)//서버에 소켓 연결 타임 아웃
				.setConnectionRequestTimeout(maxTimeOut)//커넥션 풀에서 꺼내올 때의 타임 아웃
				.setRedirectsEnabled(false)//크롤링한 서버가 리다이렉트를 하면 에러 처리
				.setSocketTimeout(maxTimeOut).build();//요청/응답간의 타임 아웃
		
		
		SocketConfig sc = SocketConfig.custom()
				.setSoTimeout(maxTimeOut)//소켓 연결 후 inputStream으로 읽을때 타임 아웃
				.setSoKeepAlive(true)//소켓 내부적으로 일정시간 간격으로 heathCheck를 해 비정상 세션 종료 감지
				.setTcpNoDelay(true)//nagle알고리즘 사용여부.... 정확히 먼지 모름.
				.setSoReuseAddress(true).build();//비정상종료된 산태에서 아직 커널이 소켓의 정보를 가지고 있다면 소켓 재사용

		
//		HttpClientBuilder hcb = HttpClientBuilder.create();
//		RedirectStrategy rs = new DefaultRedirectStrategy();
//		LaxRedirectStrategy lrs = new LaxRedirectStrategy();
//		hcb.setRedirectStrategy(lrs);
//		hcb.setDefaultRequestConfig(rc);
//		hcb.setConnectionManager(phccm);
//		hcb.setDefaultSocketConfig(sc);
//		CloseableHttpClient chc = hcb.build();		
		
		return HttpClients.custom().setConnectionManager(phccm).setDefaultRequestConfig(rc).setDefaultSocketConfig(sc).build();
	}
	
	public static CloseableHttpClient httpClient(int maxConnect, int maxTimeOut){
		PoolingHttpClientConnectionManager phccm = new PoolingHttpClientConnectionManager();
		phccm.setMaxTotal(maxConnect*2);//커넥션 풀 수
		phccm.setDefaultMaxPerRoute(maxConnect);//한 서버에 접근 가능한 커넥션 수
		
		RequestConfig rc = RequestConfig.custom()
				.setConnectTimeout(maxTimeOut)//서버에 소켓 연결 타임 아웃
				.setConnectionRequestTimeout(maxTimeOut)//커넥션 풀에서 꺼내올 때의 타임 아웃
				.setRedirectsEnabled(true)//크롤링한 서버가 리다이렉트를 하면 에러 처리
				.setSocketTimeout(maxTimeOut).build();//요청/응답간의 타임 아웃
		
		
		SocketConfig sc = SocketConfig.custom()
				.setSoTimeout(maxTimeOut)//소켓 연결 후 inputStream으로 읽을때 타임 아웃
				.setSoKeepAlive(true)//소켓 내부적으로 일정시간 간격으로 heathCheck를 해 비정상 세션 종료 감지
				.setTcpNoDelay(true)//nagle알고리즘 사용여부.... 정확히 먼지 모름.
				.setSoReuseAddress(true).build();//비정상종료된 산태에서 아직 커널이 소켓의 정보를 가지고 있다면 소켓 재사용

		return HttpClients.custom().setConnectionManager(phccm).setDefaultRequestConfig(rc).setDefaultSocketConfig(sc).build();
	}	
	
	
}
