package com.playmoweb.activitydetection;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ActivityAdapter
 *
 * @author Playmoweb
 */
public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRENCH);

    private List<DatedActivity> datedActivityList = new ArrayList<>();

    public void setList(List<DatedActivity> datedActivityList) {
        this.datedActivityList = datedActivityList;
        notifyDataSetChanged();
    }

    public void addItem(DatedActivity datedActivity) {
        datedActivityList.add(datedActivity);
        notifyDataSetChanged();
    }

    public DatedActivity getItem(int position) {
        return datedActivityList.get(datedActivityList.size() - position - 1);
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_activity, parent, false);
        return new ActivityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        holder.setDatedActivity(getItem(position));
    }

    @Override
    public int getItemCount() {
        return datedActivityList.size();
    }

    class ActivityViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.activityType)
        TextView activityType;
        @Bind(R.id.activityConfidence)
        TextView activityConfidence;
        @Bind(R.id.activityDate)
        TextView activityDate;

        public ActivityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setDatedActivity(DatedActivity datedActivity) {
            activityType.setText(datedActivity.getType());
            activityConfidence.setText(datedActivity.getConfidence() + "%");
            activityDate.setText(sdf.format(datedActivity.date));
        }
    }
}
