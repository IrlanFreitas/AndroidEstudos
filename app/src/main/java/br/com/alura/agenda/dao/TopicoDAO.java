package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.models.Topico;

public class TopicoDAO extends SQLiteOpenHelper {

    private static final Integer VERSAO = 1;
    private static final String DATABASE = "Agenda";
    private static final String TABELA = "Topico";

    public TopicoDAO( Context context ) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Topicos" +
                "(id INTEGER PRIMARY KEY," +
                 "nome TEXT NOT NULL); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE Topicos");

        onCreate(sqLiteDatabase);
    }

    public void inserir(Topico topico) {
        SQLiteDatabase writable = getWritableDatabase();

        ContentValues dados = new ContentValues();

        dados.put("nome", topico.getNome());

        writable.insert(TABELA, null, dados );

        close();
    }

    public List<Topico> getTopicos() {

        SQLiteDatabase readable = getReadableDatabase();

        String sql = "SELECT * FROM Topicos";

        List<Topico> topicos = new ArrayList<>();

        Cursor cursor = readable.rawQuery(sql, null);

        while (cursor.moveToNext()) {

            Topico topico = new Topico();
            topico.setId( cursor.getLong(cursor.getColumnIndex("id")) );
            topico.setNome( cursor.getString(cursor.getColumnIndex("nome")) );

            topicos.add(topico);

        }

        cursor.close();
        close();

        return topicos;
    }
}
