package org.tikal;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.tikal.bus.BusProvider;
import org.tikal.bus.LabelMessage;


public class CounterFragment extends Fragment {
    private Bus uiBus;

    private TextView counterTextView;
    public CounterFragment() {
        this.uiBus = BusProvider.getUiBusInstance();
     }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_counter, container, false);
        counterTextView = (TextView) result.findViewById(R.id.counter);
        return result;
    }

    private int counter = 0;

    @Subscribe public void answerAvailable(LabelMessage event) {
        counter++;
        counterTextView.setText("Event number #"+counter+" arrived");
    }

    @Override
    public void onAttach(Activity activity) {
        uiBus.register(this);
        super.onAttach(activity);
     }

    @Override
    public void onDetach() {
        uiBus.unregister(this);
        super.onDetach();

    }
}
