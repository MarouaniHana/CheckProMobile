package iset.project.checkinpromobile.Adapter;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import iset.project.checkinpromobile.Model.Tache;
import iset.project.checkinpromobile.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView t1, t2;
    View editbtn, deletebtn;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        t1 = itemView.findViewById(R.id.n);
        t2 = itemView.findViewById(R.id.p);
        editbtn = itemView.findViewById(R.id.btnedit);
        deletebtn = itemView.findViewById(R.id.btndelete);
    }

    public void bind(Tache tache) {
        t1.setText(tache.getNomt());
        t2.setText(tache.getDescrption());
    }


}
