package model.entities;

import java.util.Date;
import java.util.Objects;

public class Equipamento {
	private Integer IdEquipamento;
	private String NomeEquipamento;
	private Date DataFabricacao;
	private Float ValorEquipamento;

	public Equipamento() {
	}
	public Equipamento(Integer IdEquipamento, String NomeEquipamento, Date DataFabricacao,  Float ValorEquipamento) {
		this.IdEquipamento = IdEquipamento;
		this.NomeEquipamento = NomeEquipamento;
		this.DataFabricacao = DataFabricacao;
		this.ValorEquipamento = ValorEquipamento;
	}

	public Integer getIdEquipamento() {
		return IdEquipamento;
	}

	public void setIdEquipamento(Integer idEquipamento) {
		IdEquipamento = idEquipamento;
	}

	public String getNomeEquipamento() {
		return NomeEquipamento;
	}

	public void setNomeEquipamento(String nomeEquipamento) {
		this.NomeEquipamento = nomeEquipamento;
	}

	public Date getDataFabricacao() {
		return DataFabricacao;
	}

	public void setDataFabricacao(Date DataFabricacao) {
		this.DataFabricacao = DataFabricacao;
	
	}
	public Float getValorEquipamento() {
		return ValorEquipamento;
	}
	
	public void setValorEquipamento(Float ValorEquipamento) {
		this.ValorEquipamento = ValorEquipamento;
	}

	public String toString() {
		return "Equipamento [IdEquipamento=" + IdEquipamento + ", NomeEquipamento=" + NomeEquipamento + ", DataFabricacao="
				+ DataFabricacao + ", ValorEquipamento=" + ValorEquipamento +"]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(IdEquipamento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipamento other = (Equipamento) obj;
		return Objects.equals(IdEquipamento, other.IdEquipamento);
	}

}
