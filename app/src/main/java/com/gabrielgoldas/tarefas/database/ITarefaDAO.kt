package com.gabrielgoldas.tarefas.database

import com.gabrielgoldas.tarefas.model.Tarefa

interface ITarefaDAO {

    fun salvar( tarefa: Tarefa ): Boolean
    fun atualizar( tarefa: Tarefa ): Boolean
    fun remover( idTarefa: Int ): Boolean
    fun listar(): List<Tarefa>
}