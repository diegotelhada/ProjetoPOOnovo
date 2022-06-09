package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.EquipamentoDao;
import model.entities.Equipamento;

public class EquipamentoService {
	
	private EquipamentoDao dao = DaoFactory.createEquipamentoDao();
	
	public List<Equipamento> findAll(){
		return dao.findAll();
	}
	
	//esse metodo que salva no banco o objeto passado
	public void saveOrUptade(Equipamento obj) {
		if(obj.getIdEquipamento() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(Equipamento obj) {
		dao.deleteById(obj.getIdEquipamento());
	}
	
	public List<Equipamento> findByNome(String nome){
		return dao.findByNome(nome);
	}
	
}
