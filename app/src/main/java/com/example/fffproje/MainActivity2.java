package com.example.fffproje;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    public static veritabani veritabanim;
    ListView list_view;

    int images[] = {R.drawable.ic_resim};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        list_view = findViewById(R.id.list_view);

        veritabanim = Room.databaseBuilder(getApplicationContext(), veritabani.class, "notdb")
                .allowMainThreadQueries().build();

        Button btnNotEkle = findViewById(R.id.btnnotEkle);
        btnNotEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), notEkle.class);
                startActivity(intent);
            }
        });

        List<Not> notlar = MainActivity2.veritabanim.dao().getNot();
        ArrayList<String> arrayList = new ArrayList<>();

        ArrayList<String> n = new ArrayList<>();
        ArrayList<String> s = new ArrayList<>();
        ArrayList<String> t = new ArrayList<>();

        for (Not nt : notlar) {
            int id = nt.getId();
            String nnotsaat = nt.getNotsaat();
            String nnottarih = nt.getNottarih();
            String nnot = nt.getNot();

            arrayList.add(nnot);
            n.add(nnot);
            s.add(nnotsaat);
            t.add(nnottarih);
        }

        MyAdapter adapter = new MyAdapter(this, n, s, t, images);
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                AlertDialog.Builder diyalogOlusturucu = new AlertDialog.Builder(MainActivity2.this);

                String listeid = String.valueOf(l);

                diyalogOlusturucu.setMessage("Not Silinsin mi?")
                        .setCancelable(false)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                VeriSil(position);
                            }
                        }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                diyalogOlusturucu.create().show();
            }
        });
    }

    private void VeriSil(int position) {
        List<Not> notlarsil = MainActivity2.veritabanim.dao().getNot();

        int id = notlarsil.get(position).getId();

        Not not = new Not();
        not.setId(id);

        MainActivity2.veritabanim.dao().notSil(not);

        Toast.makeText(getApplicationContext(), "Not Silindi", Toast.LENGTH_SHORT).show();
        finish();
        startActivity(getIntent());
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> rNot;
        ArrayList<String> rSaat;
        ArrayList<String> rTarih;
        int rImgs[];

        MyAdapter(Context c, ArrayList<String> not, ArrayList<String> saat, ArrayList<String> tarih, int imgs[]) {
            super(c, R.layout.custom_view, R.id.txt_not, not);
            this.context = c;
            this.rNot = not;
            this.rSaat = saat;
            this.rTarih = tarih;
            this.rImgs = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.custom_view, parent, false);
            ImageView imgResim = row.findViewById(R.id.resim);
            TextView txtNot = row.findViewById(R.id.txt_not);
            TextView txtSaat = row.findViewById(R.id.txt_saat);
            TextView txtTarih = row.findViewById(R.id.txt_tarih);

            imgResim.setImageResource(rImgs[0]);
            txtNot.setText(rNot.get(position));
            txtSaat.setText(rSaat.get(position));
            txtTarih.setText(rTarih.get(position));

            return row;
        }
    }
}
