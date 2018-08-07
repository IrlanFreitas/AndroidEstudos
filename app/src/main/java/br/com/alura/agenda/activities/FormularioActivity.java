package br.com.alura.agenda.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.alura.agenda.BuildConfig;
import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.helpers.FormularioHelper;
import br.com.alura.agenda.models.Aluno;
import br.com.alura.agenda.util.PadraoRequisicao;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;

    private AlunoDAO alunoDAO;

    private String caminhoFoto;

    private Bitmap imagemAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        alunoDAO = new AlunoDAO(this);

        //getIntent() devolve a intent que foi usada para abrir a activity
        Aluno aluno = (Aluno) getIntent().getSerializableExtra("aluno");

        if (aluno != null) {
            Log.i("ALUNO", aluno.toString());
            helper.preencherFormulario(aluno);
        }

        Button btnAdicionarFoto = findViewById(R.id.formulario_btn_adicionar_foto);

        btnAdicionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File foto = new File(caminhoFoto);
                //Modificações necessárias para a API 24, Nougat.
                Uri fotoURI = FileProvider.getUriForFile(FormularioActivity.this, BuildConfig.APPLICATION_ID + ".provider", foto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoURI);
                startActivityForResult(intentCamera, PadraoRequisicao.CODIGO_REQUISICAO_CAMERA);

            }
        });

        //
        //Setando o comportamento de click do botão com o listener(escutador)
        // de evento de click, poderia ser outros, double click, long click*/
        // findViewById(R.id.formulario_btn_salvar).setOnClickListener(new View.OnClickListener() {
        //  @Override
        //   public void onClick(View view) { }
        //});



    }

    //Modificando a ActionBar assim que é criada
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflar é transformar o xml em view
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_formulario, menu);

        //Verificar o que a indicação do retorno faz;
        return super.onCreateOptionsMenu(menu);
    }

    //Sobrecrevendo o método que é chamado quando um item de menu da Action Bar é clicado
    //Forma mais elegante de fazer, poderia ter pego o id do item de menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.formulario_menu_item_adicionar: {

                String acao = "";

                Aluno aluno = helper.getAluno();
                if (aluno.getId() == null) {
                    alunoDAO.inserir(aluno);
                    acao = " Cadastrado com sucesso. ";
                } else {
                    alunoDAO.atualizar(aluno);
                    acao = " Atualizado com sucesso. ";
                }

                //Mensagem que aparece abaixo como uma notificação
                //FormularioActivity.this = view.getContext()
                Toast.makeText(FormularioActivity.this, acao, Toast.LENGTH_SHORT).show();

                //Finalizando a activity com o conceito de pilha do Android
                finish();

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //Recebendo o resultado da chamada do método startActivityForResult
    //que recebe valor, objeto, arquivo etc... De outra activity que
    //foi chamada pela Intent ímplicita, onde as outras actitivies
    //com o intent-filter correto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Se ele tiver completado a ação
        if (resultCode == Activity.RESULT_OK) {
            //Se a ação for da camera
            if (requestCode == PadraoRequisicao.CODIGO_REQUISICAO_CAMERA ) {

                //Delegado ao helper
                imagemAluno = helper.carregarImagem(caminhoFoto);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable("imagemAluno", imagemAluno);
        outState.putString("caminhofoto", caminhoFoto);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        caminhoFoto = savedInstanceState.getString("caminhofoto");
        imagemAluno = savedInstanceState.getParcelable("imagemAluno");
        helper.inserirImagem(caminhoFoto, imagemAluno);
    }
}
