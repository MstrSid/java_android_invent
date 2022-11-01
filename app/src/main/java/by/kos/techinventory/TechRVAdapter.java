package by.kos.techinventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import by.kos.techinventory.TechRVAdapter.TechViewHolder;
import java.util.ArrayList;
import java.util.List;

public class TechRVAdapter extends RecyclerView.Adapter<TechViewHolder> {

  private List<TechItem> techItems = new ArrayList<>();
  private OnTechClickListener onTechClickListener;

  public List<TechItem> getTechItems() {
    return techItems;
  }

  public void setTechItems(List<TechItem> techItems) {
    this.techItems = techItems;
  }

  public void setOnTechClickListener(
      OnTechClickListener onTechClickListener) {
    this.onTechClickListener = onTechClickListener;
  }

  @NonNull
  @Override
  public TechViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.tech_item_card, parent, false);
    return new TechViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull TechViewHolder holder, int position) {
    TechItem techItem = techItems.get(position);
    holder.tvTechItem.setText(techItem.getName());
    holder.tvSerialData.setText(techItem.getInvent());
    String[] arrayConditions = holder.itemView.getContext().getResources()
        .getStringArray(R.array.conditions);
    holder.tvConditionData.setText(arrayConditions[techItem.getCondition()]);

    holder.itemView.setOnClickListener(view -> {
      if (onTechClickListener != null) {
        onTechClickListener.onTechClick(techItem);
      }
    });
  }

  @Override
  public int getItemCount() {
    return techItems.size();
  }

  class TechViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTechItem;
    private TextView tvSerialData;
    private TextView tvConditionData;

    public TechViewHolder(@NonNull View itemView) {
      super(itemView);
      tvTechItem = itemView.findViewById(R.id.tvTechItem);
      tvSerialData = itemView.findViewById(R.id.tvSerialData);
      tvConditionData = itemView.findViewById(R.id.tvConditionData);
    }
  }

  interface OnTechClickListener {

    void onTechClick(TechItem techItem);
  }

}
