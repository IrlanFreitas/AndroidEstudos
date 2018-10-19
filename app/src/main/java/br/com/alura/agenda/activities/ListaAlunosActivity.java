package br.com.alura.agenda.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.adapters.AlunosAdapter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.dto.AlunoSync;
import br.com.alura.agenda.models.Aluno;
import br.com.alura.agenda.retrofit.RetrofitInicializador;
import br.com.alura.agenda.tasks.EnviaAlunosTask;
import br.com.alura.agenda.util.PadraoRequisicao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaAlunosActivity extends AppCompatActivity {

    private AlunoDAO alunoDAO;
    private ListView listaAlunos;

    /*Método sobrescrito baseado no ciclo de vida da Atividade (Activity)*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*Chamado caso tenha algum comportamento acontecendo anteiormente*/
        super.onCreate(savedInstanceState);

        /*Ligação da tela(xml) com o comportamento(activity)*/
        setContentView(R.layout.activity_lista_alunos);

        /* Modificação de TextView para ListView */
        // Em vez de usar o TextView para cada um dos elementos
        // melhor usar um ListView para uma lista de elementos como
        // TextView.
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
                /* Intent Explícita */
                //Declarando a intenção para o Android gerenciar.
                //Intent Explícita é declarado exatamente de onde você está e quer ir;
                Intent intentIrParaFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentIrParaFormulario);
            }
        });

        registerForContextMenu(listaAlunos);

        //Solicitando permissão de receber SMS.
        if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            //RequestPermission é o pop-up que aparece para solicitar permissão
            ActivityCompat.requestPermissions(ListaAlunosActivity.this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    PadraoRequisicao.CODIGO_REQUISICAO_RECEBER_SMS);
        }

        /* Solicitando permissão de acessar internet. - Não é necessário */
        // if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.INTERNET)
        //         != PackageManager.PERMISSION_GRANTED) {
        //
        //     //RequestPermission é o pop-up que aparece para solicitar permissão
        //     ActivityCompat.requestPermissions(ListaAlunosActivity.this,
        //             new String[]{Manifest.permission.INTERNET},
        //             PadraoRequisicao.CODIGO_REQUISICAO_INTENET);
        // }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Call<AlunoSync> callAlunos = new RetrofitInicializador().getAlunoService().getAlunos();

        callAlunos.enqueue(new Callback<AlunoSync>() {
            @Override
            public void onResponse(Call<AlunoSync> call, Response<AlunoSync> response) {
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);

                AlunoSync aluno = response.body();

                dao.sincronizar(aluno.getAlunos());

                carregarLista();
            }

            @Override
            public void onFailure(Call<AlunoSync> call, Throwable t) {

            }
        });

        carregarLista();
    }

    private void carregarLista() {

        /*Sobre o ArrayAdapter */
        //necessário passar no construtor, o contexto - que é a atual Activity
        //depois o layout da lista, e como não temos passarei um layout pronto do próprio android
        //e depois a fonte de dados

        //android.R.layout.simple_list_item_1 - Significa o layout de um item da lista, como o textview que apareceu

        //Como era usando antes da modificação de layout da lista, ou melhor, de cada item dela
        //ArrayAdapter<Aluno> adapter = new Arr*ayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunoDAO.getAlunos());

        List<Aluno> alunos = alunoDAO.getAlunos();

        AlunosAdapter adapter = new AlunosAdapter(this, alunos);

        for(Aluno aluno: alunos) {
            Log.i("Alunos Id", "carregarLista - nome: "+ aluno.getNome() + " -  id:" + aluno.getId());
        }

        //Adicionando o Adapter a ListView
        listaAlunos.setAdapter(adapter);
    }

    /* Personalização do Menu de Contexto */
    //Depois de registrado para o menu de contexto para a determinada view pela forma:
    //
    //registerForContextMenu(listaAlunos);
    //
    //As ações do menu de contexto são personalizadas, por esse método.

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
                            PadraoRequisicao.CODIGO_REQUISICAO_LIGACAO);
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

        //Uma opção do menu somente para controle
        MenuItem informacoes = menu.add("Informações");
        Intent intentVaiParaInformacoes = new Intent(ListaAlunosActivity.this, InformacoesActivity.class);
        intentVaiParaInformacoes.putExtra("aluno", aluno);
        informacoes.setIntent(intentVaiParaInformacoes);


        //Poderiamos fazer um inflate com um xml definindo os itens do menu
        //mas será feito de outra forma.
        MenuItem deletar = menu.add("Deletar");

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                new AlertDialog.Builder(ListaAlunosActivity.this)
                        .setTitle("Deletando aluno")
                        .setMessage("Tem certeza que deseja deletar o contato do aluno? ")
                        .setNegativeButton("Não", null)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                alunoDAO.deletar(aluno.getId());
                                carregarLista();
                                Toast.makeText(ListaAlunosActivity.this, "Aluno " + aluno.getNome() + " foi deletado com sucesso!", Toast.LENGTH_SHORT).show();

                            }
                        }).show();

                return false;
            }
        });


    }

    /* Controlar a ação depois de consedida ou não uma permissão */
    //Quando solicita permissão de algo que quer fazer algo logo depois,
    //indicando o código de requisição é possível logo após receber a permissão
    //realizar algum comportamento

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Sabendo qual a permissão está foi solicitada e atendida ou não.
        //A depois disso acionando uma ação.
        if (requestCode == PadraoRequisicao.CODIGO_REQUISICAO_LIGACAO) {

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /* Menu Inflater */
        // Inflar, juntar o xml já criado com o código java
        // sem precisar criar os elementos na mão
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /* Configurando cada botão */
    //Para isso é importante dar os devidos ids para os itens do menu
    //e com isso controlar a ação de cada um dos botões pelo id.

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_lista_alunos_enviar_notas:

                /* Executando o método - doInBackground */
                new EnviaAlunosTask(this).execute();

                break;

            case R.id.menu_lista_alunos_baixar_provas:

                /*Intent irParaProvas = new Intent(this, ProvasActivity.class);
                startActivity(irParaProvas);*/

                Intent irParaProvas = new Intent(this, ProvasActivity.class);
                startActivity(irParaProvas);

                break;

            case R.id.menu_lista_alunos_visualizar_mapa:

                Intent irParaMapa = new Intent(this, MapaActivity.class);
                startActivity(irParaMapa);

                break;
        }

        return super.onOptionsItemSelected(item);
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
