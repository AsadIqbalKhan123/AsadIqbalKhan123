package com.shashank.expensermanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.expensermanager.R;
import com.shashank.expensermanager.activities.AddExpenseActivity;
import com.shashank.expensermanager.transactionDb.AppDatabase;
import com.shashank.expensermanager.transactionDb.TransactionEntry;
import com.shashank.expensermanager.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {


    Context context;
    private List<TransactionEntry> transactionEntries;
    private AppDatabase appDatabase;

    public CustomAdapter(Context context, List<TransactionEntry> transactionEntries) {
        this.context = context;
        this.transactionEntries = transactionEntries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String amount;

//        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//
////                transactionEntries.remove(1)
//
//                Toast.makeText(context, "Press Long", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });


        holder.categoryTextViewrv.setText(transactionEntries.get(position).getCategory());
        if (transactionEntries.get(position).getTransactionType().equals(Constants.incomeCategory)) {
            amount = "+" + transactionEntries.get(position).getAmount();
            holder.amountTextViewrv.setText(amount);
            holder.amountTextViewrv.setTextColor(Color.parseColor("#aeea00"));
        } else {
            amount = "-" + transactionEntries.get(position).getAmount();
            holder.amountTextViewrv.setText(amount);
            holder.amountTextViewrv.setTextColor(Color.parseColor("#ff5722"));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateToBeSet = sdf.format(transactionEntries.get(position).getDate());
        holder.dateTextViewrv.setText(dateToBeSet);
        holder.descriptionTextViewrv.setText(transactionEntries.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        if (transactionEntries == null || transactionEntries.size() == 0) {
            return 0;
        } else {
            return transactionEntries.size();
        }
    }

    public List<TransactionEntry> getTransactionEntries() {
        return transactionEntries;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        TextView categoryTextViewrv;
        TextView amountTextViewrv;
        TextView descriptionTextViewrv;
        TextView dateTextViewrv;

        public ViewHolder(View itemView) {
            super(itemView);

            this.view = itemView;

            categoryTextViewrv = itemView.findViewById(R.id.categoryTextViewrv);
            amountTextViewrv = itemView.findViewById(R.id.amountTextViewrv);
            descriptionTextViewrv = itemView.findViewById(R.id.descriptionTextViewrv);
            dateTextViewrv = itemView.findViewById(R.id.dateTextViewrv);

            appDatabase = AppDatabase.getInstance(context);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Toast.makeText(context, "Press long for Edit ", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, AddExpenseActivity.class);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String date = sdf.format(transactionEntries.get(getAdapterPosition()).getDate());

                    if (transactionEntries.get(getAdapterPosition()).getTransactionType().equals(Constants.incomeCategory)) {
                        intent.putExtra("from", Constants.editIncomeString);
                        intent.putExtra("amount", transactionEntries.get(getAdapterPosition()).getAmount());
                        intent.putExtra("description", transactionEntries.get(getAdapterPosition()).getDescription());
                        intent.putExtra("date", date);
                        intent.putExtra("id", transactionEntries.get(getAdapterPosition()).getId());
                    } else {
                        intent.putExtra("from", Constants.editExpenseString);
                        intent.putExtra("amount", transactionEntries.get(getAdapterPosition()).getAmount());
                        intent.putExtra("description", transactionEntries.get(getAdapterPosition()).getDescription());
                        intent.putExtra("date", date);
                        intent.putExtra("category", transactionEntries.get(getAdapterPosition()).getCategory());
                        intent.putExtra("id", transactionEntries.get(getAdapterPosition()).getId());
                    }


                    //Updated on 19/8/2018 no need of this now as added update function properly
                    /*AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            appDatabase.transactionDao().removeExpense(transactionEntries.get(getAdapterPosition()));
                        }
                    });*/

                    context.startActivity(intent);
                    return true;

                }
            });
        }
    }
}
