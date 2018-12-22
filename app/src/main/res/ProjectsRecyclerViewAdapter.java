package ir.amad.reviewer.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ir.amad.reviewer.R;
import ir.amad.reviewer.ui.model.ProjectsModel;

public class ProjectsRecyclerViewAdapter extends RecyclerView.Adapter<ir.amad.reviewer.ui.ProjectsRecyclerViewAdapter.ProjectsRecyclerViewViewHolder> {
    private ArrayList<ProjectsModel> items;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProjectsRecyclerViewAdapter(ArrayList<ProjectsModel> items) {
        this.items = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProjectsRecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        ProjectsRecyclerViewViewHolder vh = new ProjectsRecyclerViewViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.project_recycler_view_view_holder, parent, false) );

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ProjectsRecyclerViewViewHolder holder, int position) {

        ProjectsModel model = items.get(position);

        holder.name.setText(  model.getName());
        holder.group.setText( model.getGroup());
        holder.state.setText( model.getState());
        holder.type.setText( model.getType());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ProjectsRecyclerViewViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView group;
        public TextView state;
        public TextView type;

        public ProjectsRecyclerViewViewHolder(View v) {
            super(v);

            name = v.findViewById(R.id.title);
            group = v.findViewById(R.id.group);
            state = v.findViewById(R.id.state);
            type = v.findViewById(R.id.type);
        }
    }
}
