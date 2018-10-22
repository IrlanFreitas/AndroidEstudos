package br.com.alura.agenda.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.alura.agenda.R;
import br.com.alura.agenda.models.Prova;

@Deprecated
public class DetalhesProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prova);

        Prova prova = (Prova) getIntent().getSerializableExtra("prova");

        if (prova == null) {
            finish();
        }

        TextView campoMateria = findViewById(R.id.detalhes_prova_materia);
        TextView campoData = findViewById(R.id.detalhes_prova_data);
        ListView listaTopicos = findViewById(R.id.detalhes_prova_lista_topicos);

        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, prova.getTopicos());

        listaTopicos.setAdapter(adapter);


    }
}
