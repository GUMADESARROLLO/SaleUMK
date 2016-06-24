package com.videumcorp.desarrolladorandroid.salesumk.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.videumcorp.desarrolladorandroid.salesumk.Lib.Anime;
import com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrarywithfragments.R;

import java.util.List;

/**
 * Created by marangelo.php on 13/06/2016.
 */
public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {
    private List<Anime> items;

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView visitas;

        public AnimeViewHolder(View v) {
            super(v);
            /*imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.nombre);
            visitas = (TextView) v.findViewById(R.id.visitas);*/
        }
    }

    public AnimeAdapter(List<Anime> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_detalles, viewGroup, false);
        return new AnimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AnimeViewHolder viewHolder, int i) {
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.visitas.setText("Visitas:"+String.valueOf(items.get(i).getVisitas()));
    }
}
