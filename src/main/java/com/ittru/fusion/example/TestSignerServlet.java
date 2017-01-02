package com.ittru.fusion.example;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.crivano.swaggerservlet.SwaggerServlet;
import com.crivano.swaggerservlet.SwaggerUtils;

public class TestSignerServlet extends SwaggerServlet {
	private static final long serialVersionUID = -1611417120964698257L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		super.setAPI(IIttruSystem.class);

		super.setActionPackage("br.jus.trf2.testsigner");

		super.setAuthorization(SwaggerUtils.getProperty("testsigner.password", null));
	}
}
