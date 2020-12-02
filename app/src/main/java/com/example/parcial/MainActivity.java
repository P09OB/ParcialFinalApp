package com.example.parcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseDatabase db;
    private TextView tv;
    private EditText calificacion;
    private Button send;
    private String votantes, suma, pregunta, idRecibido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseDatabase.getInstance();

        tv = findViewById(R.id.tv);
        calificacion = findViewById(R.id.puntaje);
        send = findViewById(R.id.addCalificacion);

        send.setOnClickListener(this);
        calificacion.setEnabled(false);
        send.setEnabled(false);


        loadData();


    }

    private void loadData() {

        db.getReference().child("preguntas").child("actuales").addValueEventListener(

                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot data) {

                        for(DataSnapshot child: data.getChildren() ){

                            Id id =  child.getValue(Id.class);
                            idRecibido = id.getId();

                            Log.e("code",""+idRecibido);

                            db.getReference().child("preguntas").child("actuales").child(idRecibido).addValueEventListener(

                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange( DataSnapshot data) {

                                            if(data.hasChildren()){
                                                calificacion.setEnabled(true);
                                                send.setEnabled(true);

                                                Question question = data.getValue(Question.class);
                                                tv.setText("");
                                                tv.append(question.getPregunta());
                                                pregunta = question.getPregunta();
                                                votantes = question.getVotantes();
                                                suma = question.getSumatoria();

                                            } else {
                                                calificacion.setEnabled(false);
                                                send.setEnabled(false);

                                                tv.setText("No hay pregunta");

                                            }




                                        }

                                        @Override
                                        public void onCancelled( DatabaseError error) {


                                        }
                                    }
                            );

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                }
        );






    }

    @Override
    public void onClick(View view) {

        String puntaje = calificacion.getText().toString();

        if(puntaje.equals("")) {

            Toast.makeText(this,"El campo esta vacio",Toast.LENGTH_LONG).show();

        } else {

            int puntaje1 = Integer.parseInt(puntaje);

            calificacion.setText("");

            if(puntaje1 > 10 || puntaje1 < 0){

                Toast.makeText(this,"Digite un número del 0 al 10",Toast.LENGTH_LONG).show();
            } else {


                int numVotantes = Integer.parseInt(votantes)+1;

                int numSuma = Integer.parseInt(suma)+puntaje1;

                float total = (float)numSuma/numVotantes;

                float resultado = Math.round(total);

                DatabaseReference ref = db.getReference().child("preguntas").child("actuales").child(idRecibido);

                String textVotantes = String.valueOf(numVotantes);
                String textSuma = String.valueOf(numSuma);
                String textTotal = String.valueOf(resultado);

                Question que = new Question(

                        idRecibido,
                        pregunta,
                        textTotal,
                        textSuma,
                        textVotantes

                );

                ref.setValue(que);

            }

        }



    }
}