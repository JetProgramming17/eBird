package com.example.ebird;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebird.Models.NearbyVariables;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.viewHolder> {

    Context context;
    List<NearbyVariables> nearbyList;
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener = clickListener;
    }

    public NearbyAdapter(Context context, List<NearbyVariables> nearbyList) {
        this.context = context;
        this.nearbyList = nearbyList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.nearby_list, parent, false);
        return new viewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder,  int position) {

        NearbyVariables nearbyVariables=nearbyList.get(position);
        holder.ComName.setText(nearbyVariables.getComName());
        holder.SciName.setText(nearbyVariables.getSciName());
        holder.LocName.setText(nearbyVariables.getLocName());

    }

    @Override
    public int getItemCount() {
        return nearbyList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{


        TextView ComName,SciName,LocName;
        ImageView isFavorite;
        public viewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            ComName=itemView.findViewById(R.id.text_birdname);
            SciName=itemView.findViewById(R.id.text_specie);
            LocName=itemView.findViewById(R.id.text_birdLocation);
            isFavorite=itemView.findViewById(R.id.ivFavorite);

            isFavorite.setOnClickListener((v) -> saveData());
        }
        void saveData() {
            String cName = ComName.getText().toString();
            String s_name = SciName.getText().toString();
            String l_name = LocName.getText().toString();

            NearbyVariables nearbyVariables = new NearbyVariables();
            nearbyVariables.setComName(cName);
            nearbyVariables.setSciName(s_name);
            nearbyVariables.setLocName(l_name);

            saveDataToFirebase(nearbyVariables);
        }
        void saveDataToFirebase(NearbyVariables nearbyVariables){
            DocumentReference documentReference;
            documentReference = Utility.getCollectionReferenceForData().document();

            documentReference.set(nearbyVariables).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Not saved!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void setData(List<NearbyVariables>data){
        this.nearbyList = data;
        notifyDataSetChanged();
    }
}
