package iset.project.checkinpromobile.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iset.project.checkinpromobile.Model.User;
import iset.project.checkinpromobile.R;


public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    List<User> list;

    //LISTNER ajouter
    private onUserActionListener actionListener;
    public UserAdapter(List<User> list ,onUserActionListener actionListener ) {
        this.list = list;
        this.actionListener=actionListener;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user=list.get(position);
        holder.bind(user, v -> actionListener.onEditUser(user), v -> actionListener.onDeleteUser(user));
    }



    @Override
    public  int getItemCount() {
        return list.size();
    }

    //Interface des listeners
    public interface onUserActionListener {
        void onEditUser(User user);
        void onDeleteUser(User user);
    }


    public void updateList(List<User> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }



}