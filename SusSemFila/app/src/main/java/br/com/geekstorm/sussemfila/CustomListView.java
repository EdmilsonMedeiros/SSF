package br.com.geekstorm.sussemfila;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class CustomListView extends ArrayAdapter<String> {
    private String[] dataConsulta;
    private String[] especialidade;
    private String[] foto;
    private Activity context;
    Bitmap bitmap;
    public CustomListView(Activity context, String[] dataConsulta, String[] especialidade , String[] foto) {
        super(context, R.layout.layout_view, dataConsulta);
        this.context = context;
        this.dataConsulta = dataConsulta;
        this.especialidade = especialidade;
        this.foto = foto;
    }
    //---------
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View r = convertView;
        ViewHolder viewHolder = null;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.layout_view, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)r.getTag();
        }

        viewHolder.tvw1.setText(dataConsulta[position]);
        viewHolder.tvw2.setText(especialidade[position]);
        //imageview
        new GetImageFromUrl(viewHolder.ivw).execute(foto[position]);

        return r;
    }//---------
    class ViewHolder{
        TextView tvw1;
        TextView tvw2;
        ImageView ivw;

        ViewHolder(View v){
            tvw1 = (TextView) v.findViewById(R.id.tvData);
            tvw2 = (TextView) v.findViewById(R.id.tvEspecialidade);
            //imageView
            ivw = (ImageView) v.findViewById(R.id.imageView);
        }


    }
    //retornaImagem

    public  class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

        ImageView imgView;
        public GetImageFromUrl(ImageView imgV){
            this.imgView = imgV;
        }
        @Override
        protected Bitmap doInBackground(String... url) {
            String urlDisplay = url[0];
            bitmap = null;

            try{
                InputStream ist = new java.net.URL(urlDisplay).openStream();
                bitmap = BitmapFactory.decodeStream(ist);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

            return bitmap;
        }
        @Override
        protected  void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            imgView.setImageBitmap(bitmap);

        }

    }

}
