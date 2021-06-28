package com.ds.soap.endPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ds.soap.service.WhiteTestService;

import de.tekup.soap.models.whitetest.StudentRequest;
import de.tekup.soap.models.whitetest.WhiteTestResponse;

@Endpoint
public class WhiteTestEndPoint {

	@Autowired
	WhiteTestService testService;

	public static final String nameSpace = "http://www.tekup.de/soap/models/whitetest";

	@PayloadRoot(namespace = nameSpace, localPart = "StudentRequest")
	@ResponsePayload
	public WhiteTestResponse getWhiteTest(@RequestPayload StudentRequest studentRequest) {
		return testService.foundWhitetTest(studentRequest);
	}

}
