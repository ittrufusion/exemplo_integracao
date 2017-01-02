package com.ittru.fusion.example;

import java.io.InputStream;
import java.sql.SQLException;

import com.ittru.fusion.example.IIttruSystem.DocIdPdfGetRequest;
import com.ittru.fusion.example.IIttruSystem.DocIdPdfGetResponse;
import com.ittru.fusion.example.IIttruSystem.IDocIdPdfGet;

public class DocIdPdfGet implements IDocIdPdfGet {

	@Override
	public void run(DocIdPdfGetRequest req, DocIdPdfGetResponse resp) throws Exception {
		PdfData pdfd = retrievePdf(req.id, req.cpf);
		resp.doc = pdfd.pdf;
		resp.secret = pdfd.secret;
	}

	protected static PdfData retrievePdf(String id, String cpf) throws Exception, SQLException {
		PdfData pdfd = new PdfData();
		InputStream is = DocIdPdfGet.class.getResourceAsStream(id + ".pdf");
		pdfd.pdf = Utils.convertStreamToByteArray(is, 64000);
		pdfd.secret = Utils.makeSecret(pdfd.pdf);
		return pdfd;
	}

	@Override
	public String getContext() {
		return "visualizar documento";
	}
}
