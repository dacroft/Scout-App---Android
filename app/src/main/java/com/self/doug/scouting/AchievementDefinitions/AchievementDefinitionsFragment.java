package com.self.doug.scouting.AchievementDefinitions;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.self.doug.scouting.ParseWrapper.ParseObjectWrapper;
import com.self.doug.scouting.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class AchievementDefinitionsFragment extends Fragment implements AbsListView.OnItemClickListener, ActionMode.Callback {

    // Request achievement definition information from other Views.
    public static final String ModifiedAchievementDefinition = "ModifiedAchievementDefinition";
    public static final int AchievementDefinitionRequestCode = 1;

    private List<AchievementDefinition> achievementDefinitions;
    protected Object mActionMode;
    public int selectedItem = -1;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private AchievementDefinitionsListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static AchievementDefinitionsFragment newInstance(String param1, String param2) {
        AchievementDefinitionsFragment fragment = new AchievementDefinitionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AchievementDefinitionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Add an empty list of AchievementDefinitions to the array adapter
        this.achievementDefinitions = new ArrayList<AchievementDefinition>();
        this.mAdapter = new AchievementDefinitionsListAdapter(this.getActivity(),this.achievementDefinitions);

        this.updateData();  // Update our data


    }

//    @Override
//    public void onResume() {
//        super.onResume();
//    }

    private void updateData() {
        // Obtain current list of achievement definitions.
        ParseQuery<ParseObject> achievementDefinitionParseQuery = ParseQuery.getQuery(AchievementDefinition.t_tablename);
        achievementDefinitionParseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null)
                {
                    return;
                }
                AchievementDefinitionsFragment.this.achievementDefinitions.clear();
                //AchievementDefinitionsFragment.this.mAdapter.clear();
                for (ParseObject parseObject : list)
                {
                    AchievementDefinition achievementDefinition = new AchievementDefinition((parseObject));
                    AchievementDefinitionsFragment.this.achievementDefinitions.add(achievementDefinition);
                }
                //AchievementDefinitionsFragment.this.mAdapter.addAll(achievementDefinitions);
                AchievementDefinitionsFragment.this.mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement_definitions, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (mActionMode != null) {
                    return false;
                }
                selectedItem = position-1;

                // Start the CAB using the ActionMode.Callback defined above
                getActivity().startActionMode(AchievementDefinitionsFragment.this);
                view.setSelected(true);
                return true;
            }
        });

        // Add a header
        View header = getActivity().getLayoutInflater().inflate(R.layout.achievement_definition_header, null);
        Button addButton = (Button)header.findViewById(R.id.add_achievement);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Add Button Clicked", Toast.LENGTH_LONG).show();
                //
                Intent intent = new Intent(AchievementDefinitionsFragment.this.getActivity(), AchievementDefinitionEditActivity.class);
                intent.putExtra(AchievementDefinition.t_tablename, (new AchievementDefinition("text here")));
                //intent.putExtra(AchievementDefinition.t_tablename, (new AchievementDefinition()));
                AchievementDefinitionsFragment.this.startActivityForResult(intent, AchievementDefinitionsFragment.AchievementDefinitionRequestCode);
                //AchievementDefinitionsFragment.this.mAdapter.notifyDataSetChanged();

            }
        });
        ((ListView) mListView).addHeaderView(header);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AchievementDefinitionsFragment.AchievementDefinitionRequestCode)
        {
            // Create an AchievementDefinition
            ParseObjectWrapper parseObjectWrapper = data.getParcelableExtra(AchievementDefinition.t_tablename);
            AchievementDefinition achievementDefinition = new AchievementDefinition(parseObjectWrapper);
            try {
                achievementDefinition.po.fetchIfNeeded();
            } catch (ParseException e) {

            }

            // Add the new item to the list now
            this.achievementDefinitions.add(achievementDefinition);
            //this.mAdapter.add(achievementDefinition);
            AchievementDefinitionsFragment.this.mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
            AchievementDefinition item = this.achievementDefinitions.get(position-1);
            Toast.makeText(getActivity(), item.getTitle() + " Clicked!", Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    private void show() {
        Toast.makeText(getActivity(), String.valueOf(selectedItem), Toast.LENGTH_LONG).show();
    }

    // Called when the action mode is created; startActionMode() was called
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Inflate a menu resource providing context menu items
        MenuInflater inflater = mode.getMenuInflater();
        // Assumes that you have "contexual.xml" menu resources
        inflater.inflate(R.menu.menu_achievement_definition_cab, menu);
        return true;
    }

    // Called each time the action mode is shown. Always called after
    // onCreateActionMode, but
    // may be called multiple times if the mode is invalidated.
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false; // Return false if nothing is done
    }

    // Called when the user selects a contextual menu item
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_achievement:
                Intent intent = new Intent();
                //show();
                // Action picked, so close the CAB
                mode.finish();
                return true;
            default:
                return false;
        }
    }

    // Called when the user exits the action mode
    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionMode = null;
        selectedItem = -1;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
