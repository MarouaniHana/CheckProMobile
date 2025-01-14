package iset.project.checkinpromobile.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import iset.project.checkinpromobile.Model.User;
import iset.project.checkinpromobile.R;

public class UserViewHolder extends RecyclerView.ViewHolder {
    private ImageView userImage;
    private TextView firstName;
    private TextView lastName;

    private ImageButton btnEdit;
    private ImageButton btnDelete;
    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        userImage = itemView.findViewById(R.id.user_image);
        firstName = itemView.findViewById(R.id.fn);
        lastName = itemView.findViewById(R.id.ln);
        btnEdit = itemView.findViewById(R.id.edit_icon);
        btnDelete = itemView.findViewById(R.id.delete_icon);
    }

    public void bind(User user, View.OnClickListener onEditClickListener,
                     View.OnClickListener onDeleteClickListener) {
        firstName.setText(user.getNom());
        lastName.setText(user.getPrenom());

        userImage.setOnClickListener(v -> showUserInfoDialog(user, v));
        // Ajout des listeners pour les boutons
        btnEdit.setOnClickListener(onEditClickListener);
        btnDelete.setOnClickListener(onDeleteClickListener);
    }



    private void showUserInfoDialog(User user, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View dialogView = inflater.inflate(R.layout.dialog_user_info, null);
        ImageView dialogImage = dialogView.findViewById(R.id.dialog_user_image);
        TextView dialogInfo = dialogView.findViewById(R.id.dialog_user_info);

        dialogInfo.setText("Name: " + user.getNom()
                + "\nPrenom: " + user.getPrenom()
                + "\nEmail: " + user.getEmail()
                + "\nUsername: " + user.getUsername()
                + "\nTelephone: " + user.getTelephone()
                + "\nCIN: " + user.getCin()
                + "\nDate Naissance: " + user.getDateNaissance()
                + "\nDate Debut Travail: " + user.getDateDebutTravail()
                + "\nPoste: " + user.getPoste()
                + "\nAdresse: " + user.getAdresseComplet());

        builder.setView(dialogView)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }


}
