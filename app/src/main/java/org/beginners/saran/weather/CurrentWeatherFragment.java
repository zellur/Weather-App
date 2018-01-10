package org.beginners.saran.weather;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CurrentWeatherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CurrentWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentWeatherFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private WeatherResponse weatherResponse;
    private WeatherService weatherService;
    private double lat ;
    private double lon ;
    View view;
    public static String unit;
    public static final String Base_Url = "http://api.openweathermap.org/data/2.5/";
    String appID = "d0b941339d6f075686460c7fe0912041";
    String city;
    private OnFragmentInteractionListener mListener;
    Context context1;
    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment CurrentWeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentWeatherFragment newInstance(double param1,double param2,String unit,String city) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putDouble("lat", param1);
        args.putDouble("lon", param2);
        args.putString("unit", unit);
        args.putString("city", city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lat = getArguments().getDouble("lat");
            lon = getArguments().getDouble("lon");
            unit = getArguments().getString("unit");
            city = getArguments().getString("city");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.current_weather_layout, container, false);
        String url;
        final String f_Or_c;
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherService=retrofit.create(WeatherService.class);
        if(lat!=181)
        {
            url=String.format("weather?lat=%4.6f&lon=%4.6f&units=%s&appid=%s",lat,lon,unit,appID);
        }
        else
            url=String.format("weather?q=%s&units=%s&appid=%s",city,unit,appID);
        if(unit.equals("metric")){
            f_Or_c="°C";
        }
        else
            f_Or_c="°F";
        final Call<WeatherResponse> weatherResponseCall=weatherService.getWeatherRespose(url);
        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if(response.code()==200){
                    weatherResponse=response.body();

                    LinearLayout layout = view.findViewById(R.id.currentLocation);
                    if (weatherResponse.getWeather().get(0).getIcon().contains("01")){
                        layout.setBackgroundResource(R.drawable.clearsky);
                    }
                    else if (weatherResponse.getWeather().get(0).getIcon().contains("02")){
                        layout.setBackgroundResource(R.drawable.fewclouds);
                    }
                    else if (weatherResponse.getWeather().get(0).getIcon().contains("03")){
                        layout.setBackgroundResource(R.drawable.scatteredclouds);
                    }
                    else if (weatherResponse.getWeather().get(0).getIcon().contains("04")){
                        layout.setBackgroundResource(R.drawable.brokenclouds);
                    }
                    else if (weatherResponse.getWeather().get(0).getIcon().contains("09")){
                        layout.setBackgroundResource(R.drawable.showerrain);
                    }
                    else if (weatherResponse.getWeather().get(0).getIcon().contains("10")){
                        layout.setBackgroundResource(R.drawable.rain);
                    }
                    else if (weatherResponse.getWeather().get(0).getIcon().contains("11")){
                        layout.setBackgroundResource(R.drawable.thunderstrom);
                    }
                    else if (weatherResponse.getWeather().get(0).getIcon().contains("13")){
                        layout.setBackgroundResource(R.drawable.snow);
                    }
                    else if (weatherResponse.getWeather().get(0).getIcon().contains("50")){
                        layout.setBackgroundResource(R.drawable.hard);
                    }

                    String icon=weatherResponse.getWeather().get(0).getIcon();
                    ImageView iconView=view.findViewById(R.id.weatherIcon);
                    Picasso.with(context1).load("http://openweathermap.org/img/w/"+icon+".png").into(iconView);
                    TextView tempTV=view.findViewById(R.id.tempTV);
                    TextView descriptnTV=view.findViewById(R.id.descritionTV);
                    TextView humidityTV=view.findViewById(R.id.humidityTV);
                    TextView pressurTV=view.findViewById(R.id.pressureTV);
                    TextView maxtepmTV=view.findViewById(R.id.maxtempTV);
                    TextView mintempTV=view.findViewById(R.id.mintempTV);
                    TextView loactionTV = view.findViewById(R.id.location);
                    tempTV.setText(weatherResponse.getMain().getTemp().toString()+f_Or_c);
                    String des=weatherResponse.getWeather().get(0).getDescription();
                    String desUp=String.valueOf(des.charAt(0)).toUpperCase()+des.subSequence(1,des.length());
                    descriptnTV.setText(desUp);
                    loactionTV.setText(weatherResponse.getName()+", "+weatherResponse.getSys().getCountry());
                    humidityTV.setText("Humidity"+"\n"+weatherResponse.getMain().getHumidity().toString());
                    pressurTV.setText("Pressure"+"\n"+weatherResponse.getMain().getPressure().toString());
                    maxtepmTV.setText("Max Temp"+"\n"+weatherResponse.getMain().getTempMax().toString()+f_Or_c);
                    mintempTV.setText("Min Temp"+"\n"+weatherResponse.getMain().getTempMin().toString()+f_Or_c);

                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(context1,"Please check your internet",Toast.LENGTH_SHORT).show();
                Log.e("error",t.getMessage());
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
    }*/

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
