package com.gdev.boot.util.crw;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public interface CrwParser {

	public CrwVo read(CrwVo vo) throws ClientProtocolException, IOException ;
	
}
