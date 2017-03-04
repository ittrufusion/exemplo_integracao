package com.ittru.fusion.example;

import java.util.ArrayList;

import com.ittru.fusion.example.IIttruSystem.DocIdInfoGetRequest;
import com.ittru.fusion.example.IIttruSystem.DocIdInfoGetResponse;
import com.ittru.fusion.example.IIttruSystem.IDocIdInfoGet;
import com.ittru.fusion.example.IIttruSystem.Signature;

public class DocIdInfoGet implements IDocIdInfoGet {

	@Override
	public void run(DocIdInfoGetRequest req, DocIdInfoGetResponse resp)
			throws Exception {
		if (req.id.equals("01")) {
			resp.signature = new ArrayList<>();
			Signature signature = new Signature();
			signature.kind = "Assinatura";
			signature.signer = "RENATO DO AMARAL CRIVANO MACHADO";
			signature.ref = "A01";
			resp.signature.add(signature);
		}

		resp.code = "DOC/" + req.id;
		resp.descr = "Documento número: " + req.id;
		resp.kind = "Documento";
		resp.origin = "Test";
		resp.secret = DocIdPdfGet.retrievePdf(req.id, null).secret;

		// resp.status = "Aguardando andamento";
	}

	@Override
	public String getContext() {
		return "obter informações sobre documento";
	}
}