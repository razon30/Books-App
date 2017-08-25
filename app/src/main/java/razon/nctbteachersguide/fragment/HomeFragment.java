package razon.nctbteachersguide.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import razon.nctbteachersguide.R;
import razon.nctbteachersguide.activity.HomeActivity;
import razon.nctbteachersguide.adapter.AdapterHomeRecycler;
import razon.nctbteachersguide.model.ClassModel;
import razon.nctbteachersguide.utils.ClassificationNode;
import razon.nctbteachersguide.utils.ClickListener;
import razon.nctbteachersguide.utils.MyRecyclerView;
import razon.nctbteachersguide.utils.RecyclerTOuchListener;


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

        HomeActivity.CurrentPage = ClassificationNode.HOME;

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Class Wise Books");

        classList = new ArrayList<>();
        populateData();
        recyclerView = (MyRecyclerView) view.findViewById(R.id.home_recycler);
        adapter = new AdapterHomeRecycler(classList, getActivity());
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);


        recyclerView.addOnItemTouchListener(new RecyclerTOuchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onCLick(View v, int position) {

                BookListFragment bookListFragment = new BookListFragment();
                String identifier = classList.get(position).getClsIdentifier();
                Bundle bundle = new Bundle();
                bundle.putString("id", identifier);
                bookListFragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.fade_out);
                transaction.replace(R.id.fragment_container, bookListFragment);
                transaction.commit();

            }

            @Override
            public void onLongClick(View v, int position) {

            }
        }));

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
