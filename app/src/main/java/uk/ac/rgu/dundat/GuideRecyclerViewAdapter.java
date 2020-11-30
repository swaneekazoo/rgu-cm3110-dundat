package uk.ac.rgu.dundat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.rgu.dundat.data.Guide;

public class GuideRecyclerViewAdapter extends RecyclerView.Adapter<GuideRecyclerViewAdapter.GuideViewHolder> {
    // debug tag
    private final String TAG = "CW1";
    // the current context the adapter is working in
    private final Context context;
    // the data to be displayed
    private List<Guide> guides;
    // Intent extra keys
    private final String EXTRA_ID = "id";

    /**
     * Creates a new {@link GuideRecyclerViewAdapter}.
     * @param context The current context the adapter is working in.
     * @param guides The data to be displayed.
     */
    public GuideRecyclerViewAdapter(Context context, List<Guide> guides) {
        super();
        this.context = context;
        this.guides = guides;

        Log.d(TAG, "Adapter created.");
        Log.d(TAG, String.valueOf(getItemCount()));
    }

    @NonNull
    @Override
    public GuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "Creating ViewHolder.");
        View itemView = LayoutInflater.from(context).inflate(R.layout.guide_list_item_view, parent, false);
        GuideViewHolder viewHolder = new GuideViewHolder(itemView, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GuideViewHolder holder, int position) {
        Log.d(TAG, "Binding ViewHolder.");

        // get guide
        Guide guide = guides.get(position);

        // get guide contents
        String title = guide.getTitle();
        int wpCount = guide.getWpCount();
        double distance = guide.getDistance();

        // get Views in ViewHolder
        TextView tv_listItemTitle = holder.guideItemView.findViewById(R.id.tv_listItem_static_title2);
        TextView tv_listItemWPCount = holder.guideItemView.findViewById(R.id.tv_listItem_static_wpCount);
        TextView tv_listItemDistance = holder.guideItemView.findViewById(R.id.tv_listItem_static_distance2);

        // bind guide to Views
        tv_listItemTitle.setText(title);
        tv_listItemWPCount.setText(context.getString(R.string.tv_listItemWPCount, wpCount));
        String distanceString = Double.toString(distance);
        tv_listItemDistance.setText(context.getString(R.string.tv_listItemDistance, distanceString));
    }

    @Override
    public int getItemCount() {
        return this.guides.size();
    }

    class GuideViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View guideItemView;
        private GuideRecyclerViewAdapter adapter;

        public GuideViewHolder(View guideItemView, GuideRecyclerViewAdapter adapter) {
            super(guideItemView);
            this.guideItemView = guideItemView;
            this.adapter = adapter;

            // add on-click listener
            this.guideItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // get position of list item that was clicked
            int position = getAdapterPosition();
            // get Guide corresponding to position
            Guide guide = guides.get(position);
            // log
            Log.d(TAG, "Guide for " + guide.getTitle() + " clicked.");
            launchGuideActivity(guide.getId());
        }

        private void launchGuideActivity(int id) {
            Intent intent = new Intent(context, GuideActivity.class);
            // Extras
            intent.putExtra(EXTRA_ID, id);
            context.startActivity(intent);
        }
    }
}