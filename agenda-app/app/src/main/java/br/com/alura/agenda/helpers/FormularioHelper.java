package br.com.alura.agenda.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.alura.agenda.R;
import br.com.alura.agenda.activities.FormularioActivity;
import br.com.alura.agenda.models.Aluno;
import br.com.alura.agenda.util.CarregadorDeFoto;

public class FormularioHelper {

    //Ter os campos aqui é importante para obter os dados quando necessário
    //sem precisar instanciar novamente o helper na activity
    private final EditText editTextNome;
    private final EditText editTextEndereco;
    private final EditText editTextTelefone;
    private final EditText editTextSite;
    private final RatingBar ratingNota;
    private final ImageView imagemAluno;

    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity) {

        editTextNome = activity.findViewById(R.id.formulario_nome);
        editTextEndereco = activity.findViewById(R.id.formulario_endereco);
        editTextTelefone = activity.findViewById(R.id.formulario_telefone);
        editTextSite = activity.findViewById(R.id.formulario_site);
        ratingNota = activity.findViewById(R.id.formulario_nota);
        imagemAluno = activity.findViewById(R.id.formulario_foto_aluno);
        aluno = new Aluno();

    }

    public Aluno getAluno() {
        aluno.setNome(editTextNome.getText().toString());
        aluno.setEndereco(editTextEndereco.getText().toString());
        aluno.setTelefone(editTextTelefone.getText().toString());
        aluno.setSite(editTextSite.getText().toString());
        aluno.setNota((double) ratingNota.getRating());
        //Foi anexada o caminho da foto ao objeto ImageView pois
        //ele não salva o caminho.
        aluno.setCaminhoFoto((String) imagemAluno.getTag());

        return aluno;
    }

    public void preencherFormulario(Aluno aluno) {
        this.aluno = aluno;
        editTextNome.setText(aluno.getNome());
        editTextEndereco.setText(aluno.getEndereco());
        editTextTelefone.setText(aluno.getTelefone());
        editTextSite.setText(aluno.getSite());
        ratingNota.setRating(Float.parseFloat(aluno.getNota().toString()));
        carregarImagem(aluno.getCaminhoFoto());

    }

    public Bitmap carregarImagem(String caminhoFoto) {

        if (caminhoFoto != null) {
            //Criando um bitmap a partir do arquivo.
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);

            //Bitmap bitmap = CarregadorDeFoto.carrega(caminhoFoto);

            //Diminuindo a qualidade da imagem por limitações de software
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);

            inserirImagem(caminhoFoto, bitmapReduzido);


            return bitmapReduzido;
        }

        return null;
    }

    public void inserirImagem(String caminhoFoto, Bitmap bitmapReduzido) {
        //Mais fácil de mexer com imagens
        imagemAluno.setImageBitmap(bitmapReduzido);

        //Encaixando a imagem na altura e largura disponível no imagem view.
        imagemAluno.setScaleType(ImageView.ScaleType.FIT_XY);

        //o ImageView não salva o caminho da foto,
        //somente o bitmap, porém, há um método
        //que pode ser utilizado como auxiliar
        //o setTag, que funciona como o PutExtra
        //para anexar informações ao objeto
        imagemAluno.setTag(caminhoFoto);
    }
}
