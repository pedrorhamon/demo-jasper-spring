package com.mballem.curso.jasper.spring.services;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class JasperService {

	private static final String JASPER_DIRETORIO = "classpath:jasper/";
	private static final String JASPER_PREFIXO = "Funcionario-";
	private static final String JASPER_SUFIXO = ".jasper";

	@Autowired
	private Connection connection;
	
//	@Autowired
//	private ResourceLoader resourceLoader;

	private Map<String, Object> params = new HashMap<>();

	public void addParams(String key, Object value) {
		this.params.put(key, value);
	}

	public byte[] exportarPDF(String code) {
		byte[] bytes = null;
		try {
			File file = ResourceUtils.getFile(JASPER_DIRETORIO.concat(JASPER_PREFIXO) .concat(code).concat(JASPER_SUFIXO));
			JasperPrint print = JasperFillManager
				    .fillReport(file.getAbsolutePath(), params, connection);
			bytes = JasperExportManager.exportReportToPdf(print);
		} catch (IOException | JRException e) {
			e.printStackTrace();
		}
		return bytes;
	}
}
