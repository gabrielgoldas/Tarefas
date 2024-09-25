package com.gabrielgoldas.tarefas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabrielgoldas.tarefas.database.TarefaDAO
import com.gabrielgoldas.tarefas.databinding.ActivityAdicionarTarefaBinding
import com.gabrielgoldas.tarefas.databinding.ActivityMainBinding
import com.gabrielgoldas.tarefas.model.Tarefa

class AdicionarTarefaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAdicionarTarefaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Recuperar tarefa passada
        var tarefa: Tarefa? = null
        val bundle = intent.extras
        if( bundle != null ){
            tarefa = bundle.getSerializable("tarefa") as Tarefa
            binding.editTarefa.setText(tarefa.descricao)
        }

        binding.btnSalvar.setOnClickListener {
            if (binding.editTarefa.text.isNotEmpty()) {

                if ( tarefa != null ){
                    editar(tarefa)
                } else {
                    salvar()
                }

            } else {
                Toast.makeText(
                    this,
                    "Preencha uma tarefa!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun editar(tarefa: Tarefa) {

        val descricao = binding.editTarefa.text.toString()
        val tarefaAtualizada = Tarefa(
            tarefa.idTarefa, descricao, "default"
        )
        val tarefaDAO = TarefaDAO(this)
        if ( tarefaDAO.atualizar(tarefaAtualizada) ){
            Toast.makeText(
                this,
                "Tarefa atualizada com sucesso",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }

    }

    private fun salvar() {
        val descricao = binding.editTarefa.text.toString()
        val tarefa = Tarefa(
            -1, descricao, "default"
        )
        val tarefaDAO = TarefaDAO(this)
        if (tarefaDAO.salvar(tarefa)) {
            Toast.makeText(
                this,
                "Tarefa cadastrada com sucesso",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
}