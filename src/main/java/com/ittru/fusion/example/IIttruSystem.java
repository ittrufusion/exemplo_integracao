package com.ittru.fusion.example;

import java.util.Date;
import java.util.List;

import com.crivano.swaggerservlet.ISwaggerMethod;
import com.crivano.swaggerservlet.ISwaggerModel;
import com.crivano.swaggerservlet.ISwaggerRequest;
import com.crivano.swaggerservlet.ISwaggerResponse;

public interface IIttruSystem {
	public class Document implements ISwaggerModel {
		public String id;
		public String secret;
		public String code;
		public String descr;
		public String kind;
		public String origin;
		public String extra;
	}

	public class Signature implements ISwaggerModel {
		public String ref;
		public String signer;
		public String kind;
	}

	public class Movement implements ISwaggerModel {
		public Date time;
		public String department;
		public String kind;
	}

	public class Warning implements ISwaggerModel {
		public String label;
		public String description;
	}

	public class ErrorDetail implements ISwaggerModel {
		public String service;
		public String context;
		public String stacktrace;
		public String url;
		public Boolean presentable;
		public Boolean logged;
	}

	public class Error implements ISwaggerModel {
		public String errormsg;
		public List<ErrorDetail> errordetails;
	}

	public class DocListGetRequest implements ISwaggerRequest {
		public String cpf;
		public String urlapi;
	}

	public class DocListGetResponse implements ISwaggerResponse {
		public List<Document> list;
	}

	public interface IDocListGet extends ISwaggerMethod {
		public void run(DocListGetRequest req, DocListGetResponse resp)
				throws Exception;
	}

	public class DocIdPdfGetRequest implements ISwaggerRequest {
		public String id;
		public String cpf;
	}

	public class DocIdPdfGetResponse implements ISwaggerResponse {
		public byte[] doc;
		public String secret;
	}

	public interface IDocIdPdfGet extends ISwaggerMethod {
		public void run(DocIdPdfGetRequest req, DocIdPdfGetResponse resp)
				throws Exception;
	}

	public class DocIdHashGetRequest implements ISwaggerRequest {
		public String id;
		public String cpf;
		public String extra;
	}

	public class DocIdHashGetResponse implements ISwaggerResponse {
		public byte[] sha1;
		public byte[] sha256;
		public String policy;
		public String extra;
		public byte[] doc;
		public String secret;
	}

	public interface IDocIdHashGet extends ISwaggerMethod {
		public void run(DocIdHashGetRequest req, DocIdHashGetResponse resp)
				throws Exception;
	}

	public class DocIdSignPutRequest implements ISwaggerRequest {
		public String id;
		public String cpf;
		public String name;
		public Date time;
		public byte[] sha1;
		public String extra;
		public byte[] envelope;
		public String policy;
		public String policyversion;
	}

	public class DocIdSignPutResponse implements ISwaggerResponse {
		public String status;
		public List<Warning> warning;
	}

	public interface IDocIdSignPut extends ISwaggerMethod {
		public void run(DocIdSignPutRequest req, DocIdSignPutResponse resp)
				throws Exception;
	}

	public class DocIdInfoGetRequest implements ISwaggerRequest {
		public String id;
	}

	public class DocIdInfoGetResponse implements ISwaggerResponse {
		public String status;
		public List<Signature> signature;
		public List<Movement> movement;
		public String secret;
		public String code;
		public String descr;
		public String kind;
		public String origin;
		public String extra;
	}

	public interface IDocIdInfoGet extends ISwaggerMethod {
		public void run(DocIdInfoGetRequest req, DocIdInfoGetResponse resp)
				throws Exception;
	}

	public class SignRefGetRequest implements ISwaggerRequest {
		public String ref;
	}

	public class SignRefGetResponse implements ISwaggerResponse {
		public byte[] envelope;
		public Date time;
	}

	public interface ISignRefGet extends ISwaggerMethod {
		public void run(SignRefGetRequest req, SignRefGetResponse resp)
				throws Exception;
	}

}