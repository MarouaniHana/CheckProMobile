package iset.project.checkinpromobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import iset.project.checkinpromobile.Adapter.UserAdapter;
import iset.project.checkinpromobile.Model.User;
import iset.project.checkinpromobile.User.Apis;
import iset.project.checkinpromobile.User.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListUser extends AppCompatActivity implements  UserAdapter.onUserActionListener {

    private RecyclerView recyclerView;
    private List<User> userList;
    private UserAdapter userAdapter;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userName = getIntent().getStringExtra("userName");
        if (userName != null) {
            Toast.makeText(this, "Hello " + userName, Toast.LENGTH_SHORT).show();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Hello " + userName);
        }

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList,this);
        recyclerView.setAdapter(userAdapter);

        fetchUsers();
    }

    @Override
    public void onResume (){
        super.onResume();
        fetchUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_logout) {

            Toast.makeText(this, "Logout...", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        } else if (itemId == R.id.add) {

            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void fetchUsers() {
        UserService userService = Apis.getService();
        Call<List<User>> call = userService.getAllUser();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userList.clear();
                    userList.addAll(response.body());
                    userAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ListUser.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("MainActivity", "Error fetching users", t);
                Toast.makeText(ListUser.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onEditUser(User user) {
        showEditDialog(user);
    }

    @Override
    public void onDeleteUser(User user) {
        confirmDeleteUser(user);
    }

    private void showEditDialog(User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_user, null);

        // Déclaration des EditTexts pour chaque attribut
        EditText editNom = dialogView.findViewById(R.id.edit_user_nom);
        EditText editPrenom = dialogView.findViewById(R.id.edit_user_prenom);
        EditText editEmail = dialogView.findViewById(R.id.edit_user_email);
        EditText editUsername = dialogView.findViewById(R.id.edit_user_username);
        EditText editTelephone = dialogView.findViewById(R.id.edit_user_telephone);
        EditText editCin = dialogView.findViewById(R.id.edit_user_cin);
        EditText editDateNaissance = dialogView.findViewById(R.id.edit_user_date_naissance);
        EditText editDateDebutTravail = dialogView.findViewById(R.id.edit_user_date_debut_travail);
        EditText editPoste = dialogView.findViewById(R.id.edit_user_poste);
        EditText editAdresse = dialogView.findViewById(R.id.edit_user_adresse);

        // Remplissez les EditTexts avec les données existantes
        editNom.setText(user.getNom());
        editPrenom.setText(user.getPrenom());
        editEmail.setText(user.getEmail());
        editUsername.setText(user.getUsername());
        editTelephone.setText(user.getTelephone());
        editCin.setText(user.getCin());
        editDateNaissance.setText(user.getDateNaissance());
        editDateDebutTravail.setText(user.getDateDebutTravail());
        editPoste.setText(user.getPoste());
        editAdresse.setText(user.getAdresseComplet());

        builder.setTitle("Update user")
                .setPositiveButton("Update user", (dialog, which) -> {
                    // Mettez à jour les informations de l'utilisateur ici
                    user.setNom(editNom.getText().toString());
                    user.setPrenom(editPrenom.getText().toString());
                    user.setEmail(editEmail.getText().toString());
                    user.setUsername(editUsername.getText().toString());
                    user.setTelephone(editTelephone.getText().toString());
                    user.setCin(editCin.getText().toString());
                    user.setDateNaissance(editDateNaissance.getText().toString());
                    user.setDateDebutTravail(editDateDebutTravail.getText().toString());
                    user.setPoste(editPoste.getText().toString());
                    user.setAdresseComplet(editAdresse.getText().toString());

                    updateUser(user); // Appel à la méthode pour mettre à jour l'utilisateur dans la base de données
                })
                .setNegativeButton("Undo", (dialog, which) -> dialog.dismiss())
                .setView(dialogView)
                .create()
                .show();
    }

    private void updateUser(User user) {
        UserService userService = Apis.getService();

        userService.updateUser(user.getId(), user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    fetchUsers(); // Rafraîchir la liste des utilisateurs
                    Toast.makeText(ListUser.this, "User updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ListUser.this, "User not updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ListUser.this, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void confirmDeleteUser(User user) {
        new AlertDialog.Builder(this)
                .setTitle("Supprimer Utilisateur")
                .setMessage("Êtes-vous sûr de vouloir supprimer cet utilisateur ?")
                .setPositiveButton("Oui", (dialog, which) -> deleteUser(user))
                .setNegativeButton("Non", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void deleteUser(User user) {
        UserService userService = Apis.getService();

        userService.deleteUser(user.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    fetchUsers(); // Rafraîchir la liste des utilisateurs
                    Toast.makeText(ListUser.this, "Utilisateur supprimé", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ListUser.this, "Échec de la suppression", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ListUser.this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openAddActivity(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
    public void openCongesActivity(View view) {
        Intent intent = new Intent(this, CongesActivity.class);
        startActivity(intent);
    }
    public void openTacheActivity (View view){
        Intent intent=new Intent(this,TacheActivity.class);
        startActivity(intent);
    }

    /* public void openAddActivity(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }*/
}


