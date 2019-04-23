package org.eclipse.om2m.ipe.smart_twin.model;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.ipe.smart_twin.util.RequestSender;

public abstract class Param<T> {

	private String name;

	private RequestSender requestSender;

	private T param;

	private Building building;

	public Param(String name, Building b, RequestSender requestSender) {
		this.name = name;
		this.building = b;
		this.requestSender = requestSender;
		this.createCNT();
	}

	public String getName() {
		return this.name;
	}

	public ResponsePrimitive getLA() {
		return requestSender.getRequest(getCNTPrefix() + "/la");
	}

	public T getParam() {
		return this.param;
	}

	protected ResponsePrimitive setParam(T param) {
		this.param = param;
		ContentInstance contentInstance = new ContentInstance();
		contentInstance.setContent(this.toString());
		return requestSender.createContentInstance(this.getCNTPrefix(), contentInstance);

	}

	abstract T paramFromString(String state);

	private String getCNTPrefix() {
		return this.building.getAEPrefix() + "/" + this.getName();
	}

	public ResponsePrimitive newOperation(String op) {
		return this.setParam(this.paramFromString(op));
	}

	private void createCNT() {
		ResponsePrimitive response = requestSender.getRequest(this.getCNTPrefix());
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			Container container = new Container();
			container.setName(this.getName());
			requestSender.createContainer(this.building.getAEPrefix(), container);
		}
	}
	
	public String toString() {
		return "{\"param\":\"" + this.getName() + "\", \"value\":\""
				+ this.getParam().toString() + "\"}";
	}

}
