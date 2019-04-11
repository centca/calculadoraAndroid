package com.example.myapplication2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;


public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaAdapter.NoticiaViewHolder>{
        private List<Noticia> items;

        public class NoticiaViewHolder extends RecyclerView.ViewHolder {
            public TextView nombre;
            public Button href;
            public View vie;


            public NoticiaViewHolder(View v) {
                super(v);
                nombre = (TextView) v.findViewById(R.id.nombre);
                href = (Button) v.findViewById(R.id.button2);
                vie = (View) v.findViewById(R.id.view);
            }
        }
        public NoticiaAdapter(List<Noticia> items) {
            this.items = items;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public NoticiaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.noticia_card, viewGroup, false);
            return new NoticiaViewHolder(v);
        }
        @Override
        public void onBindViewHolder(NoticiaViewHolder viewHolder, final int i) {
            viewHolder.nombre.setText(items.get(i).getNombre());
            //viewHolder.href.setOnClickListener(MainActivity.goToUrl(items.get(i).getHref()));
            viewHolder.href.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    MainActivity.goToUrl(v,items.get(i).getHref());
                }
            });

        }





}

