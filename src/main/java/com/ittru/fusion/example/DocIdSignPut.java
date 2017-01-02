package com.ittru.fusion.example;

import com.ittru.fusion.example.IIttruSystem.DocIdSignPutRequest;
import com.ittru.fusion.example.IIttruSystem.DocIdSignPutResponse;
import com.ittru.fusion.example.IIttruSystem.IDocIdSignPut;

public class DocIdSignPut implements IDocIdSignPut {

	@Override
	public void run(DocIdSignPutRequest req, DocIdSignPutResponse resp) throws Exception {
		if (req.envelope == null)
			throw new Exception("Envelope não pode ser nulo na gravação da assinatura");
		resp.status = "OK";
	}

	@Override
	public String getContext() {
		return "salvar assinatura";
	}
}