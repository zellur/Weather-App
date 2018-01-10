package org.beginners.saran.weather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import java.util.ArrayList;


public class WeatherActivity extends AppCompatActivity{
    private FusedLocationProviderClient client;
    private LocationCallback locationCallback;
    private LocationRequest request;
    private android.support.v4.app.Fragment fragment;
    private double lat;
    private double lat1=0;
    private double lon;
    TabLayout tabLayout;
    ViewPager viewPager;
    TabPagerAdapter tabPagerAdapter;
    private int count = 15;
    public static String unit = "metric";
    private Location location;
    MenuItem menuItem;
    MaterialSearchView searchView;
    private String query1;
    AppBarLayout appBarLayout;
    ArrayList<String> cities;
     static String[] cty;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode>0){
            startActivity(new Intent(WeatherActivity.this,WeatherActivity.class));
        }
        else return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        final CitySource citySource = new CitySource(WeatherActivity.this);
        cities =   citySource.getCities();
        Intent intent=getIntent();
        appBarLayout=findViewById(R.id.invisible);
        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Current Weather"));
        tabLayout.addTab(tabLayout.newTab().setText("Weather Forecast"));

        client = LocationServices.getFusedLocationProviderClient(this);



        if(tabPagerAdapter!=null)
            tabPagerAdapter.notifyDataSetChanged();
        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {

                    lat=location.getLatitude();
                    lon=location.getLongitude();

                    setTabLayout(lat,lon,unit,count,"0");
                    Toast.makeText(WeatherActivity.this,"lat="+lat+" lon="+lon,Toast.LENGTH_SHORT).show();
                }
            }

        };

        request = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(60000)
                .setFastestInterval(1000);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},1);
            return;
        }
        client.requestLocationUpdates(request, locationCallback, null);
        client.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            lat=location.getLatitude();
                            lon=location.getLongitude();
                            setTabLayout(lat,lon,unit,count,"0");

                        }
                    }
                });
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setSuggestions(getCt(cities));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query1=query;
                CityModelClass cityModelClass = new CityModelClass(query);
                CitySource source = new CitySource(WeatherActivity.this);
                source.incertCityData(cityModelClass);

                ////**************for get data from database*************///
                cities=citySource.getCities();
                searchView.setSuggestions(getCt(cities));
                lat1=181;
                setTabLayout(lat1,lon,unit,count,query);
                tabPagerAdapter.notifyDataSetChanged();
                menuItem.setVisible(true);
                searchView.closeSearch();
                searchView.setSuggestions(getCt(cities));
                onResume();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        searchView.setHint("City name, Country short name");
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                tabLayout.setVisibility(View.INVISIBLE);
                appBarLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                appBarLayout.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.VISIBLE);

            }

        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (tabPagerAdapter!=null)
        {
            tabPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem Currentloc=menu.findItem(R.id.currentLocation);

        menuItem=Currentloc;
        if(lat1==181)
            Currentloc.setVisible(true);

        MenuItem Unit=menu.findItem(R.id.unitChange);
        if(unit.equals("imperial"))
        {
            Unit.setTitle("Celsius");
        }
        else
        {
            Unit.setTitle("Fahrenheit");
        }

        MenuItem Count=menu.findItem(R.id.countChange);
        if(count==15)
        {
            Count.setTitle("7 days");
        }
        else {
            Count.setTitle("14 days");
        }


        MenuItem item = menu.findItem(R.id.action_search);
        try {
            searchView.setMenuItem(menu.findItem(R.id.action_search));
        }catch (Exception e){}

        return true;
    }

    public void setTabLayout(double lat, double lon,String unit,int count,String city){

        CurrentWeatherFragment currentWeatherFragment=CurrentWeatherFragment.newInstance(lat,lon,unit,city);
        ForcastWeatherFragment forcastWeatherFragment=ForcastWeatherFragment.newInstance(lat,lon,unit,count,city);
        viewPager=findViewById(R.id.viewPager);
        tabPagerAdapter=new TabPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),
                currentWeatherFragment,forcastWeatherFragment);
        viewPager.setAdapter(tabPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public void changeCount(MenuItem item) {
        setCount();
        if(count==15)
        {
            item.setTitle("7 days");
        }
        else {
            item.setTitle("14 days");
        }

    }

    public void changeUnit(MenuItem item)
    {
        setUnit();
        if(unit.equals("imperial"))
        {
            item.setTitle("Celsius");
        }
        else
        {
            item.setTitle("Fahrenheit");
        }
    }

    public void lastLoc(MenuItem item) {

        lat1=0;

        item.setVisible(false);
        setTabLayout(lat,lon,unit,count,"0");
        Intent intent=new Intent(WeatherActivity.this,WeatherActivity.class);
        startActivity(intent);
    }
    public void setCount(){
        if(count==8){
            count=15;
            if(lat1==181) {
                setTabLayout(lat1,lon,unit,count,query1);

            }
            else {
                setTabLayout(lat,lon,unit,count,"0");

            }
        }
        else {

            count=8;
            if(lat1==181) {
                setTabLayout(lat1,lon,unit,count,query1);
            }
            else {
                setTabLayout(lat,lon,unit,count,"0");

            }
        }
    }
    public void setUnit(){
        if(unit.equals("imperial")){

            unit="metric";
            if(lat1==181) {
                setTabLayout(lat1,lon,unit,count,query1);

            }
            else {
                setTabLayout(lat,lon,unit,count,"0");

            }
        }
        else {

            unit="imperial";
            if(lat1==181) {
                setTabLayout(lat1,lon,unit,count,query1);
            }
            else {
                setTabLayout(lat,lon,unit,count,"0");

            }
        }
    }
    String[] getCt(ArrayList<String> cities){
        cty=cities.toArray(new String[cities.size()]);
        return cty;
    }

}
