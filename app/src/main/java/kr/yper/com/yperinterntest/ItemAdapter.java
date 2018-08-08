package kr.yper.com.yperinterntest;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    private ArrayList<Item> list = new ArrayList<>();
    private ACallback callback;

    public ItemAdapter(ACallback callback) {
        this.callback = callback;
    }

    public interface ACallback {
        void onClickItem(int position, Item item);
        void needItemsCheck(int count);
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        final Item item = list.get(position);
        final int pos = position;
        holder.tvTime.setText(item.time);
        holder.tvKey.setText(String.valueOf(item.key));
        holder.tvAddress.setText(item.address);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickItem(pos, item);
            }
        });
    }

    public void setNewList(List<Item> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
        callback.needItemsCheck(getItemCount());
    }

    public void addItem(Item item) {
        list.add(item);
        notifyDataSetChanged();
        callback.needItemsCheck(getItemCount());
    }

    public void deleteItem(int pos) {
        list.remove(pos);
        notifyDataSetChanged();
        callback.needItemsCheck(getItemCount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        TextView tvTime;
        TextView tvKey
        TextView tvAddress;

        ItemHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvKey = itemView.findViewById(R.id.tvKey);
            tvAddress = itemView.findViewById(R.id.tvAddress);
        }
    }

}
