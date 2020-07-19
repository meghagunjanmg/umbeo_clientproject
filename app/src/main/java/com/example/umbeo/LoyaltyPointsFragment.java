package com.example.umbeo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.umbeo.Storage.UserPreference;
import com.example.umbeo.api.Api;
import com.example.umbeo.api.RetrofitClient;
import com.example.umbeo.response_data.ProductModel;
import com.example.umbeo.response_data.ProductResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoyaltyPointsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoyaltyPointsFragment extends Fragment {


    UserPreference preference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoyaltyPointsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoyaltyPointsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoyaltyPointsFragment newInstance(String param1, String param2) {
        LoyaltyPointsFragment fragment = new LoyaltyPointsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        preference = new UserPreference(getContext());

        if(preference.getTheme()==1){
            return inflater.inflate(R.layout.dark_loyalty, container, false);
        }

        // Inflate the layout for this fragment
      else   return inflater.inflate(R.layout.fragment_loyalty_points, container, false);
    }


    TextView my_points;

    RecyclerView item_recycler;
    List<ItemModel> mPersonalItems;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preference = new UserPreference(getContext());

        my_points = view.findViewById(R.id.my_points);

        if(preference.getLoyaltyPoints()!=0){
            my_points.setText(preference.getLoyaltyPoints()+"");
        }

        item_recycler = view.findViewById(R.id.item_recycler);
        GridLayoutManager mGridLayoutManager2 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        item_recycler.setLayoutManager(mGridLayoutManager2);

            getTrendingProducts();

   /*     mPersonalItems = new ArrayList<>();

        mPersonalItems.add(new ItemModel("Colgate", "pic_2", 0));
        mPersonalItems.add(new ItemModel("Hair oil", "pic_3", 0));



        List<CategoryModel> categoryModelList = new ArrayList<>();
        categoryModelList.add(new CategoryModel("Top Awarded Products", mPersonalItems));

        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(categoryModelList, getContext());
        item_recycler.setAdapter(categoryListAdapter);

    */
    }

    private void getTrendingProducts() {
        final Utils.CustomDialog customDialog = new Utils.CustomDialog();
        customDialog.showProgress(getContext(),"Loading...","Please Wait");

        RetrofitClient api_manager = new RetrofitClient();
        Api retrofit_interface =api_manager.usersClient().create(Api.class);

        Call<ProductResponse> call = retrofit_interface.fetchTrendingProducts(preference.getShopId());

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                try {
                    Log.e("TrendingProduct",response+"");
                    Log.e("TrendingProduct",response.code()+"");
                    Log.e("TrendingProduct",response.message()+"");
                    if(response.code()==200){
                        List<ProductModel> productModels = response.body().getData().getProducts();
                        Log.e("TrendingProduct",productModels.get(0).getName()+"");

                  /*      for(int i = 0;i<productModels.size();i++) {
                            for (CartEntity e : entities) {
                                if (e.getName().equalsIgnoreCase(productModels.get(i).getName())) {
                                    ProductModel productModel = new ProductModel(e.getName(),e.getCategoryId()
                                            ,e.getSubCategoryId(),productModels.get(i).getPrice(),e.getDescription(),e.getQuantity(),productModels.get(i).getDiscount(),productModels.get(i).getImage());

                                    productModels.remove(i);
                                    productModels.add(productModel);
                                }
                            }
                        }

                   */
                        List<CategoryModel> categoryModelList = new ArrayList<>();
                        categoryModelList.add(new CategoryModel("","Top Awarded Products", productModels));

                        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(categoryModelList, getContext());
                        item_recycler.setAdapter(categoryListAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    customDialog.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                customDialog.hideProgress();
            }
        });
    }


}