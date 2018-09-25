package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.alura.agenda.models.Aluno;

public class AlunoDAO extends SQLiteOpenHelper {

    private static final String TABELA = "ALUNOS";

    public AlunoDAO(Context context) {
        //Agenda - Nome do banco, não tabela.
        //Depois da modificação da tabela tem que alterar a versão.
        super(context, "Agenda", null, 5);
    }

    //Quando se usa a aplicação pela primeira vez.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = " CREATE TABLE Alunos " +
                "(id CHAR(36) PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "telefone TEXT, " +
                "endereco TEXT, " +
                "site TEXT, " +
                "nota REAL, " +
                "caminhoFoto TEXT); ";

        sqLiteDatabase.execSQL(sql);

    }

    //Quando se modifica a aplicação e já existem usuários utilizando.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        /*Utilizado na primeira versão*/
        //Quando não se tinha dados e usuários utilizando
        //String sql = "DROP TABLE IF EXISTS Alunos";
        //onCreate(sqLiteDatabase);

        /*Uma forma elegante de atualizar as versões*/
        //dos usuários que utilizam o app.


        switch (oldVersion) {
            case 2:

                //Depois de adicionada a nova coluna
                String sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT; ";
                sqLiteDatabase.execSQL(sql);

            case 3:

                //Atualizando o modelo de dados para o UUID
                //Como não há como alterar, é necessário uma nova tabela
                String sqlTabelaNova = " CREATE TABLE Alunos_Novo " +
                        "( id CHAR(36) PRIMARY KEY, " +
                        "nome TEXT NOT NULL, " +
                        "telefone TEXT, " +
                        "endereco TEXT, " +
                        "site TEXT, " +
                        "nota REAL, " +
                        "caminhoFoto TEXT ); ";

                sqLiteDatabase.execSQL(sqlTabelaNova);

                String sqlInserindoAlunosNaTabelaNova = " INSERT INTO Alunos_Novo " +
                        "(id, nome, telefone, endereco, site, nota, caminhoFoto) " +
                        " SELECT id, nome, telefone, endereco, site, nota, caminhoFoto " +
                        " FROM Alunos ";

                sqLiteDatabase.execSQL(sqlInserindoAlunosNaTabelaNova);

                String removendoTabelaAntiga = " DROP TABLE Alunos ";

                sqLiteDatabase.execSQL(removendoTabelaAntiga);

                String renomeandoTabela = " ALTER TABLE Alunos_Novo RENAME TO Alunos ";

                sqLiteDatabase.execSQL(renomeandoTabela);

            case 4:

                //Inserindo o UUID logo depois do modelo estar atualizado

                String obterAlunos  = " SELECT * FROM Alunos ";

                Cursor cursor = sqLiteDatabase.rawQuery(obterAlunos, null);

                List<Aluno> alunos = getAlunosByCursor(cursor);

                String atualizacaoIdsAlunos = " UPDATE Alunos SET id = ? WHERE id = ? ";

                for (Aluno aluno: alunos) {
                    sqLiteDatabase.execSQL(atualizacaoIdsAlunos, new String[]{ geraUUID() , aluno.getId()});
                }


        }

    }

    private String geraUUID() {

        return UUID.randomUUID().toString();
    }

    public void inserir(Aluno aluno){

        SQLiteDatabase writable = getWritableDatabase();

        ContentValues values = pegaDados(aluno);

        long idGerado = writable.insert("Alunos", null, values);

        // Controlando a geração de id's pelo SQLite
        //aluno.setId(idGerado);

        close();
    }

    public List<Aluno> getAlunos() {

        SQLiteDatabase readable = getReadableDatabase();

        Cursor cursor = readable.rawQuery("SELECT * FROM Alunos", null);

        return getAlunosByCursor(cursor);
    }

    @NonNull
    private List<Aluno> getAlunosByCursor(Cursor cursor) {

        List<Aluno> alunos = new ArrayList<>();

        while (cursor.moveToNext()) {

            Aluno aluno = new Aluno();
            aluno.setId(  cursor.getString(cursor.getColumnIndex("id")) );
            aluno.setNome( cursor.getString(cursor.getColumnIndex("nome")) );
            aluno.setEndereco( cursor.getString(cursor.getColumnIndex("endereco")) );
            aluno.setTelefone( cursor.getString(cursor.getColumnIndex("telefone")) );
            aluno.setSite( cursor.getString(cursor.getColumnIndex("site")));
            aluno.setNota( cursor.getDouble(cursor.getColumnIndex("nota")));
            aluno.setCaminhoFoto( cursor.getString(cursor.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);
        }
        cursor.close();


        return alunos;
    }

    public void deletar(String alunoId) {
        SQLiteDatabase writable = getWritableDatabase();

        writable.delete("Alunos", "id = ?", new String[]{alunoId.toString()});
        close();
    }

    public void atualizar(Aluno aluno) {
        SQLiteDatabase writable = getWritableDatabase();

        ContentValues values = pegaDados(aluno);

        writable.update(TABELA, values, "id = ?", new String[]{aluno.getId().toString()});
        close();
    }

    public Aluno encontrarPorTelefone(String telefone) {

        SQLiteDatabase writable = getWritableDatabase();

        String sql = "SELECT * FROM " + TABELA +
                " WHERE telefone = ? ";

        Cursor cursor = writable.rawQuery(sql, new String[]{telefone});

        Aluno aluno = null;

        while (cursor.moveToNext()) {

            aluno = new Aluno();
            aluno.setId(  cursor.getString(cursor.getColumnIndex("id")) );
            aluno.setNome( cursor.getString(cursor.getColumnIndex("nome")) );
            aluno.setEndereco( cursor.getString(cursor.getColumnIndex("endereco")) );
            aluno.setTelefone( cursor.getString(cursor.getColumnIndex("telefone")) );
            aluno.setSite( cursor.getString(cursor.getColumnIndex("site")));
            aluno.setNota( cursor.getDouble(cursor.getColumnIndex("nota")));
            aluno.setCaminhoFoto( cursor.getString(cursor.getColumnIndex("caminhoFoto")));

        }
        cursor.close();
        close();
        return aluno;
    }

    @NonNull
    private ContentValues pegaDados(Aluno aluno) {

        ContentValues values = new ContentValues();

        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("caminhoFoto", aluno.getCaminhoFoto());

        return values;
    }


}
