package iset.project.checkinpromobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iset.project.checkinpromobile.Model.Conges;
import iset.project.checkinpromobile.R;

public class CongesAdapter extends RecyclerView.Adapter<CongesViewHolder> {
    private final List<Conges> congesList;
    private final Context context;

    public CongesAdapter(List<Conges> congesList, Context context) {
        this.congesList = congesList;
        this.context = context;
    }

    @NonNull
    @Override
    public CongesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_conges, parent, false);
        return new CongesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CongesViewHolder holder, int position) {
        Conges conge = congesList.get(position);

        holder.textViewDate.setText(conge.getDate());
        holder.textViewDuree.setText(conge.getDuree());
        holder.textViewTitre.setText(conge.getTitre());

        // Edit Alert dialog
        holder.editButton.setOnClickListener(v -> openEditDialog(conge, position));

        // Delete Alert dialog
        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Confirm", (dialog, which) -> {
                        congesList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .create()
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return congesList.size();
    }

    private void openEditDialog(Conges conge, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_edit_conges, null);

        EditText editTextDate = dialogView.findViewById(R.id.editTextDateDialog);
        EditText editTextDuree = dialogView.findViewById(R.id.editTextDureeDialog);
        EditText editTextTitre = dialogView.findViewById(R.id.editTextTitreDialog);
        Button buttonSave = dialogView.findViewById(R.id.buttonSaveDialog);
        Button buttonCancel = dialogView.findViewById(R.id.buttonCancelDialog);

        editTextDate.setText(conge.getDate());
        editTextDuree.setText(conge.getDuree());
        editTextTitre.setText(conge.getTitre());

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        buttonSave.setOnClickListener(v -> {
            String newDate = editTextDate.getText().toString().trim();
            String newDuree = editTextDuree.getText().toString().trim();
            String newTitre = editTextTitre.getText().toString().trim();

            if (newDate.isEmpty() || newDuree.isEmpty() || newTitre.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            conge.setDate(newDate);
            conge.setDuree(newDuree);
            conge.setTitre(newTitre);
            notifyItemChanged(position);

            dialog.dismiss();
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
        });

        buttonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
