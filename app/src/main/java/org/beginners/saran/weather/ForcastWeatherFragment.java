package org.beginners.saran.weather;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ForcastWeatherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ForcastWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForcastWeatherFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private WeatherForcastResponse weatherForcastResponse;
    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    private WeatherForcastService weatherForcastService;
    private static final String BASE_URL="http://api.openweathermap.org/data/2.5/forecast/";
    private double lat,lon;
    private String unit;
    private int count;
    private String city;
    View view;
    Context context1;
    private String appid="d0b941339d6f075686460c7fe0912041";
    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;

    public ForcastWeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ForcastWeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForcastWeatherFragment newInstance(double param1, double param2, String unit, int count, String city) {
        ForcastWeatherFragment fragment = new ForcastWeatherFragment();
        Bundle args = new Bundle();
        args.putDouble("lat", param1);
        args.putDouble("lon", param2);
        args.putString("unit", unit);
        args.putString("city", city);
        args.putInt("count", count);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lat=getArguments().getDouble("lat");
            lon=getArguments().getDouble("lon");
            unit=getArguments().getString("unit");
            city=getArguments().getString("city");
            count=getArguments().getInt("count");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          view= inflater.inflate(R.layout.fragment_forcast_weather, container, false);
          String url;
        final String f_Or_c;
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherForcastService=retrofit.create(WeatherForcastService.class);
        if(lat!=181)
        {
            url=String.format("daily?lat=%f&lon=%f&cnt=%d&units=%s&appid=%s",lat,lon,count,unit,appid);
        }
        else
        {
            url=String.format("daily?q=%s&units=%s&cnt=%d&appid=%s",city,unit,count,appid);
        }
        if(unit.equals("metric")){
            f_Or_c="°C";
        }
        else
            f_Or_c="°F";
        Call<WeatherForcastResponse> weatherForcastResponseCall=weatherForcastService.getWeatherForcastRespose(url);

        weatherForcastResponseCall.enqueue(new Callback<WeatherForcastResponse>() {
            @Override
            public void onResponse(Call<WeatherForcastResponse> call, Response<WeatherForcastResponse> response) {
                if(response.code()==200){
                    weatherForcastResponse=response.body();

                    recyclerView=view.findViewById(R.id.weatherRV);
                    adapter=new WeatherAdapter(context1,weatherForcastResponse,f_Or_c);
                    RecyclerView.LayoutManager manager=new LinearLayoutManager(context1,LinearLayoutManager.VERTICAL,false);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<WeatherForcastResponse> call, Throwable t) {

            }
        });

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
         context1=getActivity();
     }

    /* @Override
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
}
