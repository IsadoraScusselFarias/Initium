package br.com.db1.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.db1.dao.impl.CandidatoDao;
import br.com.db1.model.Candidato;


@RequestScoped
@Named
public class CandidatoBean {

	@Inject
	private CandidatoDao dao;

	private List<Candidato> list;

	private String nomeCandidatoFiltrada;

	private Candidato candidato;

	@PostConstruct
	public void init() {
		list = new ArrayList<Candidato>();
	}

	public String getNomeCandidatoFiltrada() {
		return nomeCandidatoFiltrada;
	}

	public void setNomeCandidatoFiltrada(String nomeCandidatoFiltrada) {
		this.nomeCandidatoFiltrada = nomeCandidatoFiltrada;
	}

	public Candidato getCandidato() {
		return candidato;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}

	public List<Candidato> getList() {
		return list;
	}

	public String novo() {
		candidato = new Candidato();
		return "cadastrarCandidato";
	}

	public void salvar() {
		if (!dao.save(candidato)) {
			adicionarMensagem("Erro ao cadastrar a Candidato.", FacesMessage.SEVERITY_ERROR);
		}

		adicionarMensagem("Candidato salvo com sucesso.", FacesMessage.SEVERITY_INFO);
	}

	public String editar() {
		candidato = dao.findById(1L);
		return "cadastrarCandidato";
	}

	public void remover(Candidato candidato) {
		if (!dao.delete(candidato.getId())) {
			adicionarMensagem("Erro ao remover a candidato.", FacesMessage.SEVERITY_ERROR);
		}

		adicionarMensagem("Candidato removida com sucesso.", FacesMessage.SEVERITY_INFO);
	}

	public void listarCandidato() {
		if (!nomeCandidatoFiltrada.isEmpty()) {
			list.addAll(dao.findByName(nomeCandidatoFiltrada));
		} else {
			list.addAll(dao.findAll());
		}
	}

	public void adicionarMensagem(String mensagem, Severity tipoMensagem) {
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage fm = new FacesMessage(mensagem);
		fm.setSeverity(tipoMensagem);
		fc.addMessage(null, fm);

	}

}
