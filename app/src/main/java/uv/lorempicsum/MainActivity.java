package uv.lorempicsum;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit2.converter.gson.GsonConverterFactory;
import uv.lorempicsum.List.ImageResult;
import uv.lorempicsum.List.LoremPicsumService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import androidx.appcompat.widget.Toolbar;
public class MainActivity extends AppCompatActivity {

    @Override
    @SuppressWarnings ("deprecation")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById (R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar (toolbar);
        setTitle (R.string.app_name);

        RecyclerView reView = findViewById(R.id.loremPicsum);
        reView.setLayoutManager (new LinearLayoutManager (this, RecyclerView.VERTICAL, false));
        reView.addItemDecoration (new DividerItemDecoration (this, DividerItemDecoration.VERTICAL));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl ("https://picsum.photos/")
                .addConverterFactory (GsonConverterFactory.create())
                .build ();

        LoremPicsumService api = retrofit.create (LoremPicsumService.class);
        api.getImages ().enqueue (new Callback<List<ImageResult>>() {
            @Override
            public void onResponse(Call<List<ImageResult>> call, Response<List<ImageResult>> response) {
                if (response.body () == null) return;
                List<ImageResult> lista = response.body();
                runOnUiThread (() -> reView.setAdapter (new ListAdapter (getBaseContext (), lista)));
            }

            @Override
            public void onFailure(Call<List<ImageResult>> call, Throwable t) {
                if (t.getMessage () != null) {
                    Log.e ("PKAT", t.getMessage ());
                    runOnUiThread (() ->
                            Toast.makeText (getBaseContext (), "Ocurrió un error al descargar el archivo", Toast.LENGTH_LONG).show ());
                }
            }
        });
    }
}

class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private final Context context;
    private final List<ImageResult> lista;

    ListAdapter (Context context, List<ImageResult> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (context).inflate (R.layout.recyclerview_row, parent, false);
        return new MyViewHolder (view);
    }

    @Override
    public void onBindViewHolder (@NonNull MyViewHolder holder, int position) {
        holder.setData (lista.get(position).getAuthor(),lista.get(position).getUrl(),lista.get(position).getDownload_url());
    }

    @Override
    public int getItemCount() {
        return lista.size ();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final ImageView Download_Url;
        final TextView Author;
        final TextView Url;

        MyViewHolder(@NonNull View itemView) {
            super (itemView);
            Download_Url = itemView.findViewById (R.id.Download_Url);
            Author = itemView.findViewById (R.id.Author);
            Url = itemView.findViewById (R.id.Url);
        }

        void setData (String Autor,String Link, String imageURL) {
            Author.setText (Autor);
            Url.setText (Link);
            if (imageURL.startsWith ("http://")) {
                imageURL = imageURL.replaceFirst ("http://", "https://");
            }
            Picasso.get ()
                    .load (imageURL)
                    .resize (300, 300)
                    .centerCrop ()
                    .placeholder (R.drawable.placeholder)
                    .into (Download_Url);
        }
    }
}

/*
    ELABORADO POR:
    - Sixtega Escribano Miguel Angel
    - Tapia Vazquez Alan Eduardo @TaponTV
    - Medina Zarate Alan Emmanuel
    Github:
        @Mignatech
        @TaponTV
        @Makarotk
    Copyright 2022
 */