package ru.toolstrek.activity.fragment;

import ru.toolstrek.activity.R;
import ru.toolstrek.core.AbcURLRequestParam;
import ru.toolstrek.core.AbcURLRequestAsync;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TaskInWorkFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TaskInWorkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskInWorkFragment extends Fragment implements OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TaskInWorkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskInWorkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskInWorkFragment newInstance(String param1, String param2) {
        TaskInWorkFragment fragment = new TaskInWorkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_in_work, container, false);
        Button btnCheckLogin = (Button) view.findViewById(R.id.btnCheckLogin);
        btnCheckLogin.setOnClickListener(this);
        Button btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        Button btnLogout = (Button) view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCheckLogin:
                try {
                    String result = new AbcURLRequestAsync(this.getActivity().getApplicationContext()).execute(new AbcURLRequestParam("https://jira.toolstrek.ru/rest/auth/1/session", "GET", null)).get();
                    TextView textTitle = (TextView) getView().findViewById(R.id.textTitle);
                    textTitle.setText("Result1: "+ result);
                 } catch (Exception e) {
                    TextView textTitle = (TextView) getView().findViewById(R.id.textTitle);
                    textTitle.setText("Error1: "+e.getMessage()+"; "+e.toString());
                 }
                 break;

            case R.id.btnLogin:
                try {
                    Map<String, String> param = new HashMap<>();
                    param.put("username", "kristina");
                    param.put("password", "kristina");
                    String result = new AbcURLRequestAsync(this.getActivity().getApplicationContext()).execute(new AbcURLRequestParam("https://jira.toolstrek.ru/rest/auth/1/session", "POST", param)).get();
                    TextView textTitle = (TextView) getView().findViewById(R.id.textTitle);
                    textTitle.setText("Result2: "+ result);
                } catch (Exception e) {
                    TextView textTitle = (TextView) getView().findViewById(R.id.textTitle);
                    textTitle.setText("Error3: "+e.getMessage()+"; "+e.toString());
                }
                break;

            case R.id.btnLogout:
                try {
                    Map<String, String> param = new HashMap<>();
                    String result = new AbcURLRequestAsync(this.getActivity().getApplicationContext()).execute(new AbcURLRequestParam("https://jira.toolstrek.ru/rest/auth/1/session", "DELETE", param)).get();
                    TextView textTitle = (TextView) getView().findViewById(R.id.textTitle);
                    textTitle.setText("Result3: "+ result);
                } catch (Exception e) {
                    TextView textTitle = (TextView) getView().findViewById(R.id.textTitle);
                    textTitle.setText("Error4: "+e.getMessage()+"; "+e.toString());
                }
                break;
        }
    }

}
