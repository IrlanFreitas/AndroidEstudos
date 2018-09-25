package br.com.alura.agenda.adapters;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.models.Aluno;

public class AlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos;
    //Poderia trabalhar com ListaAlunosActivity porem está trabalhando de forma mais generica.
    private final Context contexto;

    public AlunosAdapter(Context contexto, List<Aluno> alunos) {
        this.contexto = contexto;
        this.alunos = alunos;
    }

    //Diz quantos itens tem na lista, lista.size()
    @Override
    public int getCount() {
        //Necessário para controle de contagem
        return alunos.size();
    }

    /* Entendimento do getItem e getItemId */
    //getItem e getItemId, são os mesmos relacionados abaixo com o onItemClick
    //recebidos como paramêtro.

    //    listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    //        @Override
    //        public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
    //
    //            Aluno aluno = (Aluno) lista.getItemAtPosition(position);
    //            Intent vaiParaFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
    //            vaiParaFormulario.putExtra("aluno", aluno);
    //            startActivity(vaiParaFormulario);
    //        }
    //    });

    //Utilizado para compreender os clicks como getItemAtPosition, etc...
    @Override
    public Aluno getItem(int position) {
        return alunos.get(position);
    }

    //Utilizado para compreender os clicks como getItemAtPosition, etc...
    @Override
    public long getItemId(int position) {
        // Resolvendo de uma forma que não irá afetar o desenvolvimento da aplicação
        return 0;
    }

    //Método mais importante
    //Necessário para preencher a lista visualmente com os itens
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Aluno aluno = alunos.get(position);

        /*LayoutInflater*/
        //Inflate - Ligando um xml criado para layout e o código
        //sem a necessidade de criar todos os elementos na mão
        LayoutInflater inflater = LayoutInflater.from(contexto);

        /*Inflate Método Paramêtros*/
        //Paramêtros do LayoutInflater.inflate
        //  R.layout.list_item - Layout que será inflado
        //  parent - Pai do layout, isso faz com que seja preservado o layout desenhado por conta
        //das referencias, porém é necessário controle, pois é tomada uma exception de repetição
        //de elementos na lista.
        //  false (attachToRoot) - Agora que a lista já sabe que é o pai dela, fica tentando
        //adiantar os elementos e repetindo, o false proíbe isso, o que faz
        //não tomar exception.

        /*ConvertView - Reciclagem dos Elementos de Layout*/
        //Seria como reciclagem das instancias do layout da lista, para não onerar
        //o dispositivo do usuário.
        View view = convertView;
        if (convertView == null) {

            /* Qual o tipo de layout apropriado  */
            //Não há necessidade de mudança de código o próprio
            //SO vai saber o melhor momento de aplicar cada layout
            //pois, estão em pastas diferentes um é somente layout
            //e o outros é layout-land, porem com o mesmo nome de arquivo, list_item
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        //Procurando dentro do layout list_item, todos os elementos já criados
        TextView campoNome = view.findViewById(R.id.list_item_nome);
        TextView campoTelefone = view.findViewById(R.id.list_item_telefone);
        ImageView imagemAluno = view.findViewById(R.id.list_item_imagem);
        TextView campoEndereco = view.findViewById(R.id.list_item_endereco );
        TextView campoSite = view.findViewById(R.id.list_item_site );

        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        if (aluno.getCaminhoFoto() != null) {
            imagemAluno.setImageBitmap(carregarImagem(aluno.getCaminhoFoto()));
            imagemAluno.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        /*Entendendo qual a orientação e aplicando a modificação*/
        /* Esse jeito que fiz não funcionou :( */
        //if (contexto.getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ) {
        //    TextView campoEndereco = view.findViewById(R.id.list_item_endereco );
        //    TextView campoSite = view.findViewById(R.id.list_item_site );
        //    campoEndereco.setText(aluno.getEndereco());
        //    campoSite.setText(aluno.getSite());
        //}

        /* Orientação Portrait */
        //Caso ele esteja na orientação portrait
        //Os objetos que represetam os elementos
        //da tela estarão nulo, e por isso deve
        //verificar antes
        if (campoEndereco != null) {
            campoEndereco.setText(aluno.getEndereco());
        }
        if (campoSite != null) {
            campoSite.setText(aluno.getSite());
        }

        Log.i("Orientação", String.valueOf(contexto.getResources().getConfiguration().orientation) );

        return view;
    }

    @org.jetbrains.annotations.Contract("null -> null")
    private Bitmap carregarImagem(String caminhoFoto) {

        if (caminhoFoto != null) {

            //Criando um bitmap a partir do arquivo.
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);

            //Diminuindo a qualidade da imagem por limitações de software
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);

            //Mais fácil de mexer com imagens
            return bitmapReduzido;
        } else {
            return null;
        }
    }

}
