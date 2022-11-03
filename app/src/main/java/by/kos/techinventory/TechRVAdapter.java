package by.kos.techinventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
    int resColorId;
    switch (techItem.getCondition()) {
      case 0:
        resColorId = android.R.color.holo_green_light;
        break;
      case 1:
        resColorId = android.R.color.holo_orange_light;
        break;
      case 2:
        resColorId = android.R.color.holo_red_light;
        break;
      default:
        resColorId = android.R.color.darker_gray;
        break;

    }
    int color = ContextCompat.getColor(holder.itemView.getContext(), resColorId);
    holder.tvConditionData.setBackgroundColor(color);
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

    private final TextView tvTechItem;
    private final TextView tvSerialData;
    private final TextView tvConditionData;

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
