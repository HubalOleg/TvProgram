package oleg.hubal.com.tvprogram.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import oleg.hubal.com.tvprogram.Constants;
import oleg.hubal.com.tvprogram.R;
import oleg.hubal.com.tvprogram.database.model.Program;
import oleg.hubal.com.tvprogram.list.ProgramAdapter;

/**
 * Created by User on 11.09.2016.
 */
public class PageFragment extends Fragment {

    private RecyclerView programRecyclerView;

    public ArrayList<Program> programs;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page_list, container, false);
        getProgramList();

        return view;
    }

    private void getProgramList() {
        Bundle bundle = getArguments();
        programs = bundle.getParcelableArrayList(Constants.BUNDLE_PROGRAMS);

        programRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_FPageList);
        programRecyclerView.setHasFixedSize(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        programRecyclerView.setLayoutManager(layoutManager);

        ProgramAdapter programAdapter = new ProgramAdapter(programs);
        programRecyclerView.setAdapter(programAdapter);
    }


}
