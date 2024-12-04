package iset.project.checkinpromobile.Adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import iset.project.checkinpromobile.R;

public class CongesViewHolder extends RecyclerView.ViewHolder {
    TextView textViewDate, textViewDuree, textViewTitre;
    ImageButton editButton, deleteButton;

    public CongesViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewDate = itemView.findViewById(R.id.tvDate);
        textViewDuree = itemView.findViewById(R.id.tvDuree);
        textViewTitre = itemView.findViewById(R.id.tvTitre);
        editButton = itemView.findViewById(R.id.btnEdit);
        deleteButton = itemView.findViewById(R.id.btnDelete);
    }
}
