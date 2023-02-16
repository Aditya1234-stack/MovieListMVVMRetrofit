package com.vicky.testmvvmretrofit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vicky.testmvvmretrofit.R;
import com.vicky.testmvvmretrofit.Repository.ResultModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder> implements Filterable {

    private final LayoutInflater inflater;
    private Context context;
    private List<ResultModel> resultModels;
    private List<ResultModel> resultModelsList;

    public AdapterClass(Context context,List<ResultModel> resultModels) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.resultModels=resultModels;
        resultModelsList=resultModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (resultModels != null)
        {
            ResultModel resultModel = resultModels.get(position);
            holder.txtTitle.setText(resultModel.getTitle());
            holder.txtBody.setText(resultModel.getYear());
            holder.txtimdb.setText("IMDB- "+resultModel.getImdbID());

            Glide.with(context)
                    .load(resultModel.getPoster())
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.img);

        }
        else {

        }
    }

    @Override
    public int getItemCount() {
        if (resultModels != null)
        {
            return resultModels.size();
        }else
        {
            return 0;

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtBody,txtimdb;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            txtTitle = itemView.findViewById(R.id.mTitle);
            txtBody = itemView.findViewById(R.id.mBody);
            txtimdb = itemView.findViewById(R.id.imdb);
        }
    }

    public void setWords(List<ResultModel> models)
    {
        resultModels = models;
        notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null && constraint.length() > 0) {
                    List<ResultModel> filterList = new ArrayList<>();
                    for (ResultModel movie:resultModels) {
                        if (movie.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())||movie.getYear().toLowerCase().contains(constraint.toString().toLowerCase()))
                        {
                            filterList.add(movie);
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                }else {
                    results.count = resultModelsList.size();
                    results.values = resultModelsList;
                }


                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
               try {
                   resultModels = (ArrayList<ResultModel>) results.values;
//                   resultModels.clear();
//                   resultModels.addAll((ArrayList) filterResults.values);
                   notifyDataSetChanged();
               }catch (Exception e){
                   e.printStackTrace();
               }


            }
        };
    }

}
