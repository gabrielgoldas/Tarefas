package com.gabrielgoldas.tarefas.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.gabrielgoldas.tarefas.model.Tarefa

class TarefaDAO(context: Context) : ITarefaDAO {

    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase

    override fun salvar(tarefa: Tarefa): Boolean {
        val conteudos = ContentValues()
        conteudos.put("${DatabaseHelper.COLUNA_DESCRICAO}", tarefa.descricao)

        try {
            escrita.insert(
                DatabaseHelper.NOME_TABELA_TAREFAS,
                null,
                conteudos
            )
            Log.i("info_db", "Sucesso ao salvar a tarefa")
        } catch (e: Exception){
            e.printStackTrace()
            Log.i("info_db", "Erro ao salvar a tarefa ")
            return false
        }
        return true

    }

    override fun atualizar(tarefa: Tarefa): Boolean {

        val args = arrayOf(tarefa.idTarefa.toString())
        val conteudo = ContentValues()
        conteudo.put("${DatabaseHelper.COLUNA_DESCRICAO}", tarefa.descricao)

        try {
            escrita.update(
                DatabaseHelper.NOME_TABELA_TAREFAS,
                conteudo,
                "${DatabaseHelper.COLUNA_ID_TAREFA} = ?",
                args
            )
            Log.i("info_db", "Sucesso ao atualizar a tarefa")
        } catch (e: Exception){
            e.printStackTrace()
            Log.i("info_db", "Erro ao atualizar a tarefa ")
            return false
        }
        return true

    }

    override fun remover(idTarefa: Int): Boolean {
        val args = arrayOf(idTarefa.toString())
        try {
            escrita.delete(
                DatabaseHelper.NOME_TABELA_TAREFAS,
                "${DatabaseHelper.COLUNA_ID_TAREFA} = ?",
                args
            )
            Log.i("info_db", "Sucesso ao remover a tarefa")
        } catch (e: Exception){
            e.printStackTrace()
            Log.i("info_db", "Erro ao remover a tarefa ")
            return false
        }
        return true

    }

    override fun listar(): List<Tarefa> {

        val listaTarefas  = mutableListOf<Tarefa>()

        val sql = "SELECT ${DatabaseHelper.COLUNA_ID_TAREFA}, " +
                "${DatabaseHelper.COLUNA_DESCRICAO}," +
                "    strftime('%d/%m/%Y %H:%M', ${DatabaseHelper.COLUNA_DATA_CADASTRO}) ${DatabaseHelper.COLUNA_DATA_CADASTRO}" +
                " FROM ${DatabaseHelper.NOME_TABELA_TAREFAS};"

        val cursor = leitura.rawQuery( sql, null )

        val indiceId  = cursor.getColumnIndex( DatabaseHelper.COLUNA_ID_TAREFA)
        val indiceDescricao  = cursor.getColumnIndex( DatabaseHelper.COLUNA_DESCRICAO)
        val indiceData  = cursor.getColumnIndex( DatabaseHelper.COLUNA_DATA_CADASTRO)

        while ( cursor.moveToNext() ){
            val idTarefa = cursor.getInt( indiceId)
            val descricao = cursor.getString( indiceDescricao)
            val data = cursor.getString( indiceData)

            listaTarefas.add(
                Tarefa(idTarefa, descricao, data)
            )
        }

        return listaTarefas

    }
}