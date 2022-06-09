package model.entities;

import java.util.Date;
import java.util.Objects;

public class Medicacao {
	private Integer IdMedicacao;
	private String NomeMedicacao;
	private Date DataValidade;
	private Float ValorMedicacao;
	private Integer QuantidadeMedicacao;

	public Medicacao() {
	}
	public Medicacao(Integer IdMedicacao, String NomeMedicacao, Date DataValidade,  Float ValorMedicacao, Integer QuantidadeMedicacao) {
		this.IdMedicacao = IdMedicacao;
		this.NomeMedicacao = NomeMedicacao;
		this.DataValidade = DataValidade;
		this.ValorMedicacao = ValorMedicacao;
		this.QuantidadeMedicacao = QuantidadeMedicacao;
	}

	public Integer getIdMedicacao() {
		return IdMedicacao;
	}

	public void setIdMedicacao(Integer idMedicacao) {
		IdMedicacao = idMedicacao;
	}

	public String getNomeMedicacao() {
		return NomeMedicacao;
	}

	public void setNomeMedicacao(String nomeMedicacao) {
		this.NomeMedicacao = nomeMedicacao;
	}

	public Date getDataValidade() {
		return DataValidade;
	}

	public void setDataValidade(Date DataValidade) {
		this.DataValidade = DataValidade;
	}

	public Integer getQuantidadeMedicacao() {
		return QuantidadeMedicacao;
	}
	public void setQuantidadeMedicacao(Integer quantidadeMedicacao) {
		QuantidadeMedicacao = quantidadeMedicacao;
	}

	public Float getValorMedicacao() {
		return ValorMedicacao;
	}
	
	public void setValorMedicacao(Float ValorMedicacao) {
		this.ValorMedicacao = ValorMedicacao;
	}
	
	public String toString() {
		return "Medicacao [IdMedicacao=" + IdMedicacao + ", NomeMedicacao=" + NomeMedicacao + ", ValorMedicacao=" + ValorMedicacao +", QuantidadeMedicacao=" + QuantidadeMedicacao + ", DataValidade=" + DataValidade +"]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(IdMedicacao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medicacao other = (Medicacao) obj;
		return Objects.equals(IdMedicacao, other.IdMedicacao);
	}

}
