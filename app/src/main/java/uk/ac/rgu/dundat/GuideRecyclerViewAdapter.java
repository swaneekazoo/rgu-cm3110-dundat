package uk.ac.rgu.dundat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.rgu.dundat.data.Guide;

public class GuideRecyclerViewAdapter extends RecyclerView.Adapter<GuideRecyclerViewAdapter.GuideViewHolder> {
    private final Context context;
    // Debug tag
    private final String D_TAG;
    private List<Guide> guides;
    // Intent extra keys
    private final String EXTRA_TITLE;

    /**
     * Creates a new {@link GuideRecyclerViewAdapter}.
     * @param context The current context the adapter is working in.
     * @param guides The data to be displayed.
     */
    public GuideRecyclerViewAdapter(Context context, List<Guide> guides) {
        super();
        this.context = context;
        D_TAG = context.getString(R.string.D_TAG);
        EXTRA_TITLE = context.getString(R.string.EXTRA_TITLE);
        this.guides = guides;
    }

    @NonNull
    @Override
    public GuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_item_main, parent, false);
        GuideViewHolder viewHolder = new GuideViewHolder(itemView, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GuideViewHolder holder, int position) {
        // Get Guide corresponding to position of this ViewHolder
        Guide guide = guides.get(position);

        // Get Guide contents
        String title = guide.getTitle();
        int wpCount = guide.getWpCount();
        double distance = guide.getDistance();

        // Get Views in ViewHolder
        TextView tv_list_title = holder.guideItemView.findViewById(R.id.tv_list_title);
        TextView tv_list_wpCount = holder.guideItemView.findViewById(R.id.tv_list_wpCount);
        TextView tv_list_distance = holder.guideItemView.findViewById(R.id.tv_list_distance);

        // Bind Guide to Views
        tv_list_title.setText(title);
        tv_list_wpCount.setText(context.getString(R.string.tv_list_wpCount, wpCount));
        tv_list_distance.setText(context.getString(R.string.tv_list_distance, Double.toString(distance)));
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

            // Add on-click listener
            this.guideItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get Context from View
            Context context = v.getContext();
            // Get position of list item that was clicked
            int position = getAdapterPosition();
            // Get Guide corresponding to position & launch GuideActivity
            Guide guide = guides.get(position);
            launchGuideActivity(context, guide.getTitle());
        }

        /**
         * Launch a GuideActivity for the Guide with the specified title
         * @param context
         * @param title The title of the Guide to retrieve
         */
        private void launchGuideActivity(Context context, String title) {
            Intent intent = new Intent(context, GuideActivity.class);
            intent.putExtra(EXTRA_TITLE, title);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}