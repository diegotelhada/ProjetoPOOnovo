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
import model.dao.EquipamentoDao;
import model.entities.Equipamento;

public class EquipamentoDaoJDBC implements EquipamentoDao {

	private Connection conn;

	public EquipamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Equipamento obj) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO Equipamento " + "(NomeEquip, DataFab, ValorEquip) " + "VALUES " +
					"(?, ?, ?)" ,
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNomeEquipamento());
			st.setDate(2, new java.sql.Date(obj.getDataFabricacao().getTime()));
			st.setFloat(3, obj.getValorEquipamento());

			int rowAffected = st.executeUpdate();

			if (rowAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdEquipamento(id);
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
	public void update(Equipamento obj) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("UPDATE Equipamento " + "SET NomeEquip = ?, DataFab = ? ,ValorEquip = ?" + "WHERE Id = ?");
			st.setString(1, obj.getNomeEquipamento());
			st.setDate(2, new java.sql.Date(obj.getDataFabricacao().getTime()));
			st.setFloat(3, obj.getValorEquipamento());
			st.setInt(4, obj.getIdEquipamento());

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
			st = conn.prepareStatement("DELETE FROM Equipamento " + "WHERE Id = ?");
			st.setInt(1, id);

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Equipamento findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT Equipamento.* FROM Equipamento " + "WHERE Equipamento.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Equipamento dep = instatiateEquipamento(rs);

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
	public List<Equipamento> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT Equipamento.* FROM Equipamento " + "ORDER BY ID");

			rs = st.executeQuery();

			List<Equipamento> list = new ArrayList<>();

			while (rs.next()) {

				Equipamento Equipamento = instatiateEquipamento(rs);
				list.add(Equipamento);
			}

			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	private Equipamento instatiateEquipamento(ResultSet rs) throws SQLException {
		Equipamento Equipamento = new Equipamento();
		Equipamento.setIdEquipamento(rs.getInt("Id"));
		Equipamento.setNomeEquipamento(rs.getString("NomeEquip"));
		Equipamento.setDataFabricacao(new java.util.Date(rs.getTimestamp("DataFab").getTime()));
		Equipamento.setValorEquipamento(rs.getFloat("ValorEquip"));
		return Equipamento;
	}

	@Override
	public List<Equipamento> findByNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT Equipamento.* FROM Equipamento WHERE NomeEquip LIKE '" + nome + "%' ORDER BY NomeEquip");
			
			rs = st.executeQuery();

			List<Equipamento> list = new ArrayList<>();

			while (rs.next()) {

				Equipamento Equipamento = instatiateEquipamento(rs);
				list.add(Equipamento);
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
