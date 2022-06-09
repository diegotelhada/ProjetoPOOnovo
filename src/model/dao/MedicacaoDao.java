package model.dao;

import java.util.List;

import model.entities.Medicacao;

public interface MedicacaoDao {
	void insert(Medicacao obj);
	void update(Medicacao obj);
	void deleteById(Integer id);
	Medicacao findById(Integer id);
	List<Medicacao> findAll();
	List<Medicacao> findByNome(String nome);
}
