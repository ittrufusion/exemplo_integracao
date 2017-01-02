package com.ittru.fusion.example;

import java.util.ArrayList;

import com.ittru.fusion.example.IIttruSystem.DocListGetRequest;
import com.ittru.fusion.example.IIttruSystem.DocListGetResponse;
import com.ittru.fusion.example.IIttruSystem.Document;
import com.ittru.fusion.example.IIttruSystem.IDocListGet;

public class DocListGet implements IDocListGet {
	private static final String[] DOCS = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };

	@Override
	public void run(DocListGetRequest req, DocListGetResponse resp) throws Exception {
		resp.list = new ArrayList<>();

		for (String s : DOCS) {
			Document doc = new Document();

			doc.id = s;
			doc.code = "DOC/" + s;
			doc.descr = "Documento número: " + s;
			doc.kind = "Documento";
			doc.origin = "Test";
			doc.secret = DocIdPdfGet.retrievePdf(s, req.cpf).secret;
			resp.list.add(doc);
		}
	}

	@Override
	public String getContext() {
		return "listar documentos";
	}

}
