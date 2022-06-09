package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.MedicacaoDao;
import model.entities.Medicacao;

public class MedicacaoService {
	
	private MedicacaoDao dao = DaoFactory.createMedicacaoDao();
	
	public List<Medicacao> findAll(){
		return dao.findAll();
	}
	
	//esse metodo que salva no banco o objeto passado
	public void saveOrUptade(Medicacao obj) {
		if(obj.getIdMedicacao() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(Medicacao obj) {
		dao.deleteById(obj.getIdMedicacao());
	}
	
	public List<Medicacao> findByNome(String nome){
		return dao.findByNome(nome);
	}
	
}
