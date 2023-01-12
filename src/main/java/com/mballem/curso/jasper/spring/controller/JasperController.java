package com.mballem.curso.jasper.spring.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mballem.curso.jasper.spring.repositories.EnderecoRepository;
import com.mballem.curso.jasper.spring.repositories.NivelRepository;
import com.mballem.curso.jasper.spring.services.JasperService;

@Controller
public class JasperController {
	
	@Autowired
	private JasperService service;
	
	@Autowired
	private NivelRepository nivelRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@GetMapping("/reports")
	public String abrir() {
		return "reports";
	}

	@GetMapping("/relatorio/pdf/jr1")
	public void exibirRelatorio01(@RequestParam("code") String code, @RequestParam("acao") String acao, HttpServletResponse response) throws IOException {
		byte[] bytes =this.service.exportarPDF(code);
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		if(acao.equals("v")) {
			response.setHeader("Content-disposition", "inline; filename=relatorio-" + code + ".pdf");
		} else {
			response.setHeader("Content-disposition", "attachment; filename=relatorio-" + code + ".pdf");
		}
		response.getOutputStream().write(bytes);
	}
	
	@GetMapping("/relatorio/pdf/jr9/{code}")
	public void exibirRelatorio09(@PathVariable("code") String code, 
			@RequestParam(name = "nivel", required = false) String nivel, 
			@RequestParam(name ="uf", required = false) String uf, 
			HttpServletResponse response) throws IOException {
		this.service.addParams("NIVEL_DESC", nivel.isEmpty() ? null : nivel);
		this.service.addParams("UF", uf.isEmpty() ? null : uf);
		
		byte[] bytes =this.service.exportarPDF(code);
		response.setContentType(MediaType.APPLICATION_PDF_VALUE);
		response.setHeader("Content-disposition", "inline; filename=relatorio-" + code + ".pdf");
		response.getOutputStream().write(bytes);
	}
	
	@ModelAttribute("niveis")
	public List<String> getNiveis(){
		return this.nivelRepository.findNiveis();
	}
	
	@ModelAttribute("ufs")
	public List<String> getUFs(){
		return this.enderecoRepository.findUfs();
	}
}
