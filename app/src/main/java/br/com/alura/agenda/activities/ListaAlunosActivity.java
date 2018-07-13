package br.com.alura.agenda.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.helpers.FormularioHelper;
import br.com.alura.agenda.models.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private AlunoDAO alunoDAO;

    private ListView listaAlunos;

    private final Integer CODIGO_REQUISICAO_LIGACAO = 9090;

    //Método sobrescrito baseado no ciclo de vida da Atividade (Activity)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Chamado caso tenha algum comportamento acontecendo anteiormente
        super.onCreate(savedInstanceState);

        //Ligação da tela(xml) com o comportamento(activity)
        setContentView(R.layout.activity_lista_alunos);

        /*Em vez de usar o TextView para cada um dos elementos
         * melhor usar um ListView para uma lista de elementos como
         * TextView. */
        listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {

                Aluno aluno = (Aluno) lista.getItemAtPosition(position);
                Intent vaiParaFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                vaiParaFormulario.putExtra("aluno", aluno);
                startActivity(vaiParaFormulario);
            }
        });

        alunoDAO = new AlunoDAO(this);

        Button btnAdicionar = findViewById(R.id.lista_alunos_btn_adicionar);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Declarando a intenção para o Android gerenciar.
                //Intent Explícita é declarado exatamente de onde você está e quer ir;
                Intent intentIrParaFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentIrParaFormulario);
            }
        });

        registerForContextMenu(listaAlunos);


    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    private void carregarLista() {
        /*Sobre o ArrayAdapter
         * necessário passar no construtor, o contexto - que é a atual Activity
         * depois o layout da lista, e como não temos passarei um layout pronto do próprio android
         * e depois a fonte de dados */
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunoDAO.getAlunos());

        //Adicionando o Adapter a ListView
        listaAlunos.setAdapter(adapter);
    }


    /* Depois de registrado para o menu de contexto para a determinada view pela forma:

        registerForContextMenu(listaAlunos);

        As ações do menu de contexto são personalizadas, por esse método.
    * */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        menu.setHeaderTitle("Opções");


        MenuItem ligar = menu.add("Ligar");
        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


                //Aqui é verificado se a permissão ainda não foi dada, caso não, é solicitada ao usuário
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    //RequestPermission é o pop-up que aparece para solicitar permissão
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            CODIGO_REQUISICAO_LIGACAO);
                } else {

                    //Caso essa permissão já tenha sido obtida, é somente feita a chamada.

                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentLigar);
                }


                return false;
            }
        });

        MenuItem enviarSMS = menu.add("Enviar SMS");
        Intent intentEnviarSMS = new Intent(Intent.ACTION_VIEW);
        intentEnviarSMS.setData( Uri.parse("sms:"+aluno.getTelefone() ));
        enviarSMS.setIntent(intentEnviarSMS);

        MenuItem visualizarNoMapa = menu.add("Visualizar no mapa");
        Intent intentVisualizarMapa = new Intent(Intent.ACTION_VIEW);
        intentVisualizarMapa.setData( Uri.parse("geo:0,0?q="+aluno.getEndereco() ));
        visualizarNoMapa.setIntent(intentVisualizarMapa);

        if (aluno.getSite() != null) {

            final MenuItem visitarSite = menu.add("Visitar o site");

            visitarSite.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    //Intent implícita, você quer essa ação não importando aceite e faça
                    Intent acessarSite = new Intent(Intent.ACTION_VIEW);
                    if (!aluno.getSite().startsWith("http://")) {
                        acessarSite.setData(Uri.parse("http://" + aluno.getSite()));
                    } else {
                        acessarSite.setData(Uri.parse(aluno.getSite()));
                    }

                    //Atalho para fazer o startActivity de outra forma
                    visitarSite.setIntent(acessarSite);


                    return false;
                }
            });
        }

        //Poderiamos fazer um inflate com um xml definindo os itens do menu
        //mas será feito de outra forma.
        MenuItem deletar = menu.add("Deletar");

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                alunoDAO.deletar(aluno.getId());
                carregarLista();
                Toast.makeText(ListaAlunosActivity.this, "Aluno " + aluno.getNome() + " foi deletado com sucesso!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }

    //Quando solicita permissão de algo que quer fazer algo logo depois,
    //indicando o código de requisição é possível logo após receber a permissão
    //realizar algum comportamento
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Sabendo qual a permissão está foi solicitada e atendida ou não.
        //A depois disso acionando uma ação.
        if (requestCode == CODIGO_REQUISICAO_LIGACAO) {

        }

    }

    //Caso tivesse inflado o xml, por aqui poderia achar o elemento
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//
//        if (item.getItemId() == 1) {
//            //do stuffs
//        }
//        return super.onContextItemSelected(item);
//    }
}
