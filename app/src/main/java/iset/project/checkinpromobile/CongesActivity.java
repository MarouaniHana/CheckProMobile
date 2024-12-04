package iset.project.checkinpromobile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import iset.project.checkinpromobile.Adapter.CongesAdapter;
import iset.project.checkinpromobile.Model.Conges;

public class CongesActivity extends AppCompatActivity {
    private EditText editTextDate, editTextDuree, editTextTitre;
    private RecyclerView recyclerView;
    private List<Conges> congesList;
    private CongesAdapter congesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conges);

        editTextDate = findViewById(R.id.editTextDate);
        editTextDuree = findViewById(R.id.editTextDuree);
        editTextTitre = findViewById(R.id.editTextTitre);
        recyclerView = findViewById(R.id.recyclerViewConges);

        congesList = new ArrayList<>();
        congesAdapter = new CongesAdapter(congesList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(congesAdapter);
    }

    public void enregistrerDonnees(View view) {
        String date = editTextDate.getText().toString().trim();
        String duree = editTextDuree.getText().toString().trim();
        String titre = editTextTitre.getText().toString().trim();

        if (date.isEmpty() || duree.isEmpty() || titre.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Conges conge = new Conges(date, duree, titre);
        congesList.add(conge);
        congesAdapter.notifyDataSetChanged();

        editTextDate.setText("");
        editTextDuree.setText("");
        editTextTitre.setText("");

        Toast.makeText(this, "Leave added", Toast.LENGTH_SHORT).show();
    }
}
