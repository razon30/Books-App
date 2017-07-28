package razon.nctballbooksfree;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.adapters.SlideInRightAnimationAdapter;
import razon.nctballbooksfree.model.ClassModel;
import razon.nctballbooksfree.utils.ClassificationNode;
import razon.nctballbooksfree.utils.MyRecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    MyRecyclerView recyclerView;
    AdapterHomeRecycler adapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    ArrayList<ClassModel> classList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        classList = new ArrayList<>();
        populateData();
        recyclerView = (MyRecyclerView) view.findViewById(R.id.home_recycler);
        adapter = new AdapterHomeRecycler(classList, getActivity());
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(new SlideInRightAnimationAdapter(adapter));

        return view;
    }

    private void populateData() {

        classList.add(new ClassModel("Class One", ClassificationNode.clasOne));
        classList.add(new ClassModel("Class Two", ClassificationNode.clasTwo));
        classList.add(new ClassModel("Class Three", ClassificationNode.clasThree));
        classList.add(new ClassModel("Class Four", ClassificationNode.clasFour));
        classList.add(new ClassModel("Class Five", ClassificationNode.clasFive));
        classList.add(new ClassModel("Class Six", ClassificationNode.clasSix));
        classList.add(new ClassModel("Class Seven", ClassificationNode.clasSeven));
        classList.add(new ClassModel("Class Eight", ClassificationNode.clasEight));
        classList.add(new ClassModel("Nine-Ten", ClassificationNode.clasNineTen));
        classList.add(new ClassModel("Eleven-Twelve", ClassificationNode.clasElevenTwelve));

    }


    public HomeFragment() {
        // Required empty public constructor
    }


}
