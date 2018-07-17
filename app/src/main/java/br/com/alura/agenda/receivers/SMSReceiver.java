package br.com.alura.agenda.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.models.Aluno;

/*Tratando eventos Broadcast e Declarando no AndroidManifext.xml*/
//Indicando que a classe é um broadcast receiver
//ela pode receber qualquer evento do sistema
//e tratar,
//Necessário também declarar no AndroidManifest com a tag
//receiver
public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        /* Entendendo porque array de object para PDU */
        //PDU - Podem ser divididos para envio de mensagem muito grande
        //limite seria 160 caracteres, caso passe disso é necessário
        //mais uma mensagem, por conta disso, array de obcejt;
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");

        /* Cabeçalho da mensagem */
        //Obtendo somente o cabecalho da mensagem que contém os dados
        //de quem enviou, o que importa.
        byte[] pdu = (byte[]) pdus[0];
        String formato = intent.getStringExtra("format");

        /* PDU - Protocol Data Unit */
        SmsMessage sms = SmsMessage.createFromPdu(pdu, formato);

        AlunoDAO alunoDAO = new AlunoDAO(context);

        /* Obtendo o numero de quem enviou a mensagem */
        String telefone = sms.getDisplayOriginatingAddress();

        Aluno aluno = alunoDAO.encontrarPorTelefone(telefone);

        if (aluno != null) {
            Toast.makeText(context, "Chegou um sms do aluno " + aluno.getNome().split(" ")[0] , Toast.LENGTH_LONG).show();

            /* Tocando uma música diferente */
            //Tocando uma música diferente a partir de
            // um arquivo já guardado
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }


    }
}
