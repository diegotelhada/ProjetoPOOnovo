package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.MedicacaoDao;
import model.entities.Medicacao;

public class MedicacaoDaoJDBC implements MedicacaoDao {

	private Connection conn;

	public MedicacaoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Medicacao obj) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO Medicacao " + "(NomeMedic, DataVal, ValorMedic, QuantidadeMedic) " + "VALUES " +
					"(?, ?, ?, ?)" ,
					Statement.RETURN_GENERATED_KEYS);
			
			

			st.setString(1, obj.getNomeMedicacao());
			st.setDate(2, new java.sql.Date(obj.getDataValidade().getTime()));
			st.setFloat(3, obj.getValorMedicacao());
			st.setFloat(4, obj.getQuantidadeMedicacao());

			int rowAffected = st.executeUpdate();

			if (rowAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdMedicacao(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("unexpected error! No rows affected!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Medicacao obj) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("UPDATE Medicacao " + "SET NomeMedic = ?, DataVal = ? ,ValorMedic = ? ,QuantidadeMedic = ?"  + "WHERE id = ?");
			st.setString(1, obj.getNomeMedicacao());
			st.setDate(2, new java.sql.Date(obj.getDataValidade().getTime()));
			st.setFloat(3, obj.getValorMedicacao());
			st.setFloat(4, obj.getQuantidadeMedicacao());
			st.setInt(5, obj.getIdMedicacao());

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("DELETE FROM Medicacao " + "WHERE id = ?");
			st.setInt(1, id);

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Medicacao findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT Medicacao.* FROM Medicacao " + "WHERE Medicacao.id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Medicacao dep = instatiateMedicacao(rs);

				return dep;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Medicacao> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT Medicacao.* FROM Medicacao " + "ORDER BY Id");

			rs = st.executeQuery();

			List<Medicacao> list = new ArrayList<>();

			while (rs.next()) {

				Medicacao Medicacao = instatiateMedicacao(rs);
				list.add(Medicacao);
			}

			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	private Medicacao instatiateMedicacao(ResultSet rs) throws SQLException {
		Medicacao Medicacao = new Medicacao();
		Medicacao.setIdMedicacao(rs.getInt("id"));
		Medicacao.setNomeMedicacao(rs.getString("NomeMedic"));
		Medicacao.setDataValidade(new java.util.Date(rs.getTimestamp("DataVal").getTime()));
		Medicacao.setValorMedicacao(rs.getFloat("ValorMedic"));
		Medicacao.setQuantidadeMedicacao(rs.getInt("QuantidadeMedic"));
		return Medicacao;
	}

	@Override
	public List<Medicacao> findByNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT Medicacao.* FROM Medicacao WHERE (NomeMedic LIKE '" + nome + "%' ORDER BY (QuantidadeMedic");
			
			rs = st.executeQuery();

			List<Medicacao> list = new ArrayList<>();

			while (rs.next()) {

				Medicacao Medicacao = instatiateMedicacao(rs);
				list.add(Medicacao);
			}

			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

}
