/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author rapha
 */
public class TaskController {

    public void save(Task task) {
        String sql = "INSERT INTO tasks(idproject,"
                + "name,"
                + "description,"
                + "notes,"
                + "completed,"
                + "deadline,"
                + "createdAt,"
                + "updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescritpion());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new Date(task.getDeadLine().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa "
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void update(Task task) {

        String sql = "UPDATE tasks SET "
                + "idproject = ?, "
                + "name = ?, "
                + "description = ?, "
                + "notes = ?, "
                + "completed = ?, "
                + "deadline = ?, "
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            //Preparando a Query
            statement = connection.prepareStatement(sql);
            //Setando os valores no statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescritpion());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new Date(task.getDeadLine().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            //Executando a Query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa "
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void removeById(int taskId) throws SQLException {

        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, taskId);
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa"
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Task> getAll(int idProject) {

        String sql = "SELECT * FROM tasks WHERE idproject = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Task> tasks = new ArrayList<Task>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            //Setando o valor que corresponde ao filtro de busca
            statement.setInt(1, idProject);
            //Valor retornado pela execução da Query
            resultSet = statement.executeQuery();

            //Enquanto houverem valores a serem percorridos
            while (resultSet.next()) {

                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idproject"));
                task.setName(resultSet.getString("name"));
                task.setDescritpion(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadLine(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));

                tasks.add(task);

            }

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir a tarefas"
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        return tasks;
    }
}
